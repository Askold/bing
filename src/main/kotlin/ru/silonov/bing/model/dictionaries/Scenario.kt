package ru.silonov.bing.model.dictionaries

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "scenario", schema = "bing")
data class Scenario(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: UUID? = null,

    @Column(name = "name", nullable = false)
    var name: String,

    @ManyToOne
    @JoinColumn(name = "scenario_group_id", nullable = false)
    var scenarioGroupId: ScenarioGroup,

    @Column(name = "scenario_number")
    var scenarioNumber: Int
)
