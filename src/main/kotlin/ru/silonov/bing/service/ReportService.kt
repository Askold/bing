package ru.silonov.bing.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.silonov.bing.dto.report.ReportDto
import ru.silonov.bing.mapper.AssessmentMapper
import ru.silonov.bing.mapper.ReportMapper
import ru.silonov.bing.model.fillers.Report
import ru.silonov.bing.repository.ReportRepository
import java.util.*

@Service
class ReportService(
    private val assessmentMapper: AssessmentMapper,
    private val reportRepository: ReportRepository,
    private val reportMapper: ReportMapper
) {

    @Transactional
    fun getByIdOrCreate(reportId: UUID?, authorLogin: String): Report =
        if (reportId != null) reportRepository.findById(reportId).orElse(createWithLogin(authorLogin))
        else createWithLogin(authorLogin)

    @Transactional(readOnly = true)
    fun getById(reportId: UUID): ReportDto {
        val report = reportRepository.findById(reportId).orElseThrow()
        { NoSuchElementException("Report not found with id: $reportId") }

        return reportMapper.toDto(report).apply {
            assessments = report.assessments.map { assessmentMapper.toDto(it) }
        }
    }

    fun createWithLogin(authorLogin: String) = reportRepository.save(Report(authorLogin = authorLogin))

    @Transactional(readOnly = true)
    fun getAllReports(): List<ReportDto> {
        val reports = reportRepository.findAll()
        return reports.map { report -> reportMapper.toDto(report) }
    }

    @Transactional
    fun deleteReport(id: UUID) {
        if (!reportRepository.existsById(id)) {
            throw NoSuchElementException("Report not found with id: $id")
        }
        reportRepository.deleteById(id)
    }
}
