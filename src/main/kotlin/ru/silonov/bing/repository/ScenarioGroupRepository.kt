package ru.silonov.bing.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.silonov.bing.model.dictionaries.HydroObject
import ru.silonov.bing.model.dictionaries.ScenarioGroup
import java.util.*

interface ScenarioGroupRepository : JpaRepository<ScenarioGroup, UUID> {
    fun findByObjectId_Id(objectId: UUID): List<ScenarioGroup>

    fun findByObjectIdAndName(objectId: HydroObject, name: String): Optional<ScenarioGroup>
}