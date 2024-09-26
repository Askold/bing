package ru.silonov.bing.dto.scenario

import java.util.UUID

data class ScenarioCreateDto(
    val name: String,
    val scenarioGroupId: UUID
)
