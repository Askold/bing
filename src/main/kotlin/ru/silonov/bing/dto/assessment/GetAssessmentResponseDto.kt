package ru.silonov.bing.dto.assessment

import java.util.*

data class GetAssessmentResponseDto(
    val technicalState: Double? = null,
    val correctionFactorValue: Double? = null,
    val technicalStateWithCorrection: Double? = null,
    val finalTechnicalState: String? = null,
    val termOfUseState: Double? = null,
    val termOfUseFactor: Double? = null,
    val constructionStateWithoutE3: Double? = null,
    val constructionStateWithE3: Double? = null,
    val safetyStateWithoutE3: Double? = null,
    val safetyStateWithE3: Double? = null,
    val dangerAccidentFactor: Double? = null,
    val safetyScenarioGroupState: Double? = null,
    val finalSafetyLevel: String? = null,
    val accidentProbability: Double? = null,
    val objectId: UUID,
    val scenarioId: UUID,
    val createdBy: String? = null,
    val templateId: UUID
)