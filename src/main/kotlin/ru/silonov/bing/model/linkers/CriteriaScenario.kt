package ru.silonov.bing.model.linkers

import jakarta.persistence.*
import java.util.UUID
import ru.silonov.bing.model.dictionaries.CriteriaGroup
import ru.silonov.bing.model.dictionaries.Scenario

@Entity
@Table(name = "criteria_scenario", schema = "bing")
data class CriteriaScenario(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: UUID? = null,

    @ManyToOne
    @JoinColumn(name = "scenario_id", nullable = false)
    var scenario: Scenario,

    @ManyToOne
    @JoinColumn(name = "criterio_id", nullable = false)
    var criteria: CriteriaGroup
)
