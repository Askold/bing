package ru.silonov.bing.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.silonov.bing.dto.assessment.CreateAssessmentRequestDto
import ru.silonov.bing.dto.assessment.CreateAssessmentResponseDTO
import ru.silonov.bing.dto.report.ReportDto
import ru.silonov.bing.service.AssessmentService
import ru.silonov.bing.service.ReportService
import java.time.LocalDate
import java.time.format.DateTimeFormatter.ofPattern
import java.util.*

@RestController
@RequestMapping("/report")
@Tag(name = "Report", description = "Работа с отчетами")
class ReportController(
    private val assessmentService: AssessmentService,
    private val reportService: ReportService
) {

    // 5.6
    @GetMapping("/list")
    @Operation(summary = "5.6 Получение списка отчетов")
    fun getAllReports(): ResponseEntity<List<ReportDto>> = ResponseEntity.ok(reportService.getAllReports())

    @GetMapping("/{id}")
    @Operation(summary = "5.6 Получение детального отчета")
    fun getReport(@PathVariable id: UUID): ResponseEntity<ReportDto> = ResponseEntity.ok(reportService.getById(id))

    // 5.7
    @PostMapping("/assessment")
    @Operation(summary = "5.7 Создание оценок")
    fun createAssessment(@RequestBody assessmentDto: CreateAssessmentRequestDto): ResponseEntity<CreateAssessmentResponseDTO> {
        val result = assessmentService.calculateValuesAndSaveAssessment(assessmentDto)
        return ResponseEntity.ok(result)
    }

    // 5.10
    @DeleteMapping("/{id}")
    @Operation(summary = "5.10 Удаление отчета")
    fun deleteReport(@PathVariable id: UUID): ResponseEntity<Void> {
        reportService.deleteReport(id)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/file")
    @Operation(summary = "5.10 Удаление отчета")
    fun createFile(@RequestParam id: UUID): ResponseEntity<ByteArray> = ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=employees_${LocalDate.now().format(ofPattern("yyyyMMdd"))}.xlsx")
        .header(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
        .body(reportService.createFile(id))
}
