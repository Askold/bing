package ru.silonov.bing.calculator

import org.junit.jupiter.api.assertThrows
import ru.silonov.bing.calculator.AssessmentValuesCalculator.getTechnicalState
import kotlin.test.Test
import kotlin.test.assertEquals

class TechnicalStateTest {

    @Test
    fun testGetTechnicalState_AllValuesBelowOrEqual3() {
        val criteriaRatingFinalList = listOf(1.0f, 2.0f, 3.0f)
        val result = getTechnicalState(criteriaRatingFinalList)
        assertEquals(3.0 - (3.0 - 1.0) * (3.0 - 2.0) * (3.0 - 3.0), result)
    }

    @Test
    fun testGetTechnicalState_MinAbove3_MaxBetween4And5() {
        val criteriaRatingFinalList = listOf(3.5f, 4.0f, 4.5f)
        val result = getTechnicalState(criteriaRatingFinalList)
        assertEquals(5.0 - (5.0 - 3.5) * (5.0 - 4.0) * (5.0 - 4.5), result)
    }

    @Test
    fun testGetTechnicalState_MinAbove3_MaxBelowOrEqual4() {
        val criteriaRatingFinalList = listOf(3.1f, 3.2f, 4.0f)
        val result = getTechnicalState(criteriaRatingFinalList)
        assertEquals(4.0 - (4.0 - 3.1) * (4.0 - 3.2) * (4.0 - 4.0), result)
    }

    @Test
    fun testGetTechnicalState_MinBelow3_MaxAbove3() {
        val criteriaRatingFinalList = listOf(2.0f, 3.5f, 4.0f)
        val result = getTechnicalState(criteriaRatingFinalList)
        assertEquals(2.0 - (2.0 - 2.0) * (2.0 - 3.5) * (2.0 - 4.0), result)
    }

    @Test
    fun testGetTechnicalState_SingleValue() {
        val criteriaRatingFinalList = listOf(2.0f)
        val result = getTechnicalState(criteriaRatingFinalList)
        assertEquals(3.0 - (3.0 - 2.0), result)
    }

    @Test
    fun testGetTechnicalState_EmptyList() {
        val criteriaRatingFinalList = listOf<Float>()
        assertThrows<NoSuchElementException> {
            getTechnicalState(criteriaRatingFinalList)
        }
    }

    @Test
    fun testGetTechnicalState_AllValuesEqual() {
        val criteriaRatingFinalList = listOf(4.0f, 4.0f, 4.0f)
        val result = getTechnicalState(criteriaRatingFinalList)
        assertEquals(4.0 - (4.0 - 4.0) * (4.0 - 4.0) * (4.0 - 4.0), result)
    }

    @Test
    fun testGetTechnicalState_MinAbove3_MaxAbove5() {
        val criteriaRatingFinalList = listOf(4.0f, 5.5f, 6.0f)
        val result = getTechnicalState(criteriaRatingFinalList)
        assertEquals(4.0 - (4.0 - 4.0) * (4.0 - 5.5) * (4.0 - 6.0), result)
    }
}