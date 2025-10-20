package ru.silonov.bing.dto.authentication

data class AuthenticationRequest(
    val login: String,
    val password: String
)