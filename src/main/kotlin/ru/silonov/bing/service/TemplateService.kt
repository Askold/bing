package ru.silonov.bing.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.silonov.bing.dto.template.TemplateDto
import ru.silonov.bing.dto.template.CreateTemplateRequestDTO
import ru.silonov.bing.dto.template.CreateTemplateResponseDTO
import ru.silonov.bing.mapper.TemplateMapper
import ru.silonov.bing.model.dictionaries.ResponsibilityClass
import ru.silonov.bing.model.fillers.Template
import ru.silonov.bing.model.linkers.TemplateCriteriaScenario
import ru.silonov.bing.repository.*
import java.util.*

@Service
class TemplateService(
    private val responsibilityClassRepository: ResponsibilityClassRepository,
    private val criteriaTemplateRepository: CriteriaTemplateRepository,
    private val hydroObjectRepository: HydroObjectRepository,
    private val scenarioRepository: ScenarioRepository,
    private val templateRepository: TemplateRepository,
    private val criteriaRepository: CriteriaRepository,
    private val templateMapper: TemplateMapper
) {

    @Transactional(readOnly = true)
    fun getAllTemplates(): List<TemplateDto> {
        return templateRepository.findAll().map { template -> templateMapper.toDto(template) }
    }

    @Transactional(readOnly = true)
    fun getById(id: UUID): TemplateDto {
        return templateRepository.findById(id).map { template -> templateMapper.toDto(template) }.orElseThrow()
        { NoSuchElementException("Template not found with id: $id") }
    }

    @Transactional
    fun createTemplate(templateDto: CreateTemplateRequestDTO): CreateTemplateResponseDTO {
        val templateId = templateDto.id
        var template: Template

        val hydroObject = hydroObjectRepository.findById(templateDto.objectId)
            .orElseThrow { NoSuchElementException("HydroObject not found with id: $templateDto.objectId") }
        val respClass = responsibilityClassRepository.findById(templateDto.classId).orElseThrow()
        { NoSuchElementException("ResponsibilityClass not found with id: ${templateDto.classId}") }

        if (templateId != null) {
            template = templateRepository.findById(templateId).orElseThrow()
            { NoSuchElementException("Template not found with id: $templateId") }
            template.apply {
                name = templateDto.name
                authorLogin = templateDto.authorLogin
                classId = respClass

            }
        } else {
            template = templateMapper.toEntity(templateDto)
            template.objectId = hydroObject
            template.classId = respClass

            val templateCriteriaScenarios: List<TemplateCriteriaScenario> = templateDto.criteriaScenarios.map {
            criteriaDTO -> TemplateCriteriaScenario(
                templateId = template,
                criteriaId = criteriaRepository.findById(criteriaDTO.criteriaId).orElseThrow()
                     {NoSuchElementException("Criteria not found with id: ${criteriaDTO.criteriaId}")},
                scenario = scenarioRepository.findById(criteriaDTO.scenarioId).orElseThrow()
                {NoSuchElementException("Scenario not found with id: ${criteriaDTO.scenarioId}")},
                rank = criteriaDTO.rank,
                criteriesRating = 3.0F,
                significanceCoefficient = 1.0F,
                criteriesRatingFinal = 3.0F
            )
        }
        criteriaTemplateRepository.saveAll(templateCriteriaScenarios)
        }

        template = templateRepository.save(template)

        return templateMapper.toCreateDto(template)
    }


    @Transactional
    fun deleteTemplate(id: UUID) {
        if (!templateRepository.existsById(id)) {
            throw NoSuchElementException("Template not found with id: $id")
        }
        templateRepository.deleteById(id)
    }
}
