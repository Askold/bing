package ru.silonov.bing.service

import org.springframework.stereotype.Service
import ru.silonov.bing.model.dictionaries.HydroObject
import ru.silonov.bing.repository.ScenarioGroupRepository
import java.util.NoSuchElementException

@Service
class ScenarioGroupService(private val repository: ScenarioGroupRepository) {

    fun getDandgerKoef(objectId: HydroObject, name: String): Double =
        repository.findByObjectIdAndName(objectId, name).orElseThrow()
        { NoSuchElementException("ScenarioGroup not found with objectId: ${objectId.id}, name: $name") }
            .dangerKoef.toDouble()
}