package ru.silonov.bing.mapper

import ru.silonov.bing.model.fillers.Template
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingTarget
import ru.silonov.bing.dto.TemplateDto
import ru.silonov.bing.dto.template.CreateTemplateRequestDTO
import ru.silonov.bing.dto.template.CreateTemplateResponseDTO

@Mapper(componentModel = "spring")
interface TemplateMapper {

    @Mapping(target = "objectId", source = "objectId.id")
    fun toDto(entity: Template): TemplateDto

    fun toCreateDto(entity: Template): CreateTemplateResponseDTO

    @Mapping(target = "objectId", ignore = true)
    fun toEntity(dto: CreateTemplateRequestDTO): Template
}
