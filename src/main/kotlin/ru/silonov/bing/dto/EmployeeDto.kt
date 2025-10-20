package ru.silonov.bing.dto

data class EmployeeDto(
    val login: String,
    val fullName: String,
    val roleId: String,
    val position: String,
    val leadId: String
)