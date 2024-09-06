package ru.silonov.bing.dto.report

import java.util.UUID

data class CreateReportRequestDto(
    val templateId: UUID,
    val objectId: UUID,
    val authorLogin: String
)