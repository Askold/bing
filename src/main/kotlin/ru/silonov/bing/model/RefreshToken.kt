package ru.silonov.bing.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant

@Entity
@Table(name = "refresh_token", schema = "bing")
class RefreshToken(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "token", nullable = false, unique = true, length = 512)
    var token: String,

    @Column(name = "login", nullable = false)
    var login: String,

    @Column(name = "expiry_date", nullable = false)
    var expiryDate: Instant,

    @Column(name = "created_at", nullable = false)
    var createdAt: Instant = Instant.now(),

    @Column(name = "revoked", nullable = false)
    var revoked: Boolean = false
)