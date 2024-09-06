package ru.silonov.bing.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.silonov.bing.dto.ObjectDto
import ru.silonov.bing.service.ObjectService

@Tag(name = "Object", description = "Работа с объектами")
@RestController
@RequestMapping("/object")
class ObjectController(
    private val objectService: ObjectService
) {

    // 5.2
    @Operation(summary = "5.2 Получение списка объектов")
    @GetMapping("/list")
    fun getAllObjects(): ResponseEntity<List<ObjectDto>> {
        val objects = objectService.getAllObjects()
        return ResponseEntity.ok(objects)
    }
}
