package ru.silonov.bing.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.silonov.bing.model.fillers.Employee

interface EmployeeRepository : JpaRepository<Employee, String> {
    fun findByLogin(login: String): Employee?
}
