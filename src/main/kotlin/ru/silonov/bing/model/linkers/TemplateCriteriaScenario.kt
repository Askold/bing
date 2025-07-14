package ru.silonov.bing.model.linkers

import jakarta.persistence.*
import ru.silonov.bing.model.TemplateUniqueKey
import ru.silonov.bing.model.fillers.Assessment
import java.util.*

@Entity
@Table(name = "template_criteria_scenario", schema = "bing")
class TemplateCriteriaScenario(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: UUID? = null,

    var rank: Int?,

    @Column(name = "significance_coefficient")
    var significanceCoefficient: Float,

    @Column(name = "criteries_rating")
    var criteriesRating: Float? = null,

    @Column(name = "criteries_rating_final")
    var criteriesRatingFinal: Float? = null,

    @Column(name = "fact_value")
    var factValue: String? = null,

    @Column(name = "is_criteries_rating_final", nullable = false)
    var isCriteriesRatingFinal: Boolean = false,

    @ManyToOne(cascade = [(CascadeType.ALL)])
    @JoinColumn(name = "assessment_id")
    var assessment: Assessment? = null,

    @Embedded
    var uniqueKey: TemplateUniqueKey
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false

        other as TemplateCriteriaScenario

        if (id != other.id) return false
        if (uniqueKey != other.uniqueKey) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (uniqueKey.hashCode())
        return result
    }
}