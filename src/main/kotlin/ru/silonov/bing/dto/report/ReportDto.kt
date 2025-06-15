package ru.silonov.bing.dto.report

import ru.silonov.bing.dto.assessment.GetAssessmentResponseDto
import ru.silonov.bing.dto.template.CriteriaScenarioDTO
import java.time.LocalDateTime
import java.util.UUID

data class ReportDto(
    val id: UUID,
    val templateId: UUID,
    val authorLogin: String,
    var criteriaScenario: List<CriteriaScenarioReportDTO>,
    val objectId: UUID,
    val jsonValues: String? = null,
    val createdAt: LocalDateTime,
    val modifiedAt: LocalDateTime,
    var assessments: List<GetAssessmentResponseDto>
)