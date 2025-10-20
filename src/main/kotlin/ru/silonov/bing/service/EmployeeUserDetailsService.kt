package ru.silonov.bing.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import ru.silonov.bing.authorization.EmployeeUserDetails
import ru.silonov.bing.repository.EmployeeRepository

@Service
class EmployeeUserDetailsService(
    private val employeeRepository: EmployeeRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val employee = employeeRepository.findByLogin(username)
            ?: throw UsernameNotFoundException("Employee not found with login: $username")

        return EmployeeUserDetails(employee)
    }
}