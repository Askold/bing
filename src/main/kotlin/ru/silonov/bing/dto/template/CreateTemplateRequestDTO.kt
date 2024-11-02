package ru.silonov.bing.dto.template

import java.util.*

data class CreateTemplateRequestDTO(
    val id: UUID? = null,
    val objectId: UUID,
    val authorLogin: String,
    val name: String,
    val classId: UUID,
    val criteriaScenarios: List<CriteriaScenarioDTO>
)

data class CriteriaScenarioDTO(
    val criteriaId: UUID,
    val scenarioId: UUID,
    val rank: Int
)
