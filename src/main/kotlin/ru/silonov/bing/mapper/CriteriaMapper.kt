package ru.silonov.bing.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers
import ru.silonov.bing.dto.CriteriaDto
import ru.silonov.bing.model.dictionaries.Criteria

@Mapper(componentModel = "spring")
interface CriteriaMapper {
    @Mapping(target = "criteriesGroupId", source = "criteriaGroup.id")
    @Mapping(target = "criteriesGroupFullName", source = "criteriaGroup.fullName")
    fun toDto(criteria: Criteria): CriteriaDto
}