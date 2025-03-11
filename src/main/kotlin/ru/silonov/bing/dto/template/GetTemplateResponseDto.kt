package ru.silonov.bing.dto.template

import java.util.UUID

data class GetTemplateResponseDto(
    val objectId: UUID,
    val authorLogin: String,
    val name: String,
    val classId: UUID,
    val criteriaScenario: List<GetCriteriaScenarioDto>
)

data class GetCriteriaScenarioDto(
    val criterioId: UUID,
    val rank: Int,
    val scenarioId: UUID
)