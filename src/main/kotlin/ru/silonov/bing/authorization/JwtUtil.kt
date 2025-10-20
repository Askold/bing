package ru.silonov.bing.authorization

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*

@Component
class JwtUtil {

    @Value("\${jwt.secret:mySecretKeyForJWTTokenGenerationThatIsAtLeast256BitsLongForHS256Algorithm}")
    private lateinit var secret: String

    @Value("\${jwt.access-token.expiration:900000}") // 15 minutes
    private var accessTokenExpiration: Long = 900000

    @Value("\${jwt.refresh-token.expiration:604800000}") // 7 days
    private var refreshTokenExpiration: Long = 604800000

    private fun getSigningKey(): Key = Keys.hmacShaKeyFor(secret.toByteArray())

    fun generateAccessToken(userDetails: EmployeeUserDetails): String {
        return generateToken(userDetails, accessTokenExpiration, TokenType.ACCESS)
    }

    fun generateRefreshToken(userDetails: EmployeeUserDetails): String {
        return generateToken(userDetails, refreshTokenExpiration, TokenType.REFRESH)
    }

    private fun generateToken(
        userDetails: EmployeeUserDetails,
        expiration: Long,
        tokenType: TokenType
    ): String {
        val claims = HashMap<String, Any>()
        claims["type"] = tokenType.name
        claims["role"] = userDetails.employee.roleId
        userDetails.employee.position?.let { claims["position"] = it }
        userDetails.employee.leadId?.let { claims["leadId"] = it }
        claims["fullName"] = userDetails.employee.fullName

        return createToken(claims, userDetails.username, expiration)
    }

    private fun createToken(claims: Map<String, Any>, subject: String, expiration: Long): String {
        val now = Date()
        val expirationDate = Date(now.time + expiration)

        return Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(now)
            .setExpiration(expirationDate)
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact()
    }

    fun extractUsername(token: String): String {
        return extractAllClaims(token).subject
    }

    fun extractTokenType(token: String): TokenType {
        val type = extractAllClaims(token)["type"] as String
        return TokenType.valueOf(type)
    }

    fun extractClaims(token: String): Claims {
        return extractAllClaims(token)
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts.parser()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .body
    }

    fun validateToken(token: String, userDetails: UserDetails): Boolean {
        val username = extractUsername(token)
        return (username == userDetails.username && !isTokenExpired(token))
    }

    fun validateAccessToken(token: String, userDetails: UserDetails): Boolean {
        return validateToken(token, userDetails) && extractTokenType(token) == TokenType.ACCESS
    }

    fun validateRefreshToken(token: String, userDetails: UserDetails): Boolean {
        return validateToken(token, userDetails) && extractTokenType(token) == TokenType.REFRESH
    }

    private fun isTokenExpired(token: String): Boolean {
        return extractAllClaims(token).expiration.before(Date())
    }

    enum class TokenType {
        ACCESS, REFRESH
    }
}