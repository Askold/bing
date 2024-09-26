package ru.silonov.bing.dto.criteria

import java.util.UUID

data class CriteriaDto(
    val id: UUID,
    val code: String,
    val fullName: String,
    val k1: String,
    val k2: String,
    val k3: String,
    val criteriesGroupId: UUID,
    val criteriesGroupFullName: String
)