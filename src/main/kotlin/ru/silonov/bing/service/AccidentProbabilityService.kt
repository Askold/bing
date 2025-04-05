package ru.silonov.bing.service

import org.springframework.stereotype.Service
import ru.silonov.bing.model.dictionaries.ResponsibilityClass
import ru.silonov.bing.repository.AccidentProbabilityRepository
import java.util.NoSuchElementException

@Service
class AccidentProbabilityService(
    private val repository: AccidentProbabilityRepository
) {
    
    fun getAccidentProbability(responsibilityClass: ResponsibilityClass, finalSafetyLevel: String): Double =
        repository.findByResponsibilityClassAndFinalSafetyLevel(responsibilityClass, finalSafetyLevel).orElseThrow()
        { NoSuchElementException("AccidentProbability not found with class id: ${responsibilityClass.id} and finalSafetyLevel: $finalSafetyLevel") }
        .accidentProbabilityValue
}