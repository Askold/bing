package ru.silonov.bing.excel

import org.apache.poi.ss.usermodel.*
import org.apache.poi.ss.util.CellRangeAddress
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import ru.silonov.bing.excel.ReportFileGenerator.addTableName
import ru.silonov.bing.model.fillers.Assessment
import ru.silonov.bing.model.fillers.Report
import java.io.ByteArrayOutputStream
import java.util.stream.Collectors

object ReportFileGenerator {

    fun generateReport(report: Report): ByteArray {

        val workbook: Workbook = XSSFWorkbook()
            .addScenarios(report)
            .addCalculatedValues(report)
            .addGeneralResult(
                report.assessments,
                "Результат общий",
                "Таблица 3 - Сводная таблица результатов оценки технического состояния и уровня безопасности"
            )
            .addGeneralResult(
                report.assessments.filter { it.finalTechnicalState != "Работоспособное" },
                "Итог",
                "Таблица 4 - Сводная таблица наихудших результатов оценки технического состояния и уровня безопасности для каждой группы сценариев"
            )

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

        val sheet: Sheet = this.createSheet("Сценарии")
            .addTableName("Таблица 1 - Сценарии аварий ${report.objectId.name}", 1)
            .addHeaders(listOf("№", "Сценарии аварий"), this)

        var rowIndex = 1
        resultMap.forEach { sheet.createRow(rowIndex++, listOf(it.key, it.value)) }

        return this
    }

    private fun Workbook.addCalculatedValues(report: Report): Workbook {
        val scenarios = report.assessments.flatMap { it.templateCriteriaScenarios }

        val sheet: Sheet = this.createSheet("Расчетные значения")
            .addTableName(
                "Таблица 2 - Результаты оценки и ранжирования критериев безопасности групп А, Б и В, " +
                        "а также результыт оценки критериев групп Г,Д, Е для сценариев аварии 1,2 и 3 групп", 7
            )
            .addHeaders(
                listOf(
                    "№ сценария",
                    "Обозначение критерия безопасности",
                    "Наименование критерия безопасности групп А, Б и В",
                    "Результат оценки критерия ",
                    "Ранг",
                    "Коэффициент важности (значимости)",
                    "Уточненный результат оценки критерия",
                    "Критерии групп безопасности Г, Д, Е"
                ), this
            )

        var rowIndex = 2
        scenarios.forEach {
            sheet.createRow(
                rowIndex++, listOf(
                    it.uniqueKey.scenario.scenarioNumber.toString(),
                    it.uniqueKey.criteria.code,
                    it.uniqueKey.criteria.fullName,
                    it.criteriesRating.toString(),
                    it.rank.toString(),
                    it.significanceCoefficient.toString(),
                    it.criteriesRatingFinal.toString()
                )
            )
        }

        return this
    }

    private fun Workbook.addGeneralResult(assessments: List<Assessment>, sheetName: String, tableName: String): Workbook {
        val sheet: Sheet = this.createSheet(sheetName)
            .addTableName(tableName, 15)
            .addHeaders(
                listOf(
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
                ), this
            )

        var rowIndex = 2
        assessments.forEach {
            sheet.createRow(
                rowIndex++, listOf(
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
                )
            )
        }

        val legend = mapOf(
            "Вид технического состояния:" to "Уровень безопаности:",
            "И - исправное" to "Нр - нормальный",
            "Р - работоспособное" to "П - пониженный",
            "ОР - ограниченно работоспособное" to "Нд - неудовлетворительный",
            "П - предаварийное" to "О - опасный",
            "А - аварийное" to "",
        )

        legend.forEach{
            rowIndex++
            sheet.createRow(rowIndex).createCell(1, it.key)
            sheet.createRow(rowIndex).createCell(12, it.value)
            sheet.addMergedRegion(CellRangeAddress(rowIndex, rowIndex, 0, 11))
            sheet.addMergedRegion(CellRangeAddress(rowIndex, rowIndex, 12, 15))
        }


        return this
    }

    private fun Sheet.createRow(rowIndex: Int, values: List<String>) {
        val row: Row = this.createRow(rowIndex)
        values.forEachIndexed { index, value ->
            row.createCell(index, value)
        }
    }

    private fun Sheet.addTableName(name: String, lastCellIndex: Int): Sheet {
        this.createRow(0).createCell(0, name)
        this.addMergedRegion(CellRangeAddress(0, 0, 0, lastCellIndex))

        return this
    }

    private fun Row.createCell(index: Int, value: String) = this.createCell(index).setCellValue(value)

    private fun Sheet.addHeaders(headers: List<String>, workbook: Workbook): Sheet {
        val headerRow: Row = this.createRow(1)
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