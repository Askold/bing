package ru.silonov.bing.dto.template

import java.util.*

data class CreateTemplateRequestDTO(
    val objectId: UUID,
    val authorLogin: String,
    val name: String,
    val criteries: List<CriteriaDTO>
)

data class CriteriaDTO(
    val id: UUID,
    val rank: Int
)
