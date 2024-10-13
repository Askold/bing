package ru.silonov.bing.model.linkers

import jakarta.persistence.*
import ru.silonov.bing.model.dictionaries.Criteria
import ru.silonov.bing.model.dictionaries.Scenario
import ru.silonov.bing.model.fillers.Template
import java.util.UUID

@Entity
@Table(name = "template_criteria_scenario", schema = "bing")
data class TemplateCriteriaScenario(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: UUID? = null,

    @ManyToOne
    @JoinColumn(name = "template_id", nullable = false)
    var templateId: Template,

    @ManyToOne
    @JoinColumn(name = "criteria_id", nullable = false)
    var criteriaId: Criteria,

    var rank: Int?,

    @ManyToOne
    @JoinColumn(name = "scenario_id", nullable = false)
    var scenario: Scenario,

    @Column(name = "significance_coefficient")
    var significanceCoefficient: Int,

    @Column(name = "criteries_rating")
    var criteriesRating: Int,

    @Column(name = "criteries_rating_final")
    var criteriesRatingFinal: Int,

    @Column(name = "fact_value")
    var factValue: Int,
)
