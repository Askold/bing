package ru.silonov.bing.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import ru.silonov.bing.model.TemplateUniqueKey
import ru.silonov.bing.model.dictionaries.ResponsibilityClass
import ru.silonov.bing.model.linkers.TemplateCriteriaScenario
import java.util.*

interface TemplateCriterioScenarioRepository : JpaRepository<TemplateCriteriaScenario, UUID> {

    @Query("select tcs from TemplateCriteriaScenario tcs where tcs.uniqueKey.template.id = :templateId")
    fun findAllByTemplateId(templateId: UUID): List<TemplateCriteriaScenario>

    fun findByUniqueKey(uniqueKey: TemplateUniqueKey): Optional<TemplateCriteriaScenario>
}