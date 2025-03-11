package ru.silonov.bing.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.silonov.bing.dto.report.ReportDto
import ru.silonov.bing.mapper.ReportMapper
import ru.silonov.bing.repository.ReportRepository
import java.util.*

@Service
class ReportService(
    private val reportRepository: ReportRepository,
    private val reportMapper: ReportMapper
) {

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
