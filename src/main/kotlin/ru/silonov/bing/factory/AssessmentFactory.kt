package ru.silonov.bing.factory

import ru.silonov.bing.calculator.AssessmentValuesCalculator.getCorrectionFactorValue
import ru.silonov.bing.calculator.AssessmentValuesCalculator.getDangerAccidentFactor
import ru.silonov.bing.calculator.AssessmentValuesCalculator.getFinalSafetyLevel
import ru.silonov.bing.calculator.AssessmentValuesCalculator.getSafetyScenarioGroupState
import ru.silonov.bing.calculator.AssessmentValuesCalculator.getSafetyStateE3
import ru.silonov.bing.calculator.AssessmentValuesCalculator.getTechnicalState
import ru.silonov.bing.calculator.AssessmentValuesCalculator.getTermOfUseFactor
import ru.silonov.bing.model.dictionaries.Scenario
import ru.silonov.bing.model.fillers.Assessment
import ru.silonov.bing.model.linkers.TemplateCriteriaScenario

object AssessmentFactory {

    fun getAssessment(entry: Map.Entry<Scenario, MutableList<TemplateCriteriaScenario>>): Assessment {
        val template = entry.value[0].templateId
        val hydroObject = template.objectId
        val criteriaRatingFinalList = entry.value.map { it.criteriesRatingFinal }

        val technicalState = getTechnicalState(criteriaRatingFinalList)
        val correctionFactorValue = template.classId!!.correctionFactor.toDoubleOrNull()!!
        val criteriaRatingFinalMax = criteriaRatingFinalList.max()
        val termOfUseState = criteriaRatingFinalMax.toDouble()
        val termOfUseFactor = getTermOfUseFactor(template.classId!!.name)
        val constructionStateWithoutE3 = criteriaRatingFinalMax.toDouble()
        val safetyStateWithE3 = getSafetyStateE3(technicalState, termOfUseState, termOfUseFactor, constructionStateWithoutE3)
        val dangerAccidentFactor = getDangerAccidentFactor(entry.key.scenarioGroupId.name)
        val safetyScenarioGroupState = getSafetyScenarioGroupState(dangerAccidentFactor, safetyStateWithE3)

        return Assessment(
            scenarioId = entry.key,
            template = template,
            objectId = hydroObject!!,
            technicalState = technicalState,
            correctionFactorValue = correctionFactorValue,
            technicalStateWithCorrection = getCorrectionFactorValue(technicalState, correctionFactorValue),
            termOfUseState = termOfUseState,
            termOfUseFactor = termOfUseFactor,
            constructionStateWithoutE3 = constructionStateWithoutE3,
            safetyStateWithoutE3 = getSafetyStateE3(technicalState, termOfUseState, termOfUseFactor, null),
            safetyStateWithE3 = safetyStateWithE3,
            dangerAccidentFactor = dangerAccidentFactor,
            safetyScenarioGroupState = safetyScenarioGroupState,
            finalSafetyLevel = getFinalSafetyLevel(safetyScenarioGroupState)
        )
    }
}