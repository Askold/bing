package ru.silonov.bing.mapper

import org.mapstruct.Mapper
import ru.silonov.bing.dto.CreateEmployeeRequestDto
import ru.silonov.bing.dto.EmployeeDto
import ru.silonov.bing.model.fillers.Employee

@Mapper(componentModel = "spring")
interface EmployeeMapper {
    fun toDto(entity: Employee): EmployeeDto
    fun toEntity(dto: CreateEmployeeRequestDto): Employee
}
