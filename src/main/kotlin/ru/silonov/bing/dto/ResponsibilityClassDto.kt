package ru.silonov.bing.dto

import java.util.UUID

data class ResponsibilityClassDto(
    val id: UUID,
    val name: String,
    val termOfUseFactor: String,
    val correctionFactor: String
)
