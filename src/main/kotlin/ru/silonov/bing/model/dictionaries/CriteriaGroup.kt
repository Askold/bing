package ru.silonov.bing.model.dictionaries

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "criteria_group", schema = "bing")
data class CriteriaGroup(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: UUID? = null,

    @Column(name = "full_name", nullable = false)
    var fullName: String,

    @OneToMany(mappedBy = "criteriaGroup", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var criteria: List<Criteria> = emptyList()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CriteriaGroup

        return id == other.id
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "CriteriaGroup(id=$id, fullName='$fullName'"
    }
}
