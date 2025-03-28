package ru.silonov.bing.decorator

import ru.silonov.bing.calculator.TemplateValuesCalculator.getCriteriesRating
import ru.silonov.bing.calculator.TemplateValuesCalculator.getCriteriesRatingFinal
import ru.silonov.bing.dto.assessment.CriteriaScenario
import ru.silonov.bing.model.linkers.TemplateCriteriaScenario

object TemplateCriteriaScenarioDecorator {

    fun TemplateCriteriaScenario.calculateValues(request: CriteriaScenario): TemplateCriteriaScenario {

        val criteriaRating: Float
        val criteriaRatingFinal: Float

        if (request.isCriteriaRatingFinalManual) {
            criteriaRating = request.criteriaRating!!
            criteriaRatingFinal = request.criteriaRatingFinal!!
        } else {
            criteriaRating = getCriteriesRating(
                request.factValue,
                this.significanceCoefficient,
                this.uniqueKey.criteria
            )
            criteriaRatingFinal = getCriteriesRatingFinal(criteriaRating, this.significanceCoefficient)
        }

        this.apply {
            criteriesRating = criteriaRating
            factValue = request.factValue
            criteriesRatingFinal = criteriaRatingFinal
        }

        return this
    }
}