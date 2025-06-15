package ru.silonov.bing.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import ru.silonov.bing.dto.assessment.GetAssessmentResponseDto
import ru.silonov.bing.model.fillers.Assessment

@Mapper(componentModel = "spring")
interface AssessmentMapper {
    @Mapping(target = "objectId", source = "objectId.id")
    @Mapping(target = "scenarioId", source = "scenarioId.id")
    @Mapping(target = "templateId", source = "template.id")
    fun toDto(entity: Assessment): GetAssessmentResponseDto
}