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
    private val assessmentFactory: AssessmentFactory,
    private val reportService: ReportService
) {

    @Transactional
    fun calculateValuesAndSaveAssessment(request: CreateAssessmentRequestDto): CreateAssessmentResponseDTO {

        var report = reportService.getByIdOrCreate(request)

        val templateCriteriaScenarios = templateCriterioScenarioService.updateAllByRequestAndReturn(request)
            .stream().collect(Collectors.groupingBy { it.uniqueKey.scenario })

        report = reportService.save(report.apply {
            assessments.addAll(templateCriteriaScenarios.map { assessmentFactory.getAssessment(it, report) })
        })

        return CreateAssessmentResponseDTO(report.id!!)
    }
}