package ru.silonov.bing.service

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.silonov.bing.dto.CreateEmployeeRequestDto
import ru.silonov.bing.dto.EmployeeDto
import ru.silonov.bing.dto.LoginRequestDto
import ru.silonov.bing.mapper.EmployeeMapper
import ru.silonov.bing.repository.EmployeeRepository
import ru.silonov.bing.security.JwtTokenProvider

@Service
class EmployeeService(
    private val employeeRepository: EmployeeRepository,
    private val employeeMapper: EmployeeMapper,
    private val authenticationManager: AuthenticationManager,
    private val jwtTokenProvider: JwtTokenProvider,
    private val passwordEncoder: PasswordEncoder
) {

    @Transactional
    fun createEmployee(request: CreateEmployeeRequestDto): EmployeeDto {
        val employee = employeeMapper.toEntity(request)
        employee.password = passwordEncoder.encode(request.password)
        val savedEmployee = employeeRepository.save(employee)

        return employeeMapper.toDto(savedEmployee)
    }

    @Transactional(readOnly = true)
    fun getEmployeeByLogin(login: String): EmployeeDto {
        return employeeMapper.toDto(employeeRepository.findByLogin(login)
            .orElseThrow { UsernameNotFoundException("Employee not found with login: $login") })
    }


    fun login(request: LoginRequestDto): String {
        // Authenticate the user
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(request.login, request.password)
        )

        val userDetails = authentication.principal as UserDetails
        val roles = userDetails.authorities.map { it.authority }

        // Generate a JWT token
        return jwtTokenProvider.createToken(userDetails.username, roles)
    }
}
