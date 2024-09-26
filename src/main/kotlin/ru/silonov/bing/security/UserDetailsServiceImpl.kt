package ru.silonov.bing.security

import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import ru.silonov.bing.repository.EmployeeRepository

@Service
class UserDetailsServiceImpl(
    private val employeeRepository: EmployeeRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {

        val employee = employeeRepository.findByLogin(username)
            .orElseThrow { UsernameNotFoundException("User not found with username: $username") }

        return User
            .withUsername(username)
            .password(employee.password)
            .roles("USER")
            .build()
    }

//    private fun getAuthorities(user: User): Collection<GrantedAuthority> {
//        return user.roles.map { role -> SimpleGrantedAuthority(role.name) }
//    }
}
