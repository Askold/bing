package ru.silonov.bing.service

import mu.KotlinLogging
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import ru.silonov.bing.dto.assessment.CreateAssessmentRequestDto
import ru.silonov.bing.dto.report.ReportDto
import ru.silonov.bing.mapper.AssessmentMapper
import ru.silonov.bing.mapper.ReportMapper
import ru.silonov.bing.mapper.TemplateCriteriaScenarioMapper
import ru.silonov.bing.model.fillers.Report
import ru.silonov.bing.repository.ReportRepository
import java.io.ByteArrayOutputStream
import java.util.*
import java.util.stream.Collectors

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
    fun getAllReports(): List<ReportDto> = reportRepository.findAll().map { report -> reportMapper.toDto(report) }

    @Transactional
    fun deleteReport(id: UUID) {
        if (!reportRepository.existsById(id)) {
            throw NoSuchElementException("Report not found with id: $id")
        }
        reportRepository.deleteById(id)
    }

    @Transactional
    fun createFile(id: UUID): ByteArray {
        val report = reportRepository.findById(id).orElseThrow {
            NoSuchElementException("Report not found with id: $id")
        }
        val scenariosMap = report.assessments.map { it.scenarioId }.stream().collect(Collectors.groupingBy { it.scenarioGroupId })
        val resultMap = LinkedHashMap<String, String>()
        scenariosMap.forEach {
            resultMap[it.key.name] = it.key.dangerKoef.toString()
            it.value.forEach { scenario ->
                run {
                    resultMap[scenario.scenarioNumber.toString()] = scenario.name
                }
            }
        }

        val workbook: Workbook = XSSFWorkbook()
        val sheet: Sheet = workbook.createSheet("Результат")

        // Create header row
        val headerRow: Row = sheet.createRow(0)
        val headers = listOf("№", "Сценарии аварий")

        headers.forEachIndexed { index, header ->
            val cell = headerRow.createCell(index)
            cell.setCellValue(header)
            val style: CellStyle = workbook.createCellStyle()
            val font: Font = workbook.createFont()
            font.bold = true
            style.setFont(font)
            cell.cellStyle = style
        }

        var rowIndex = 0
        resultMap.forEach {
            rowIndex++
            val row: Row = sheet.createRow(rowIndex)
            val cellNumber = row.createCell(0)
            cellNumber.setCellValue(it.key)
            val cellName = row.createCell(1)
            cellName.setCellValue(it.value)
        }

        return ByteArrayOutputStream().use { outputStream ->
            workbook.write(outputStream)
            outputStream.toByteArray()
        }.also {
            workbook.close()
        }
    }
}
