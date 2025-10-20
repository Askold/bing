package ru.silonov.bing.controller

import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import ru.silonov.bing.authorization.EmployeeUserDetails
import ru.silonov.bing.service.EmployeeUserDetailsService
import ru.silonov.bing.authorization.JwtUtil
import ru.silonov.bing.service.RefreshTokenService
import ru.silonov.bing.dto.*
import ru.silonov.bing.dto.authentication.AuthenticationRequest
import ru.silonov.bing.dto.authentication.AuthenticationResponse
import ru.silonov.bing.dto.authentication.RefreshTokenRequest
import ru.silonov.bing.dto.authentication.TokenRefreshResponse
import ru.silonov.bing.exception.TokenException

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authenticationManager: AuthenticationManager,
    private val jwtUtil: JwtUtil,
    private val refreshTokenService: RefreshTokenService,
    private val employeeUserDetailsService: EmployeeUserDetailsService
) {

    @PostMapping("/login")
    fun login(@RequestBody request: AuthenticationRequest): ResponseEntity<AuthenticationResponse> {
        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(request.login, request.password)
        )

        val userDetails = authentication.principal as EmployeeUserDetails
        val accessToken = jwtUtil.generateAccessToken(userDetails)
        val refreshToken = jwtUtil.generateRefreshToken(userDetails)

        // Save refresh token to database
        refreshTokenService.createRefreshToken(userDetails.employee.login, refreshToken)

        val response = AuthenticationResponse(
            accessToken = accessToken,
            refreshToken = refreshToken,
            login = userDetails.employee.login,
            fullName = userDetails.employee.fullName,
            role = userDetails.employee.roleId,
            position = userDetails.employee.position
        )

        return ResponseEntity.ok(response)
    }

    @PostMapping("/refresh")
    fun refreshToken(@RequestBody request: RefreshTokenRequest): ResponseEntity<TokenRefreshResponse> {
        val refreshTokenString = request.refreshToken

        return try {
            // Find token in database
            val refreshTokenEntity = refreshTokenService.findByToken(refreshTokenString)
            
            // Verify token is not expired or revoked
            refreshTokenService.verifyExpiration(refreshTokenEntity)

            // Validate JWT token
            val username = jwtUtil.extractUsername(refreshTokenString)
            val userDetails = employeeUserDetailsService.loadUserByUsername(username)

            // Verify it's a refresh token
            if (jwtUtil.extractTokenType(refreshTokenString) != JwtUtil.TokenType.REFRESH) {
                throw TokenException("Invalid token type")
            }

            // Generate new access token
            val newAccessToken = jwtUtil.generateAccessToken(
                userDetails as EmployeeUserDetails
            )

            // Optionally generate new refresh token (rotation)
            val newRefreshToken = jwtUtil.generateRefreshToken(userDetails)
            
            // Revoke old refresh token
            refreshTokenService.revokeToken(refreshTokenString)
            
            // Save new refresh token
            refreshTokenService.createRefreshToken(username, newRefreshToken)

            val response = TokenRefreshResponse(
                accessToken = newAccessToken,
                refreshToken = newRefreshToken
            )

            ResponseEntity.ok(response)
        } catch (e: TokenException) {
            ResponseEntity.badRequest().build()
        }
    }

    @PostMapping("/logout")
    fun logout(@RequestBody request: RefreshTokenRequest, authentication: Authentication): ResponseEntity<Void> {
        // Revoke refresh token
        try {
            refreshTokenService.revokeToken(request.refreshToken)
        } catch (e: Exception) {
            // Token not found or already revoked
        }

        return ResponseEntity.ok().build()
    }

    @PostMapping("/logout-all")
    fun logoutAll(authentication: Authentication): ResponseEntity<Void> {
        val userDetails = authentication.principal as EmployeeUserDetails
        refreshTokenService.revokeAllUserTokens(userDetails.employee.login)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/me")
    fun getCurrentUser(authentication: Authentication): ResponseEntity<Map<String, Any?>> {
        val userDetails = authentication.principal as EmployeeUserDetails
        
        val response = mapOf(
            "login" to userDetails.employee.login,
            "fullName" to userDetails.employee.fullName,
            "role" to userDetails.employee.roleId,
            "position" to userDetails.employee.position,
            "leadId" to userDetails.employee.leadId
        )

        return ResponseEntity.ok(response)
    }
}