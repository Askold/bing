package ru.silonov.bing.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.silonov.bing.dto.ResponsibilityClassDto
import ru.silonov.bing.dto.criteria.CriteriaDto
import ru.silonov.bing.service.ResponsibilityClassService

@RestController
@RequestMapping("/class")
@Tag(name = "ResponsibilityClass", description = "Работа с классами ответственности")
class ResponsibilityClassController(
    private val service: ResponsibilityClassService
) {

    @GetMapping("/all")
    @Operation(summary = "5.9 Получение критериев по шаблону")
    fun getByTemplateId(): ResponseEntity<List<ResponsibilityClassDto>> {
        return ResponseEntity.ok(service.findAll())
    }
}