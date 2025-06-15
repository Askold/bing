package ru.silonov.bing.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import ru.silonov.bing.dto.assessment.GetAssessmentResponseDto
import ru.silonov.bing.dto.report.CriteriaScenarioReportDTO
import ru.silonov.bing.model.fillers.Assessment
import ru.silonov.bing.model.linkers.TemplateCriteriaScenario

@Mapper(componentModel = "spring")
interface TemplateCriteriaScenarioMapper {

    @Mapping(target = "criterioId", source = "uniqueKey.criteria.id")
    @Mapping(target = "scenarioId", source = "uniqueKey.scenario.id")
    @Mapping(target = "assessmentId", source = "assessment.id")
    fun toDto(entity: TemplateCriteriaScenario): CriteriaScenarioReportDTO
}