package ru.silonov.bing.factory

import org.springframework.stereotype.Component
import ru.silonov.bing.calculator.TemplateValuesCalculator.getCriteriaPriorityMap
import ru.silonov.bing.calculator.TemplateValuesCalculator.getCriteriesRating
import ru.silonov.bing.calculator.TemplateValuesCalculator.getCriteriesRatingFinal
import ru.silonov.bing.calculator.TemplateValuesCalculator.getSignificanceCoefficients
import ru.silonov.bing.dto.template.CreateTemplateRequestDTO
import ru.silonov.bing.model.fillers.Template
import ru.silonov.bing.model.linkers.TemplateCriteriaScenario
import ru.silonov.bing.repository.CriteriaRepository
import ru.silonov.bing.repository.ScenarioRepository
import java.util.NoSuchElementException

@Component
class TemplateCriteriaScenariosFactory(
    private val scenarioRepository: ScenarioRepository,
    private val criteriaRepository: CriteriaRepository
) {
    fun getTemplateCriteriaScenarioList(template: Template, templateDto: CreateTemplateRequestDTO): MutableList<TemplateCriteriaScenario> {
        val criteriaPriorityMap = getCriteriaPriorityMap(templateDto.criteriaScenarios.map { it.rank })

        return templateDto.criteriaScenarios.map { criteriaDTO ->
            val significanceCoefficients = getSignificanceCoefficients(criteriaDTO.rank, criteriaPriorityMap)
            val criteria = criteriaRepository.findById(criteriaDTO.criteriaId).orElseThrow()
            { NoSuchElementException("Criteria not found with id: ${criteriaDTO.criteriaId}") }
            val criteriesRating = getCriteriesRating(criteriaDTO.factValue, significanceCoefficients, criteria)

            TemplateCriteriaScenario(
                templateId = template,
                criteriaId = criteria,
                scenario = scenarioRepository.findById(criteriaDTO.scenarioId).orElseThrow()
                { NoSuchElementException("Scenario not found with id: ${criteriaDTO.scenarioId}") },
                rank = criteriaDTO.rank,
                criteriesRating = criteriesRating,
                significanceCoefficient = significanceCoefficients,
                criteriesRatingFinal = getCriteriesRatingFinal(criteriesRating, significanceCoefficients),
                factValue = criteriaDTO.factValue
            )
        }.toMutableList()
    }
}