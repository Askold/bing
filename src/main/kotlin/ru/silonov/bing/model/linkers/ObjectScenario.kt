package ru.silonov.bing.model.linkers

import jakarta.persistence.*
import java.util.UUID
import org.hibernate.proxy.HibernateProxy
import ru.silonov.bing.model.dictionaries.HydroObject
import ru.silonov.bing.model.dictionaries.Scenario

@Entity
@Table(name = "object_scenario", schema = "bing")
data class ObjectScenario(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Automatically generates UUID
    var id: UUID? = null,

    @ManyToOne
    @JoinColumn(name = "scenario_id", nullable = false)
    var scenario: Scenario,

    @ManyToOne
    @JoinColumn(name = "object_id", nullable = false)
    var hydroObject: HydroObject
) {

}
