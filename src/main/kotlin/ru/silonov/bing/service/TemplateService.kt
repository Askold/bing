package ru.silonov.bing.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.silonov.bing.dto.CreateEmployeeRequestDto
import ru.silonov.bing.dto.template.*
import ru.silonov.bing.factory.TemplateCriteriaScenariosFactory
import ru.silonov.bing.mapper.TemplateMapper
import ru.silonov.bing.model.fillers.Template
import ru.silonov.bing.repository.HydroObjectRepository
import ru.silonov.bing.repository.ResponsibilityClassRepository
import ru.silonov.bing.repository.TemplateCriterioScenarioRepository
import ru.silonov.bing.repository.TemplateRepository
import java.util.*

@Service
class TemplateService(
    private val templateCriteriaScenariosFactory: TemplateCriteriaScenariosFactory,
    private val responsibilityClassRepository: ResponsibilityClassRepository,
    private val hydroObjectRepository: HydroObjectRepository,
    private val templateRepository: TemplateRepository,
    private val templateMapper: TemplateMapper
) {

    @Transactional(readOnly = true)
    fun getAllTemplates(): List<TemplateDto> =
        templateRepository.findAll().map { template -> templateMapper.toDto(template) }

    @Transactional(readOnly = true)
    fun getById(id: UUID): GetTemplateResponseDto =
        templateRepository.findById(id).map { template ->
            GetTemplateResponseDto(
                objectId = template.objectId?.id!!,
                authorLogin = template.authorLogin,
                name = template.name,
                classId = template.classId?.id!!,
                criteriaScenario = template.templateCriteriaScenarios.map { criteriaScenario ->
                    GetCriteriaScenarioDto(
                        criterioId = criteriaScenario.criteriaId.id,
                        rank = criteriaScenario.rank!!,
                        scenarioId = criteriaScenario.scenario.id!!,
                        significanceCoefficient = criteriaScenario.significanceCoefficient,
                        criteriaRating = criteriaScenario.criteriesRating,
                        criteriaRatingFinal = criteriaScenario.criteriesRatingFinal,
                        factValue = criteriaScenario.factValue!!
                    )
                }
            )
        }.orElseThrow { NoSuchElementException("Template not found with id: $id") }

    @Transactional
    fun createTemplate(templateDto: CreateTemplateRequestDTO): CreateTemplateResponseDTO {
        val templateId = templateDto.id
        var template = templateId?.let {
            templateRepository.findById(templateId).orElseThrow()
            { NoSuchElementException("Template not found with id: $templateId") }
                .apply {
                    name = templateDto.name
                    authorLogin = templateDto.authorLogin
                }
        } ?: Template(
            name = templateDto.name,
            authorLogin = templateDto.authorLogin
        )

        template.apply {
            objectId = hydroObjectRepository.findById(templateDto.objectId)
                .orElseThrow { NoSuchElementException("HydroObject not found with id: ${templateDto.objectId}") }
            classId = responsibilityClassRepository.findById(templateDto.classId).orElseThrow()
            { NoSuchElementException("ResponsibilityClass not found with id: ${templateDto.classId}") }
        }

        template.templateCriteriaScenarios.clear()
//        templateId?.let { templateCriterioScenarioRepository.deleteAllByTemplateId(it) }
        template.templateCriteriaScenarios.addAll(
            templateCriteriaScenariosFactory.getTemplateCriteriaScenarioList(
                template,
                templateDto
            )
        )

        template = templateRepository.save(template)

        return (CreateTemplateResponseDTO(template.id.toString()))
    }


    @Transactional
    fun deleteTemplate(id: UUID) =
        if (!templateRepository.existsById(id))
            throw NoSuchElementException("Template not found with id: $id")
        else templateRepository.deleteById(id)
}
