package ru.silonov.bing.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.silonov.bing.dto.criteria.CriteriaDto
import ru.silonov.bing.mapper.CriteriaMapper
import ru.silonov.bing.model.dictionaries.Criteria
import ru.silonov.bing.repository.CriteriaGroupRepository
import ru.silonov.bing.repository.CriteriaRepository
import ru.silonov.bing.repository.TemplateCriterioScenarioRepository
import java.util.UUID

@Service
class CriteriaService(
    private val templateCriterioScenarioRepository: TemplateCriterioScenarioRepository,
    private val criteriaGroupRepository: CriteriaGroupRepository,
    private val criteriaRepository: CriteriaRepository,
    private val criteriaMapper: CriteriaMapper
) {

    @Transactional(readOnly = true)
    fun findAll(templateId: UUID?): List<CriteriaDto> {
        val criteriaList: List<Criteria> =
            if (templateId == null) criteriaRepository.findAll()
            else templateCriterioScenarioRepository.findAllByTemplateId(templateId)
                .map { linker -> linker.criteriaId }

        return criteriaList.map { criteria -> criteriaMapper.toDto(criteria) }
    }

    @Transactional
    fun create(criteria: CriteriaDto) {
        val entity = criteriaMapper.toEntity(criteria)
        val group = criteriaGroupRepository.findById(criteria.criteriesGroupId)
            .orElseThrow { NoSuchElementException("Group not found with id: $criteria.criteriesGroupId") }
        entity.criteriaGroup = group

        criteriaRepository.save(entity)
    }

    @Transactional
    fun delete(id: UUID) {
        criteriaRepository.deleteById(id)
    }
}