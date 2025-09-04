package ru.silonov.bing.excel

import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import ru.silonov.bing.excel.ReportFileGenerator.addGeneralResult
import ru.silonov.bing.model.fillers.Report
import java.io.ByteArrayOutputStream
import java.util.stream.Collectors

object ReportFileGenerator {

    fun generateReport(report: Report): ByteArray {

        val workbook: Workbook = XSSFWorkbook()
            .addScenarios(report)
            .addCalculatedValues(report)
            .addGeneralResult(report)
            .addFinal(report)

        return ByteArrayOutputStream().use { outputStream ->
            workbook.write(outputStream)
            outputStream.toByteArray()
        }.also {
            workbook.close()
        }
    }

    private fun Workbook.addScenarios(report: Report): Workbook {
        val scenariosMap = report.assessments.map { it.scenarioId }.stream().collect(Collectors.groupingBy { it.scenarioGroupId })
        val resultMap = LinkedHashMap<String, String>()
        scenariosMap.forEach {
            resultMap[it.key.name] = it.key.dangerKoef.toString()
            it.value.forEach { scenario ->
                run {
                    resultMap[scenario.scenarioNumber.toString()] = scenario.name
                }
            }
        }

        val sheet: Sheet = this.createSheet("Сценарии").addHeaders(listOf("№", "Сценарии аварий"), this)

        var rowIndex = 0
        resultMap.forEach { sheet.createRow(rowIndex++, listOf(it.key, it.value)) }

        return this
    }

    private fun Workbook.addCalculatedValues(report: Report): Workbook {
        val scenarios = report.assessments.flatMap { it.templateCriteriaScenarios }

        val sheet: Sheet = this.createSheet("Расчетные значения").addHeaders(listOf(
            "№ сценария",
            "Обозначение критерия безопасности",
            "Наименование критерия безопасности групп А, Б и В",
            "Результат оценки критерия ",
            "Ранг",
            "Коэффициент важности (значимости)",
            "Уточненный результат оценки критерия",
            "Критерии групп безопасности Г, Д, Е"
        ), this)

        var rowIndex = 0
        scenarios.forEach {
            sheet.createRow(rowIndex++, listOf(
                it.uniqueKey.scenario.scenarioNumber.toString(),
                it.uniqueKey.criteria.code,
                it.uniqueKey.criteria.fullName,
                it.criteriesRating.toString(),
                it.rank.toString(),
                it.significanceCoefficient.toString(),
                it.criteriesRatingFinal.toString()
            ))
        }

        return this
    }

    private fun Workbook.addGeneralResult(report: Report): Workbook {
        val assessments = report.assessments

        val sheet: Sheet = this.createSheet("Результат общий").addHeaders(listOf(
            "Сценарий",
            "ТС",
            "Ку",
            "Тсу",
            "Вид тех. состояния",
            "УЭ",
            "Куэ",
            "НП(е1,е2)",
            "БС(е1,е2)",
            "НП (е3)",
            "Кнпi",
            "БС(е1, е2 + е3)",
            "Ксц",
            "БСсц",
            "Уровень безопасности",
            "Верх.гр.расчетной вероятности возникновения аварии (1/год)"
        ), this)

        var rowIndex = 0
        assessments.forEach {
            sheet.createRow(rowIndex++, listOf(
                it.scenarioId.scenarioNumber.toString(),
                it.technicalState.toString(),
                it.correctionFactorValue.toString(),
                it.technicalStateWithCorrection.toString(),
                it.finalTechnicalState.toString(),
                it.termOfUseState.toString(),
                it.termOfUseFactor.toString(),
                it.constructionStateWithoutE3.toString(),
                it.safetyStateWithoutE3.toString(),
                it.safetyStateWithE3.toString(),
                "",
                it.safetyStateWithE3.toString(),
                it.dangerAccidentFactor.toString(),
                it.safetyScenarioGroupState.toString(),
                it.finalSafetyLevel.toString(),
                it.accidentProbability.toString()
            ))
        }

        return this
    }

    private fun Workbook.addFinal(report: Report): Workbook {
        val assessments = report.assessments

        val sheet: Sheet = this.createSheet("Результат общий").addHeaders(listOf(
            "Сценарий",
            "ТС",
            "Ку",
            "Тсу",
            "Вид тех. состояния",
            "УЭ",
            "Куэ",
            "НП(е1,е2)",
            "БС(е1,е2)",
            "НП (е3)",
            "Кнпi",
            "БС(е1, е2 + е3)",
            "Ксц",
            "БСсц",
            "Уровень безопасности",
            "Верх.гр.расчетной вероятности возникновения аварии (1/год)"
        ), this)

        var rowIndex = 0
        assessments.forEach {
            sheet.createRow(rowIndex++, listOf(
                it.scenarioId.scenarioNumber.toString(),
                it.technicalState.toString(),
                it.correctionFactorValue.toString(),
                it.technicalStateWithCorrection.toString(),
                it.finalTechnicalState.toString(),
                it.termOfUseState.toString(),
                it.termOfUseFactor.toString(),
                it.constructionStateWithoutE3.toString(),
                it.safetyStateWithoutE3.toString(),
                it.safetyStateWithE3.toString(),
                "",
                it.safetyStateWithE3.toString(),
                it.dangerAccidentFactor.toString(),
                it.safetyScenarioGroupState.toString(),
                it.finalSafetyLevel.toString(),
                it.accidentProbability.toString()
            ))
        }

        return this
    }

    private fun Sheet.createRow(rowIndex: Int, values: List<String>) {
        val row: Row = this.createRow(rowIndex)
        values.forEachIndexed {index, value ->
            row.createCell(index, value)
        }
    }

    private fun Row.createCell(index: Int, value: String) = this.createCell(index).setCellValue(value)

    private fun Sheet.addHeaders(headers: List<String>, workbook: Workbook): Sheet {
        val headerRow: Row = this.createRow(0)
        headers.forEachIndexed { index, header ->
            val cell = headerRow.createCell(index)
            cell.setCellValue(header)
            val style: CellStyle = workbook.createCellStyle()
            val font: Font = workbook.createFont()
            font.bold = true
            style.setFont(font)
            cell.cellStyle = style
        }

        return this
    }
}