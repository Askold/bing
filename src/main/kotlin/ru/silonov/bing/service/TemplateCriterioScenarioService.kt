package ru.silonov.bing.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.silonov.bing.decorator.TemplateCriteriaScenarioDecorator.calculateWithFactValue
import ru.silonov.bing.dto.assessment.CreateAssessmentRequestDto
import ru.silonov.bing.model.TemplateUniqueKey
import ru.silonov.bing.model.linkers.TemplateCriteriaScenario
import ru.silonov.bing.repository.TemplateCriterioScenarioRepository

@Service
class TemplateCriterioScenarioService(
    private val templateCriterioScenarioRepository: TemplateCriterioScenarioRepository,
    private val templateService: TemplateService,
    private val scenarioService: ScenarioService,
    private val criteriaService: CriteriaService
) {

    @Transactional(readOnly = true)
    fun getByUnique(unique: TemplateUniqueKey): TemplateCriteriaScenario = templateCriterioScenarioRepository.findByUniqueKey(unique)
        .orElseThrow { NoSuchElementException("TemplateCriteriaService not found with uniqueKey: ") }

    @Transactional
    fun updateAllByRequestAndReturn(request: CreateAssessmentRequestDto): List<TemplateCriteriaScenario> {
        val template = templateService.getById(request.templateId)

        val templateCriteriaScenarios = request.criteriaScenario.map {
            getByUnique(
                TemplateUniqueKey(
                    template = template,
                    criteria = criteriaService.getById(it.criteriaId),
                    scenario = scenarioService.getById(it.scenarioId)
                )
            ).calculateWithFactValue(it.factValue)
        }

        return templateCriterioScenarioRepository.saveAll(templateCriteriaScenarios)
    }
}