package ru.silonov.bing.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.silonov.bing.dto.report.CreateReportRequestDto
import ru.silonov.bing.dto.report.ReportDto
import ru.silonov.bing.dto.report.ReportUpdateDto
import ru.silonov.bing.mapper.ReportMapper
import ru.silonov.bing.model.fillers.Report
import ru.silonov.bing.repository.HydroObjectRepository
import ru.silonov.bing.repository.ReportRepository
import ru.silonov.bing.repository.TemplateRepository
import java.util.UUID

@Service
class ReportService(
    private val hydroObjectRepository: HydroObjectRepository,
    private val templateRepository: TemplateRepository,
    private val reportRepository: ReportRepository,
    private val reportMapper: ReportMapper
) {

    @Transactional(readOnly = true)
    fun getAllReports(): List<ReportDto> {
        val reports = reportRepository.findAll()
        return reports.map { report -> reportMapper.toDto(report) }
    }

    @Transactional
    fun createReport(requestDto: CreateReportRequestDto): ReportDto {
        val hydroObject = hydroObjectRepository.findById(requestDto.objectId)
            .orElseThrow { NoSuchElementException("HydroObject not found with id: $requestDto.objectId") }
        val template = templateRepository.findById(requestDto.templateId)
            .orElseThrow { NoSuchElementException("Template not found with id: $requestDto.templateId") }
        val report = reportRepository.saveAndFlush(Report(
            objectId = hydroObject,
            template = template,
            authorLogin = requestDto.authorLogin,
        ))

        return reportMapper.toDto(report)
    }

    @Transactional
    fun updateReport(id: UUID, reportDto: ReportUpdateDto) {
        val report = reportRepository.findById(id)
            .orElseThrow { NoSuchElementException("Report not found with id: $id") }

        report.apply {
            //todo investigate
        }

        reportRepository.save(report)
    }

    @Transactional
    fun deleteReport(id: UUID) {
        if (!reportRepository.existsById(id)) {
            throw NoSuchElementException("Report not found with id: $id")
        }
        reportRepository.deleteById(id)
    }

//    fun generateReportFile(id: String): String {
//        val report = reportRepository.findById(id)
//            .orElseThrow { NoSuchElementException("Report not found with id: $id") }
//
//        val reportFile = generateFile(report)
//        return encodeFileToBase64(reportFile)
//    }
}
