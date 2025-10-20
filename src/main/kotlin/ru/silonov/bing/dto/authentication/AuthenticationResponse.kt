package ru.silonov.bing.dto.authentication

data class AuthenticationResponse(
    val accessToken: String,
    val refreshToken: String,
    val login: String,
    val fullName: String,
    val role: String,
    val position: String?,
    val tokenType: String = "Bearer"
)