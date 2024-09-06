package ru.silonov.bing.model.linkers

import jakarta.persistence.*
import ru.silonov.bing.model.dictionaries.Criteria
import ru.silonov.bing.model.fillers.Template
import java.util.UUID

@Entity
@Table(name = "criteria_template", schema = "bing")
data class CriteriaTemplate(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: UUID? = null,

    @ManyToOne
    @JoinColumn(name = "template_id", nullable = false)
    var templateId: Template,

    @ManyToOne
    @JoinColumn(name = "criteria_id", nullable = false)
    var criteriaId: Criteria,

    var rank: Int?
)
