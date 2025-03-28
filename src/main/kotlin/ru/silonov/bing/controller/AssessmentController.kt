package ru.silonov.bing.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.silonov.bing.dto.assessment.CreateAssessmentRequestDto
import ru.silonov.bing.dto.assessment.CreateAssessmentResponseDTO
import ru.silonov.bing.service.AssessmentService

@RestController
@RequestMapping("/assessment")
@Tag(name = "Report", description = "Работа с оценками")
class AssessmentController (
    private val assessmentService: AssessmentService
){

    // 5.7
    @PostMapping
    @Operation(summary = "5.7 Создание оценок")
    fun createAssessment(@RequestBody assessmentDto: CreateAssessmentRequestDto): ResponseEntity<CreateAssessmentResponseDTO> {
        val result  = assessmentService.calculateValuesAndSaveAssessment(assessmentDto)
        return ResponseEntity.ok(result)
    }

}