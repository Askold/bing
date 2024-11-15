package ru.silonov.bing.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.silonov.bing.model.dictionaries.ResponsibilityClass
import ru.silonov.bing.model.linkers.TemplateCriteriaScenario
import java.util.*

interface TemplateCriterioScenarioRepository : JpaRepository<TemplateCriteriaScenario, UUID> {

    fun findAllByTemplateId(templateId: UUID): List<TemplateCriteriaScenario>
}