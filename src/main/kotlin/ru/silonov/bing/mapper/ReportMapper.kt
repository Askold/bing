package ru.silonov.bing.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import ru.silonov.bing.dto.report.CriteriaScenarioReportDTO
import ru.silonov.bing.dto.report.ReportDto
import ru.silonov.bing.model.fillers.Assessment
import ru.silonov.bing.model.fillers.Report

@Mapper(componentModel = "spring")
interface ReportMapper {

    @Mapping(target = "assessments", ignore = true)
    @Mapping(target = "criteriaScenario", ignore = true)
    @Mapping(target = "objectId", source = "objectId.id")
    @Mapping(target = "templateId", source = "template.id")
    fun toDto(entity: Report): ReportDto
}
