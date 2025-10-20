package ru.silonov.bing.authorization

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import ru.silonov.bing.model.fillers.Employee

class EmployeeUserDetails(val employee: Employee) : UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority> {
        val authorities = mutableListOf<GrantedAuthority>()
        
        // Add role-based authority
        authorities.add(SimpleGrantedAuthority("ROLE_${employee.roleId}"))
        
        // Add position-based authority if position exists
        employee.position?.let {
            authorities.add(SimpleGrantedAuthority("POSITION_$it"))
        }
        
        return authorities
    }

    override fun getPassword(): String = employee.password

    override fun getUsername(): String = employee.login

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}