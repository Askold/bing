package ru.silonov.bing.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.silonov.bing.dto.TemplateDto
import ru.silonov.bing.dto.template.CreateTemplateRequestDTO
import ru.silonov.bing.dto.template.CreateTemplateResponseDTO
import ru.silonov.bing.mapper.TemplateMapper
import ru.silonov.bing.model.linkers.CriteriaTemplate
import ru.silonov.bing.repository.CriteriaRepository
import ru.silonov.bing.repository.CriteriaTemplateRepository
import ru.silonov.bing.repository.HydroObjectRepository
import ru.silonov.bing.repository.TemplateRepository
import java.util.*

@Service
class TemplateService(
    private val hydroObjectRepository: HydroObjectRepository,
    private val criteriaTemplateRepository: CriteriaTemplateRepository,
    private val templateRepository: TemplateRepository,
    private val criteriaRepository: CriteriaRepository,
    private val templateMapper: TemplateMapper
) {

    @Transactional(readOnly = true)
    fun getAllTemplates(): List<TemplateDto> {
        return templateRepository.findAll().map { template -> templateMapper.toDto(template) }
    }

    @Transactional
    fun createTemplate(templateDto: CreateTemplateRequestDTO): CreateTemplateResponseDTO {
        val hydroObject = hydroObjectRepository.findById(templateDto.objectId)
            .orElseThrow {NoSuchElementException("HydroObject not found with id: $templateDto.objectId")}
        var template = templateMapper.toEntity(templateDto)
        template.objectId = hydroObject
        template = templateRepository.save(template)

        val criteriaTemplates: List<CriteriaTemplate> = templateDto.criteries.map {
            criteriaDTO -> CriteriaTemplate(
                templateId = template,
                criteriaId = criteriaRepository.findById(criteriaDTO.id).orElseThrow()
                     {NoSuchElementException("Criteria not found with id: $templateDto.objectId")},
                rank = criteriaDTO.rank
            )
        }
        criteriaTemplateRepository.saveAll(criteriaTemplates)

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
