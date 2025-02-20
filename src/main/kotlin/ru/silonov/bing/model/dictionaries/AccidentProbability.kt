package ru.silonov.bing.model.dictionaries

import jakarta.persistence.*
import lombok.Getter
import lombok.Setter
import java.util.*

@Entity
@Table(name = "accident_probability", schema = "bing")
data class AccidentProbability(
    
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", nullable = false, updatable = false)
    var id: UUID?,

    @Column(name = "safety_scenario_group_state", nullable = false)
    var safetyScenarioGroupState: String,

    @Column(name = "final_safety_level", nullable = false)
    var finalSafetyLevel: String,

    @Column(name = "accident_probability_value", nullable = false)
    var accidentProbabilityValue: Double,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responsibility_class_id", nullable = false)
    var responsibilityClass: ResponsibilityClass
)