package ru.silonov.bing.mapper

import ru.silonov.bing.model.fillers.Template
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import ru.silonov.bing.dto.template.TemplateDto
import ru.silonov.bing.dto.template.CreateTemplateRequestDTO
import ru.silonov.bing.dto.template.CreateTemplateResponseDTO

@Mapper(componentModel = "spring")
interface TemplateMapper {

    @Mapping(target = "objectId", source = "objectId.id")
    @Mapping(target = "classId", source = "classId.id")
    fun toDto(entity: Template): TemplateDto

    fun toCreateDto(entity: Template): CreateTemplateResponseDTO

    @Mapping(target = "objectId", ignore = true)
    @Mapping(target = "classId", ignore = true)
    @Mapping(target = "templateCriteriaScenarios", expression = "java(java.util.Collections.emptyList())")
    fun toEntity(dto: CreateTemplateRequestDTO): Template
}
