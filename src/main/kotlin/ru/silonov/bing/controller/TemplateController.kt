package ru.silonov.bing.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import lombok.extern.slf4j.Slf4j
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.silonov.bing.dto.TemplateDto
import ru.silonov.bing.dto.template.CreateTemplateRequestDTO
import ru.silonov.bing.dto.template.CreateTemplateResponseDTO
import ru.silonov.bing.service.TemplateService
import java.util.UUID

@Slf4j
@RestController
@RequestMapping("/template")
@Tag(name = "Templates", description = "Работа с шаблонами")
class TemplateController(
    private val templateService: TemplateService
) {

    // 5.1
    @GetMapping("/list")
    @Operation(summary = "5.1 Получение списка шаблонов")
    fun getAllTemplates(): ResponseEntity<List<TemplateDto>> {
        val templates = templateService.getAllTemplates()
        return ResponseEntity.ok(templates)
    }

    // 5.4
    @PostMapping
    @Operation(summary = "5.4 Создание/Редактирование шаблона")
    fun createTemplate(@RequestBody templateDto: CreateTemplateRequestDTO): ResponseEntity<CreateTemplateResponseDTO> {
        val createdTemplate = templateService.createTemplate(templateDto)
        return ResponseEntity.ok(createdTemplate)
    }

    // 5.5
    @DeleteMapping("/{id}")
    @Operation(summary = "5.5 Удаление шаблона")
    fun deleteTemplate(@PathVariable id: UUID): ResponseEntity<Void> {
        templateService.deleteTemplate(id)
        return ResponseEntity.ok().build()
    }
}
