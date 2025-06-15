package ru.silonov.bing.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.silonov.bing.dto.assessment.CreateAssessmentRequestDto
import ru.silonov.bing.dto.assessment.CreateAssessmentResponseDTO
import ru.silonov.bing.dto.report.ReportDto
import ru.silonov.bing.service.AssessmentService
import ru.silonov.bing.service.ReportService
import java.util.UUID

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
    fun getAllReports(): ResponseEntity<List<ReportDto>> {
        val reports = reportService.getAllReports()
        return ResponseEntity.ok(reports)
    }

    @GetMapping("/{id}")
    @Operation(summary = "5.6 Получение детального отчета")
    fun getReport(@PathVariable id: UUID): ResponseEntity<ReportDto> {
        return ResponseEntity.ok(reportService.getById(id))
    }

    // 5.7
    @PostMapping("/assessment")
    @Operation(summary = "5.7 Создание оценок")
    fun createAssessment(@RequestBody assessmentDto: CreateAssessmentRequestDto): ResponseEntity<CreateAssessmentResponseDTO> {
        val result  = assessmentService.calculateValuesAndSaveAssessment(assessmentDto)
        return ResponseEntity.ok(result)
    }

    // 5.10
    @DeleteMapping("/{id}")
    @Operation(summary = "5.10 Удаление отчета")
    fun deleteReport(@PathVariable id: UUID): ResponseEntity<Void> {
        reportService.deleteReport(id)
        return ResponseEntity.noContent().build()
    }
}
