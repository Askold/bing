package ru.silonov.bing.dto.scenario

import ru.silonov.bing.dto.CriteriaDto

data class ScenarioListDto(
    val name: String? = null,
    val scenarioGroupId: String,
    val scenaries: List<ScenarioDto>
)