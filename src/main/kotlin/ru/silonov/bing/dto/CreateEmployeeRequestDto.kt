package ru.silonov.bing.dto

data class CreateEmployeeRequestDto(
    val fullName: String,
    val roleId: String,
    val position: String,
    val leadId: String
)