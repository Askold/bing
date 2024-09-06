package ru.silonov.bing.service

import org.springframework.stereotype.Service
import ru.silonov.bing.dto.CriteriaDto
import ru.silonov.bing.mapper.CriteriaMapper
import ru.silonov.bing.model.dictionaries.Criteria
import ru.silonov.bing.repository.CriteriaRepository

@Service
class CriteriaService(
    private val criteriaRepository: CriteriaRepository,
    private val criteriaMapper: CriteriaMapper
) {
    fun findAll(): List<CriteriaDto> {
        return criteriaRepository.findAll().map {
            criteria -> criteriaMapper.toDto(criteria)
        }
    }
}