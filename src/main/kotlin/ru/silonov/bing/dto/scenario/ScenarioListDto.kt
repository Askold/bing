package ru.silonov.bing.dto.scenario

import java.util.UUID

data class ScenarioListDto(
    val name: String? = null,
    val scenarioGroupId: String,
    val scenaries: List<ScenarioDto>
)