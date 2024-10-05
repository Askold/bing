package ru.silonov.bing.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.silonov.bing.dto.ObjectDto
import ru.silonov.bing.mapper.HydroObjectMapper
import ru.silonov.bing.model.dictionaries.HydroObject
import ru.silonov.bing.repository.HydroObjectRepository
import ru.silonov.bing.repository.ResponsibilityClassRepository
import java.util.*
import kotlin.NoSuchElementException

@Service
class ObjectService(
    private val responsibilityClassRepository: ResponsibilityClassRepository,
    private val objectRepository: HydroObjectRepository,
    private val hydroObjectMapper: HydroObjectMapper
) {

    @Transactional
    fun create(objectDto: ObjectDto) {
        val responsibility = responsibilityClassRepository.findById(objectDto.classId)
            .orElseThrow { NoSuchElementException("ResponsibilityClass not found with id: ${objectDto.classId}") }
        val entity = HydroObject(id = objectDto.id, name = objectDto.name, responsibilityClass = responsibility)

        objectRepository.save(entity)
    }

    @Transactional
    fun delete(id: UUID) {
        objectRepository.deleteById(id)
    }

    @Transactional(readOnly = true)
    fun getAllObjects(): List<ObjectDto> {
        val objects = objectRepository.findAll()
        return objects.map { obj -> hydroObjectMapper.toDto(obj) }
    }
}
