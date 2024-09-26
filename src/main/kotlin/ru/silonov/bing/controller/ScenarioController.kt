package ru.silonov.bing.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.silonov.bing.dto.scenario.ScenarioCreateDto
import ru.silonov.bing.dto.scenario.ScenarioListDto
import ru.silonov.bing.service.ScenarioService
import java.util.UUID

@RestController
@RequestMapping("/scenario")
@Tag(name = "Scenario", description = "Работа со сценариями")
class ScenarioController(
    private val scenarioService: ScenarioService
) {

    @GetMapping("/list")
    @Operation(summary = "5.3 Получение списка сценариев (с критериями)")
    fun getScenariosByObjectId(@RequestParam objectId: UUID): ResponseEntity<List<ScenarioListDto>> {
        val scenarios = scenarioService.getScenariosByObjectId(objectId)
        return ResponseEntity.ok(scenarios)
    }

    @PostMapping
    @Operation(summary = "Создание сценария")
    fun createScenario(@RequestBody requestBody: ScenarioCreateDto): ResponseEntity<Void> {
        scenarioService.create(requestBody)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "5.3 Получение списка сценариев (с критериями)")
    fun deleteScenario(@PathVariable id: UUID): ResponseEntity<Void> {
        scenarioService.delete(id)
        return ResponseEntity.ok().build()
    }
}
