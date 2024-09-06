package ru.silonov.bing.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers
import ru.silonov.bing.dto.ObjectDto
import ru.silonov.bing.model.dictionaries.HydroObject

@Mapper(componentModel = "spring")
interface HydroObjectMapper {

    @Mapping(target = "classId", source = "responsibilityClass.id")
    fun toDto(entity: HydroObject): ObjectDto
}
