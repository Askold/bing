package ru.silonov.bing.decorator

import ru.silonov.bing.calculator.TemplateValuesCalculator.getCriteriesRating
import ru.silonov.bing.calculator.TemplateValuesCalculator.getCriteriesRatingFinal
import ru.silonov.bing.model.linkers.TemplateCriteriaScenario

object TemplateCriteriaScenarioDecorator {

    fun TemplateCriteriaScenario.calculateWithFactValue(factValueFromRequest: String): TemplateCriteriaScenario {
        val criteriesRatingCalculated = getCriteriesRating(
            factValueFromRequest,
            this.significanceCoefficient,
            this.uniqueKey.criteria
        )

        val significanceCoefficients = this.significanceCoefficient

        this.apply {
            criteriesRating = criteriesRatingCalculated
            factValue = factValueFromRequest
            criteriesRatingFinal = getCriteriesRatingFinal(criteriesRatingCalculated, significanceCoefficients)
        }

        return this
    }
}