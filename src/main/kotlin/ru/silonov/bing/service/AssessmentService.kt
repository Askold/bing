package ru.silonov.bing.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.silonov.bing.dto.assessment.CreateAssessmentRequestDto
import ru.silonov.bing.dto.assessment.CreateAssessmentResponseDTO

import ru.silonov.bing.factory.AssessmentFactory
import ru.silonov.bing.repository.*
import java.util.stream.Collectors

@Service
class AssessmentService(
    private val templateCriterioScenarioService: TemplateCriterioScenarioService,
    private val assessmentRepository: AssessmentRepository,
    private val assessmentFactory: AssessmentFactory,
) {

    @Transactional
    fun calculateValuesAndSaveAssessment(request: CreateAssessmentRequestDto): CreateAssessmentResponseDTO {

        val templateCriteriaScenarios = templateCriterioScenarioService.updateAllByRequestAndReturn(request)
            .stream().collect(Collectors.groupingBy { it.uniqueKey.scenario })

        val result = assessmentRepository.saveAll(templateCriteriaScenarios.map { assessmentFactory.getAssessment(it) })

        return CreateAssessmentResponseDTO(result.map { it.id!! })
    }
}