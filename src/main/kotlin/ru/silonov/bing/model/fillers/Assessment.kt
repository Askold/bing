package ru.silonov.bing.model.fillers

import jakarta.persistence.*
import org.mapstruct.Mapper
import ru.silonov.bing.model.dictionaries.HydroObject
import ru.silonov.bing.model.dictionaries.Scenario
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "assessment", schema = "bing")
class Assessment(

    @Id
    @Column(name = "id", nullable = false, unique = true)
    var id: UUID,

    @Column(name = "technical_state")
    var technicalState: Double? = null,

    @Column(name = "correction_factor_varue")
    var correctionFactorValue: Double? = null,

    @Column(name = "technical_state_with_correction")
    var technicalStateWithCorrection: Double? = null,

    @Column(name = "final_technical_state")
    var finalTechnicalState: String? = null,

    @Column(name = "term_of_use_state")
    var termOfUseState: Double? = null,

    @Column(name = "term_of_use_factor")
    var termOfUseFactor: Double? = null,

    @Column(name = "construction_state_without_e3")
    var constructionStateWithoutE3: Double? = null,

    @Column(name = "construction_state_with_e3")
    var constructionStateWithE3: Double? = null,

    @Column(name = "safety_state_without_e3")
    var safetyStateWithoutE3: Double? = null,

    @Column(name = "safety_state_with_e3")
    var safetyStateWithE3: Double? = null,

    @Column(name = "danger_accident_factor")
    var dangerAccidentFactor: Double? = null,

    @Column(name = "safety_scenario_group_state")
    var safetyScenarioGroupState: Double? = null,

    @Column(name = "final_safety_level")
    var finalSafetyLevel: String? = null,

    @Column(name = "accident_probability")
    var accidentProbability: Double? = null,

    @ManyToOne
    @JoinColumn(name = "object_id", nullable = false)
    var objectId: HydroObject,

    @Column(name = "fact_varues")
    var factValues: String? = null,

    @ManyToOne
    @JoinColumn(name = "scenario_id", nullable = false)
    var scenarioId: Scenario,

    @Column(name = "created_at")
    var createdAt: LocalDateTime? = null,

    @Column(name = "created_by")
    var createdBy: String? = null,

    @ManyToOne
    @JoinColumn(name = "template_id", nullable = false)
    var template: Template
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false

        other as Assessment

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return Objects.hash(id)
    }
}
