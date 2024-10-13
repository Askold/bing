package ru.silonov.bing.model.linkers

import jakarta.persistence.*
import java.util.UUID
import ru.silonov.bing.model.dictionaries.ResponsibilityClass
import ru.silonov.bing.model.dictionaries.Scenario

@Entity
@Table(name = "correction_factor", schema = "bing")
data class CorrectionFactor(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Automatically generates UUID
    var id: UUID? = null,

    @ManyToOne
    @JoinColumn(name = "class_id", nullable = false)
    var responsibilityClass: ResponsibilityClass,

    @ManyToOne
    @JoinColumn(name = "scenario_id", nullable = false)
    var scenario: Scenario,

    var varue: Double?
)
