package ru.silonov.bing.repository;

import org.springframework.data.jpa.repository.JpaRepository
import ru.silonov.bing.model.dictionaries.AccidentProbability
import ru.silonov.bing.model.dictionaries.ResponsibilityClass
import java.util.*

interface AccidentProbabilityRepository : JpaRepository<AccidentProbability, UUID> {

    fun findByResponsibilityClassAndFinalSafetyLevel(responsibilityClass: ResponsibilityClass, finalSafetyLevel: String): Optional<AccidentProbability>
}