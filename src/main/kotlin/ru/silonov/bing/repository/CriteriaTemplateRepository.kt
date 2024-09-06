package ru.silonov.bing.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.silonov.bing.model.fillers.Template
import ru.silonov.bing.model.linkers.CriteriaTemplate
import java.util.UUID

interface CriteriaTemplateRepository : JpaRepository<CriteriaTemplate, UUID>
