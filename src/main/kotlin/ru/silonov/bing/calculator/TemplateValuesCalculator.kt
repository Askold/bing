package ru.silonov.bing.calculator

import ru.silonov.bing.model.dictionaries.Criteria

object TemplateValuesCalculator {

    fun getSignificanceCoefficients(rank: Int, criteriaPriorityMap: HashMap<Int, Float>): Float =
        criteriaPriorityMap[rank]!! / criteriaPriorityMap.values.max()

    fun getCriteriaPriorityMap(ranks: List<Int>): HashMap<Int, Float> {
        val sum = ranks.sum().toFloat()
        val n = ranks.sum().toFloat()

        val resultMap = HashMap<Int, Float>()
        ranks.forEach { resultMap[it] = ((n - it + 1) / sum) }

        return resultMap
    }

    fun getCriteriesRating(factValue: String, significanceCoefficient: Float, criteria: Criteria): Float {
        val value = factValue.toFloatOrNull()
        val k1 = criteria.k1.toFloatOrNull()!!
        val k2 = criteria.k2.toFloatOrNull()!!
        val k3 = criteria.k3.toFloatOrNull()!!

        if (value == null) return 2F
        else if (value.compareTo(k1) < 0) return 2F
        else if (value.compareTo(k1) > 0 && value.compareTo(k2) < 0) return 3 + (value / (k1 + k2))
        else if (value.compareTo(k2) == 0) return 4F
        else if (value.compareTo(k2) > 0 && value.compareTo(k3) < 0) return 4 + (value / (k1 + k2))
        else if (value.compareTo(k3) == 0) return 5F
        else return 2 + (value - 2) * significanceCoefficient
    }

    fun getCriteriesRatingFinal(criteriesRating: Float, significanceCoefficient: Float) =
        2 + (criteriesRating - 2) * significanceCoefficient
}