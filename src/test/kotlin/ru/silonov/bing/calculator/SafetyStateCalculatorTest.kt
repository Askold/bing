package ru.silonov.bing.calculator

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import ru.silonov.bing.calculator.AssessmentValuesCalculator.getSafetyStateE3
import ru.silonov.bing.calculator.AssessmentValuesCalculator.safetyStateFormula

class SafetyStateCalculatorTest {

    @Test
    fun `should return safetyStateFormula(4_0) when all values are between 3 and 4`() {
        val result = getSafetyStateE3(3.5, 3.5, 1.0, 3.5)
        assertEquals(safetyStateFormula(4.0, 3.5, 3.5, 3.5), result)
    }

    @Test
    fun `should return safetyStateFormula(5_0) when all values are between 3 and 5`() {
        val result = getSafetyStateE3(4.0, 3.5, 1.0, 4.5)
        assertEquals(safetyStateFormula(5.0, 4.0, 3.5, 4.5), result)
    }

    @Test
    fun `should return safetyStateFormula(5_0) when all values are between 4 and 5`() {
        val result = getSafetyStateE3(4.5, 4.5, 1.0, 4.5)
        assertEquals(safetyStateFormula(5.0, 4.5, 4.5, 4.5), result)
    }

    @Test
    fun `should return max value when technicalState or constructionStateWithoutE3 is greater than 5`() {
        val result = getSafetyStateE3(5.5, 3.0, 1.0, 4.0)
        assertEquals(5.5, result)

        val result2 = getSafetyStateE3(4.0, 3.0, 1.0, 5.5)
        assertEquals(5.5, result2)
    }

    @Test
    fun `should return max of all values when none of the conditions match`() {
        val result = getSafetyStateE3(2.0, 3.0, 1.0, 2.5)
        assertEquals(3.0, result) // Max of (2.0, 3.0, 2.5)

        val result2 = getSafetyStateE3(1.5, 2.5, 1.2, null)
        assertEquals(2.5, result2) // Max of (1.5, 2.5 * 1.2 = 3.0)
    }

    @Test
    fun `should handle null constructionStateWithoutE3 correctly`() {
        val result = getSafetyStateE3(3.5, 3.5, 1.0, null)
        assertEquals(safetyStateFormula(4.0, 3.5, 3.5, null), result)
    }
}
