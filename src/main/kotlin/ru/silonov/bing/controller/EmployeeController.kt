package ru.silonov.bing.controller

import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.silonov.bing.dto.CreateEmployeeRequestDto
import ru.silonov.bing.dto.EmployeeDto
import ru.silonov.bing.service.EmployeeService

@RestController
@RequestMapping("/employee")
class EmployeeController(
    private val employeeService: EmployeeService
) {

    // 5.12
    @PostMapping
    @Operation(summary = "5.12 Создание нового пользователя")
    fun createEmployee(@RequestBody employeeDto: CreateEmployeeRequestDto): ResponseEntity<EmployeeDto> {
        val createdEmployee = employeeService.createEmployee(employeeDto)
        return ResponseEntity.ok(createdEmployee)
    }

    // 5.13
    @GetMapping("/{login}")
    @Operation(summary = "5.13 Получение данных по сотруднику")
    fun getEmployeeByLogin(@PathVariable login: String): ResponseEntity<EmployeeDto> {
        val employee = employeeService.getEmployeeByLogin(login)
        return ResponseEntity.ok(employee)
    }
}
