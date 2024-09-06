package ru.silonov.bing.dto

data class EmployeeDto(
    val login: String,
    val fullname: String,
    val roleId: String,
    val position: String,
    val leadId: String
)