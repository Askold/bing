package ru.silonov.bing.dto.template

import java.time.LocalDateTime
import java.util.UUID

data class TemplateDto(
    val id: String,
    val objectId: String,
    val authorLogin: String,
    val createdAt: LocalDateTime,
    val modifiedAt: LocalDateTime,
    val classId: UUID
)