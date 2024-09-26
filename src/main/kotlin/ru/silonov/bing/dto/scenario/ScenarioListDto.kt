package ru.silonov.bing.dto.scenario

data class ScenarioListDto(
    val name: String? = null,
    val scenarioGroupId: String,
    val scenaries: List<ScenarioDto>
)