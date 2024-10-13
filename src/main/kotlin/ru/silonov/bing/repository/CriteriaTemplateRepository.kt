package ru.silonov.bing.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.silonov.bing.model.linkers.TemplateCriteriaScenario
import java.util.UUID

interface CriteriaTemplateRepository : JpaRepository<TemplateCriteriaScenario, UUID>
