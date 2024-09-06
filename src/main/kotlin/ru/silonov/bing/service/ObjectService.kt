package ru.silonov.bing.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.silonov.bing.dto.ObjectDto
import ru.silonov.bing.mapper.HydroObjectMapper
import ru.silonov.bing.repository.HydroObjectRepository

@Service
class ObjectService(
    private val objectRepository: HydroObjectRepository,
    private val hydroObjectMapper: HydroObjectMapper
) {

    @Transactional(readOnly = true)
    fun getAllObjects(): List<ObjectDto> {
        val objects = objectRepository.findAll()
        return objects.map { obj -> hydroObjectMapper.toDto(obj) }
    }
}
