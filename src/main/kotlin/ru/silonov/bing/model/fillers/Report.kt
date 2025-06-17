package ru.silonov.bing.model.fillers

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.Type
import org.hibernate.annotations.UpdateTimestamp
import ru.silonov.bing.model.dictionaries.CriteriaGroup
import ru.silonov.bing.model.dictionaries.HydroObject
import ru.silonov.bing.model.linkers.TemplateCriteriaScenario
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "report", schema = "bing")
class Report(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: UUID? = null,

    @ManyToOne
    @JoinColumn(name = "object_id", nullable = false)
    var objectId: HydroObject,

    @Column(name = "modified_at", nullable = false)
    @OneToMany(mappedBy = "report", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var assessments: MutableList<Assessment> = mutableListOf(),

    @Column(name = "author_login", nullable = false)
    var authorLogin: String,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime? = null,

    @UpdateTimestamp
    @Column(name = "modified_at", nullable = false)
    var modifiedAt: LocalDateTime? = null,

    @ManyToOne
    @JoinColumn(name = "template_id", nullable = false)
    var template: Template
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false

        other as Report

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}

