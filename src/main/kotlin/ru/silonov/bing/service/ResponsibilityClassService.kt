package ru.silonov.bing.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.silonov.bing.dto.ResponsibilityClassDto
import ru.silonov.bing.model.dictionaries.ResponsibilityClass
import ru.silonov.bing.repository.ResponsibilityClassRepository

@Service
class ResponsibilityClassService(
    private val repository: ResponsibilityClassRepository
) {

    @Transactional(readOnly = true)
    fun findAll(): List<ResponsibilityClassDto> = repository.findAll().stream().map { v ->
        ResponsibilityClassDto(
            name = v.name,
            significanceFactor = v.significanceFactor,
            correctionFactor = v.correctionFactor
        )
    }.toList()
}