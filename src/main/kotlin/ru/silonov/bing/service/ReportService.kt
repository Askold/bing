package ru.silonov.bing.service

import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.silonov.bing.dto.assessment.CreateAssessmentRequestDto
import ru.silonov.bing.dto.report.ReportDto
import ru.silonov.bing.mapper.AssessmentMapper
import ru.silonov.bing.mapper.ReportMapper
import ru.silonov.bing.mapper.TemplateCriteriaScenarioMapper
import ru.silonov.bing.model.fillers.Report
import ru.silonov.bing.repository.ReportRepository
import java.util.*
import kotlin.math.log

@Service
class ReportService(
    private val templateCriteriaScenarioMapper: TemplateCriteriaScenarioMapper,
    private val assessmentMapper: AssessmentMapper,
    private val reportRepository: ReportRepository,
    private val templateService: TemplateService,
    private val reportMapper: ReportMapper
) {

    private val logger = KotlinLogging.logger {}

    @Transactional
    fun getByIdOrCreate(request: CreateAssessmentRequestDto): Report =
        if (request.reportId != null) reportRepository.findById(request.reportId).orElseThrow {
            NoSuchElementException("Report not found with id: ${request.reportId}")
        }
        else {
            val template = templateService.getById(request.templateId)
            reportRepository.save(
                Report
                    (
                    authorLogin = request.authorLogin,
                    template = template,
                    objectId = template.objectId!!
                )
            )
        }

    @Transactional(readOnly = true)
    fun getById(reportId: UUID): ReportDto {
        val report = reportRepository.findById(reportId).orElseThrow()
        { NoSuchElementException("Report not found with id: $reportId") }

        return reportMapper.toDto(report).apply {
            criteriaScenario = report.assessments.flatMap {
                it.templateCriteriaScenarios.map { template ->
                    templateCriteriaScenarioMapper.toDto(template)
                }
            }
            assessments = report.assessments.map { assessmentMapper.toDto(it) }
        }
    }


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

    @Transactional
    fun save(report: Report): Report = reportRepository.save(report).also { logger.info { "Report saved: $it" } }
}
