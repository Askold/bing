package ru.silonov.bing.calculator

object AssessmentValuesCalculator {

    private val termOfUseStrategyMap = mapOf(
        "I" to 0.82,
        "II" to 0.80,
        "III" to 0.75,
        "IV" to 0.70
    )

    private val dangerAccidentFactorMap = mapOf(
        "ПЕРВАЯ ГРУППА" to 1.0,
        "ВТОРАЯ ГРУППА" to 0.9,
        "ТРЕТЬЯ ГРУППА" to 0.8
    )

    fun getTechnicalState(criteriaRatingFinalList: List<Float>): Double {
        val localMax = criteriaRatingFinalList.max()
        val localMin = criteriaRatingFinalList.min()

        val iMax: Double = when {
            localMin > 3.0 && localMax <= 4.0 -> 4.0
            localMin > 3.0 && localMax <= 5.0 -> 5.0
            localMax <= 3.0 -> localMax.toDouble()
            else -> localMin.toDouble()
        }

        val multiply = criteriaRatingFinalList.map { iMax - it }.reduce { first, second -> first * second }

        return iMax - multiply
    }

    fun getCorrectionFactorValue(technicalState: Double, correctionFactorValue: Double): Double =
        2.0 + (technicalState - 2.0) * correctionFactorValue

    fun getTermOfUseFactor(classId: String): Double = termOfUseStrategyMap[classId]!!

    fun getSafetyStateE3(
        technicalState: Double,
        termOfUseState: Double,
        termOfUseFactor: Double,
        constructionStateWithoutE3: Double?
    ): Double {
        val termOfUseMultiply = termOfUseFactor * termOfUseState

        val allBetween3And4 = areAllBetween(3.0, 4.0, technicalState, termOfUseMultiply, constructionStateWithoutE3)
        val allBetween3And5 = areAllBetween(3.0, 5.0, technicalState, termOfUseMultiply, constructionStateWithoutE3)
        val allBetween4And5 = areAllBetween(4.0, 5.0, technicalState, termOfUseMultiply, constructionStateWithoutE3)

        return when {
            allBetween3And4 -> safetyStateFormula(4.0, technicalState, termOfUseMultiply, constructionStateWithoutE3)
            allBetween3And5 || allBetween4And5 -> safetyStateFormula(
                5.0,
                technicalState,
                termOfUseMultiply,
                constructionStateWithoutE3
            )

            ((technicalState > 5.0 || (constructionStateWithoutE3?.compareTo(5.0) ?: -1) > 0)) ->
                listOfNotNull(technicalState, constructionStateWithoutE3).maxOrNull() ?: technicalState

            else -> listOfNotNull(technicalState, termOfUseState, constructionStateWithoutE3).maxOrNull()
                ?: Double.MIN_VALUE
        }
    }

    private fun Double.isBetween(lower: Double, upper: Double): Boolean = this > lower && this <= upper

    private fun areAllBetween(rangeStart: Double, rangeEnd: Double, vararg values: Double?): Boolean =
        values.filterNotNull().all { it.isBetween(rangeStart, rangeEnd) }


    fun safetyStateFormula(
        iMax: Double,
        technicalState: Double,
        termOfUseMultiply: Double,
        constructionStateWithoutE3: Double?
    ) = iMax - ((iMax - technicalState) * (iMax - termOfUseMultiply) *
            (constructionStateWithoutE3?.let { iMax - it } ?: 1.0))


    fun getDangerAccidentFactor(groupName: String) = dangerAccidentFactorMap[groupName]!!

    fun getSafetyScenarioGroupState(dangerAccidentFactor: Double, safetyStateWithE3: Double) =
        2 + dangerAccidentFactor * (safetyStateWithE3 - 2)

    fun getFinalSafetyLevel(safetyScenarioGroupState: Double) = when {
        safetyScenarioGroupState in 2.0..3.0 -> "Нормальный"
        safetyScenarioGroupState.isBetween(3.0, 4.0) -> "Пониженный"
        safetyScenarioGroupState.isBetween(4.0, 5.0) -> "Неудовлетворительный"
        safetyScenarioGroupState > 5.0 -> "Опасный"
        else -> ""
    }
}