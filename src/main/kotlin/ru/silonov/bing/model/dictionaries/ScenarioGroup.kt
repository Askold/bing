package ru.silonov.bing.model.dictionaries

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "scenario_group", schema = "bing")
data class ScenarioGroup(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: UUID? = null,

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "danger_koef", nullable = false)
    var dangerKoef: Int,

    @ManyToOne
    @JoinColumn(name = "object_id", nullable = false)
    var objectId: HydroObject,

    @OneToMany(mappedBy = "scenarioGroupId", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var scenarios: List<Scenario> = emptyList()
)
