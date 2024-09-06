package ru.silonov.bing.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.silonov.bing.model.fillers.Template
import java.util.UUID

interface TemplateRepository : JpaRepository<Template, UUID> {
    fun findByAuthorLogin(authorLogin: String): List<Template>
}
