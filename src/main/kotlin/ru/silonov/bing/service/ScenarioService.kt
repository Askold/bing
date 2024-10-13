package ru.silonov.bing.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.silonov.bing.dto.scenario.ScenarioCreateDto
import ru.silonov.bing.dto.scenario.ScenarioDto
import ru.silonov.bing.dto.scenario.ScenarioListDto
import ru.silonov.bing.model.dictionaries.Scenario
import ru.silonov.bing.repository.HydroObjectRepository
import ru.silonov.bing.repository.ScenarioGroupRepository
import ru.silonov.bing.repository.ScenarioRepository
import java.util.*

@Service
class ScenarioService(
    private val scenarioGroupRepository: ScenarioGroupRepository,
    private val hydroObjectRepository: HydroObjectRepository,
    private val scenarioRepository: ScenarioRepository
) {

    @Transactional(readOnly = true)
    fun getScenariosByObjectId(objectId: UUID): List<ScenarioListDto> {
        val hydroObject = hydroObjectRepository.findById(objectId)
            .orElseThrow { NoSuchElementException("HydroObject not found with id: $objectId") }
        val scenarios = scenarioGroupRepository.findByObjectId(hydroObject)

        return scenarios.map { scenario ->
            ScenarioListDto(
                name = scenario.name,
                scenarioGroupId = scenario.id.toString(),
                scenaries = scenario.scenarios.map { value -> ScenarioDto(name = value.name) }
            )
        }
    }

    @Transactional
    fun create(scenario: ScenarioCreateDto) {
        val scenarioGroup = scenarioGroupRepository.findById(scenario.scenarioGroupId)
            .orElseThrow { NoSuchElementException("HydroObject not found with id: ${scenario.scenarioGroupId}") }

        scenarioRepository.save(
            Scenario(
                name = scenario.name,
                scenarioGroupId = scenarioGroup,
                scenarioNumber = scenario.scenarioNumber
            )
        )
    }

    @Transactional
    fun delete(id: UUID) {
        scenarioRepository.findById(id)
    }
}
