package ru.silonov.bing.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.silonov.bing.dto.report.CreateReportRequestDto
import ru.silonov.bing.dto.report.ReportDto
import ru.silonov.bing.dto.report.ReportUpdateDto
import ru.silonov.bing.service.ReportService
import java.util.UUID

@RestController
@RequestMapping("/report")
@Tag(name = "Report", description = "Работа с отчетами")
class ReportController(
    private val reportService: ReportService
) {

    // 5.6
    @GetMapping("/list")
    @Operation(summary = "5.6 Получение списка отчетов")
    fun getAllReports(): ResponseEntity<List<ReportDto>> {
        val reports = reportService.getAllReports()
        return ResponseEntity.ok(reports)
    }

    // 5.7
    @PostMapping
    @Operation(summary = "5.7 Создание отчета")
    fun createReport(@RequestBody reportDto: CreateReportRequestDto): ResponseEntity<ReportDto> {
        val createdReport = reportService.createReport(reportDto)
        return ResponseEntity.ok(createdReport)
    }

    // 5.8
    @PutMapping("/{id}")
    @Operation(summary = "5.8 Редактирование отчетов")
    fun updateReport(@PathVariable id: UUID, @RequestBody reportUpdateDto: ReportUpdateDto): ResponseEntity<Void> {
        reportService.updateReport(id, reportUpdateDto)
        return ResponseEntity(HttpStatus.OK)
    }

    // 5.10
    @DeleteMapping("/{id}")
    @Operation(summary = "5.10 Удаление отчета")
    fun deleteReport(@PathVariable id: UUID): ResponseEntity<Void> {
        reportService.deleteReport(id)
        return ResponseEntity.noContent().build()
    }
}
