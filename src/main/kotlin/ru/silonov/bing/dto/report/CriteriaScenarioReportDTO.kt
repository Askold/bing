package ru.silonov.bing.dto.report

data class CriteriaScenarioReportDTO(
    val criterioId: String,
    val rank: Int,
    val scenarioId: String,
    val significanceCoefficient: Float,
    val criteriaRating: Float,
    val criteriaRatingFinal: Float,
    val isCriteriaRatingFinalManual: Boolean,
    val factValue: String,
    val assessmentId: String? = null
)
