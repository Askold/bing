package ru.silonov.bing.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import ru.silonov.bing.dto.report.ReportDto
import ru.silonov.bing.model.fillers.Report

@Mapper(componentModel = "spring")
interface ReportMapper {
    @Mapping(target = "assessments", ignore = true)
    fun toDto(entity: Report): ReportDto
}
