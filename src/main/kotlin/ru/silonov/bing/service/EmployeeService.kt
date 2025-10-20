package ru.silonov.bing.service

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.silonov.bing.dto.CreateEmployeeRequestDto
import ru.silonov.bing.dto.EmployeeDto
import ru.silonov.bing.dto.authentication.AuthenticationRequest
import ru.silonov.bing.mapper.EmployeeMapper
import ru.silonov.bing.repository.EmployeeRepository

@Service
class EmployeeService(
    private val passwordEncoder: PasswordEncoder,
    private val employeeRepository: EmployeeRepository,
    private val employeeMapper: EmployeeMapper
) {

    @Transactional
    fun createEmployee(createEmployeeRequestDto: CreateEmployeeRequestDto): EmployeeDto {
        val employee = employeeMapper.toEntity(createEmployeeRequestDto)
        employee.password = passwordEncoder.encode(employee.password)
        val savedEmployee = employeeRepository.save(employee)
        return employeeMapper.toDto(savedEmployee)
    }

    @Transactional(readOnly = true)
    fun getEmployeeByLogin(login: String): EmployeeDto {
        return employeeMapper.toDto(employeeRepository.findByLogin(login)
            ?:throw NoSuchElementException("Employee not found with login: $login"))
    }

    fun login(loginRequestDto: AuthenticationRequest): Boolean {
        val employee = employeeRepository.findByLogin(loginRequestDto.login)
            ?:throw NoSuchElementException("Invalid login or password")

        if (employee.password == loginRequestDto.password) return true // Successful login logic
        else throw NoSuchElementException("Invalid login or password")
    }
}
