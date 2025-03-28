package ru.silonov.bing.model

import jakarta.persistence.*
import ru.silonov.bing.model.dictionaries.Criteria
import ru.silonov.bing.model.dictionaries.Scenario
import ru.silonov.bing.model.fillers.Template

@Embeddable
data class TemplateUniqueKey(

    @ManyToOne(cascade = [(CascadeType.PERSIST)])
    @JoinColumn(name = "template_id", nullable = false)
    var template: Template,

    @ManyToOne
    @JoinColumn(name = "criteria_id", nullable = false)
    var criteria: Criteria,

    @ManyToOne
    @JoinColumn(name = "scenario_id", nullable = false)
    var scenario: Scenario,
) {
    override fun toString(): String {
        return "TemplateUniqueKey(template=$template, criteria=$criteria, scenario=$scenario)"
    }
}

