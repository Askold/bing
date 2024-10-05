package ru.silonov.bing.dto

import java.util.UUID

data class ObjectDto(
    val id: UUID,
    val name: String,
    val classId: UUID
)