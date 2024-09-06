package ru.silonov.bing.dto

import java.time.LocalDateTime

data class TemplateDto(
    val id: String,
    val objectId: String,
    val authorLogin: String,
    val createdAt: LocalDateTime,
    val modifiedAt: LocalDateTime
)