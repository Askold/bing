package ru.silonov.bing.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import ru.silonov.bing.dto.report.CreateReportRequestDto
import ru.silonov.bing.dto.report.ReportDto
import ru.silonov.bing.model.dictionaries.HydroObject
import ru.silonov.bing.model.fillers.Report
import ru.silonov.bing.model.fillers.Template

@Mapper(componentModel = "spring")
interface ReportMapper {

    @Mapping(target = "objectId", source = "objectId.id")
    fun toDto(entity: Report): ReportDto
}
