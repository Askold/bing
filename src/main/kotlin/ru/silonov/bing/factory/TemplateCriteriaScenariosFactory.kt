package ru.silonov.bing.factory

import org.springframework.stereotype.Component
import ru.silonov.bing.calculator.TemplateValuesCalculator.getCriteriaPriorityMap
import ru.silonov.bing.calculator.TemplateValuesCalculator.getCriteriesRating
import ru.silonov.bing.calculator.TemplateValuesCalculator.getCriteriesRatingFinal
import ru.silonov.bing.calculator.TemplateValuesCalculator.getSignificanceCoefficients

import ru.silonov.bing.dto.template.CreateTemplateRequestDTO

import ru.silonov.bing.model.TemplateUniqueKey
import ru.silonov.bing.model.fillers.Template
import ru.silonov.bing.model.linkers.TemplateCriteriaScenario
import ru.silonov.bing.service.CriteriaService
import ru.silonov.bing.service.ScenarioService

@Component
class TemplateCriteriaScenariosFactory(
    private val scenarioService: ScenarioService,
    private val criteriaService: CriteriaService
) {
    fun getTemplateCriteriaScenarioList(template: Template, templateDto: CreateTemplateRequestDTO): MutableList<TemplateCriteriaScenario> {
        val criteriaPriorityMap = getCriteriaPriorityMap(templateDto.criteriaScenarios.map { it.rank })

        return templateDto.criteriaScenarios.map {
            TemplateCriteriaScenario(
                uniqueKey = TemplateUniqueKey(template, criteriaService.getById(it.criteriaId), scenarioService.getById(it.scenarioId)),
                rank = it.rank,
                significanceCoefficient = getSignificanceCoefficients(it.rank, criteriaPriorityMap)
            )
        }.toMutableList()
    }
}