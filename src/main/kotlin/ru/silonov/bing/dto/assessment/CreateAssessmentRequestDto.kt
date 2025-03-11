package ru.silonov.bing.dto.assessment

import java.util.UUID

data class CreateAssessmentRequestDto(
    val templateId: UUID,
    val authorLogin: String,
    val criteriaScenario: List<CriteriaScenario>
)

data class CriteriaScenario(
    val criteriaId: UUID,
    val rank: Int,
    val scenarioId: UUID,
    val significanceCoefficient: Float,
    val criteriaRating: Float,
    val criteriaRatingFinal: Float,
    val isCriteriaRatingFinalManual: Boolean,
    val factValue: String
)