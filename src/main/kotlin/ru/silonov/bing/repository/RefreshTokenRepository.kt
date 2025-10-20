package ru.silonov.bing.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import ru.silonov.bing.model.RefreshToken
import java.time.Instant

interface RefreshTokenRepository : JpaRepository<RefreshToken, Long> {

    fun findByToken(token: String): RefreshToken?

    fun findByLogin(login: String): List<RefreshToken>

    @Modifying
    @Query("UPDATE RefreshToken rt SET rt.revoked = true WHERE rt.login = :login")
    fun revokeAllByLogin(login: String): Int

    @Modifying
    @Query("DELETE FROM RefreshToken rt WHERE rt.expiryDate < :now")
    fun deleteExpiredTokens(now: Instant): Int
}