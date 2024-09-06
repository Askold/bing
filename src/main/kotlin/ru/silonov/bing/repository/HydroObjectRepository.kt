package ru.silonov.bing.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.silonov.bing.model.dictionaries.HydroObject
import java.util.UUID

interface HydroObjectRepository: JpaRepository<HydroObject, UUID> {
    fun findByName(name: String): List<HydroObject>
}
