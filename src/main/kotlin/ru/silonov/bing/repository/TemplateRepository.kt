package ru.silonov.bing.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.silonov.bing.model.fillers.Template
import java.util.*

interface TemplateRepository : JpaRepository<Template, UUID> {
}
