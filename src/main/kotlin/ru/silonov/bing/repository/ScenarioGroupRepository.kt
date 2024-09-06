package ru.silonov.bing.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.silonov.bing.model.dictionaries.HydroObject
import ru.silonov.bing.model.dictionaries.ScenarioGroup
import java.util.UUID

interface ScenarioGroupRepository : JpaRepository<ScenarioGroup, UUID> {
    fun findByObjectId(objectId: HydroObject): List<ScenarioGroup>
}