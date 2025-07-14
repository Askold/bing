package ru.silonov.bing.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.silonov.bing.dto.assessment.CreateAssessmentRequestDto
import ru.silonov.bing.dto.assessment.CreateAssessmentResponseDTO
import ru.silonov.bing.factory.AssessmentFactory
import java.util.stream.Collectors

@Service
class AssessmentService(
    private val templateCriterioScenarioService: TemplateCriterioScenarioService,
    private val assessmentFactory: AssessmentFactory,
    private val reportService: ReportService
) {

    @Transactional
    fun calculateValuesAndSaveAssessment(request: CreateAssessmentRequestDto): CreateAssessmentResponseDTO {
        val report = reportService.getByIdOrCreate(request)

        val templateCriteriaScenarios = templateCriterioScenarioService.getCalculated(request)

        templateCriteriaScenarios.stream()
            .collect(Collectors.groupingBy { it.uniqueKey.scenario })
            .forEach { it.value.forEach { v -> v.assessment = assessmentFactory.getAssessment(it, report) } }

        templateCriterioScenarioService.saveAll(templateCriteriaScenarios)
        return CreateAssessmentResponseDTO(report.id!!)
    }
}