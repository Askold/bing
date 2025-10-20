package ru.silonov.bing.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.silonov.bing.model.RefreshToken
import ru.silonov.bing.exception.TokenException
import ru.silonov.bing.repository.RefreshTokenRepository
import java.time.Instant

@Service
class RefreshTokenService(
    private val refreshTokenRepository: RefreshTokenRepository
) {

    @Value("\${jwt.refresh-token.expiration:604800000}") // 7 days in milliseconds
    private var refreshTokenExpiration: Long = 604800000

    @Transactional
    fun createRefreshToken(login: String, token: String): RefreshToken {
        val expiryDate = Instant.now().plusMillis(refreshTokenExpiration)

        val refreshToken = RefreshToken(
            token = token,
            login = login,
            expiryDate = expiryDate
        )

        return refreshTokenRepository.save(refreshToken)
    }

    @Transactional(readOnly = true)
    fun findByToken(token: String): RefreshToken {
        return refreshTokenRepository.findByToken(token)
            ?: throw TokenException("Refresh token not found")
    }

    @Transactional
    fun verifyExpiration(token: RefreshToken): RefreshToken {
        if (token.revoked) {
            throw TokenException("Refresh token was revoked")
        }

        if (token.expiryDate.isBefore(Instant.now())) {
            refreshTokenRepository.delete(token)
            throw TokenException("Refresh token was expired")
        }

        return token
    }

    @Transactional
    fun revokeToken(token: String) {
        val refreshToken = findByToken(token)
        refreshToken.revoked = true
        refreshTokenRepository.save(refreshToken)
    }

    @Transactional
    fun revokeAllUserTokens(login: String) {
        refreshTokenRepository.revokeAllByLogin(login)
    }

    @Transactional
    fun deleteExpiredTokens() {
        refreshTokenRepository.deleteExpiredTokens(Instant.now())
    }
}