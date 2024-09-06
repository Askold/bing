package ru.silonov.bing.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.silonov.bing.dto.CriteriaDto
import ru.silonov.bing.service.CriteriaService

@RestController
@RequestMapping("/criteria")
@Tag(name = "Criteria", description = "Работа с критериями")
class CriteriaController(
    val criteriaService: CriteriaService
) {

    // 5.9
    @GetMapping("/{templateId}")
    @Operation(summary = "5.9 Получение критериев по шаблону")
    fun getByTemplateId(@PathVariable templateId: String): ResponseEntity<List<CriteriaDto>> {
        return ResponseEntity.ok(criteriaService.findAll())
    }
}