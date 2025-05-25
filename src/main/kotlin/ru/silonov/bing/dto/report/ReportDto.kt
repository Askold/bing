package ru.silonov.bing.dto.report

import ru.silonov.bing.dto.assessment.GetAssessmentResponseDto
import java.time.LocalDateTime
import java.util.UUID

data class ReportDto(
    val id: UUID,
    val objectId: UUID,
    val jsonValues: String? = null,
    val authorLogin: String,
    val createdAt: LocalDateTime,
    val modifiedAt: LocalDateTime,
    var assessments: List<GetAssessmentResponseDto>
)