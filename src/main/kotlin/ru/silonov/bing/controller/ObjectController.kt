package ru.silonov.bing.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.silonov.bing.dto.ObjectDto
import ru.silonov.bing.service.ObjectService
import java.util.UUID

@Tag(name = "Object", description = "Работа с объектами")
@RestController
@RequestMapping("/object")
class ObjectController(
    private val objectService: ObjectService
) {

    // 5.2
    @GetMapping("/list")
    @Operation(summary = "5.2 Получение списка объектов")
    fun getAllObjects(): ResponseEntity<List<ObjectDto>> {
        val objects = objectService.getAllObjects()
        return ResponseEntity.ok(objects)
    }

    @Operation(summary = "Создание объекта")
    @PostMapping
    fun createObject(@RequestBody requestBody: ObjectDto): ResponseEntity<Void> {
        objectService.create(requestBody)
        return ResponseEntity.ok().build()
    }

    @Operation(summary = "Удаление объекта")
    @DeleteMapping("/{criteriaId}")
    fun deleteObject(@PathVariable criteriaId: UUID): ResponseEntity<Void> {
        objectService.delete(criteriaId)
        return ResponseEntity.ok().build()
    }
}
