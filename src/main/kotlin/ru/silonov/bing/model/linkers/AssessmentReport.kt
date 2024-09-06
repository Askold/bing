package ru.silonov.bing.model.linkers

import jakarta.persistence.*
import ru.silonov.bing.model.fillers.Assessment
import ru.silonov.bing.model.fillers.Report
import java.util.*

@Entity
@Table(name = "assessment_report", schema = "bing")
data class AssessmentReport(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: UUID? = null,

    @ManyToOne
    @JoinColumn(name = "ass_id", nullable = false)
    var assessment: Assessment,

    @ManyToOne
    @JoinColumn(name = "report_id", nullable = false)
    var report: Report
)
