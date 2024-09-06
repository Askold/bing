package ru.silonov.bing.model.dictionaries

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "responsibility_class", schema = "bing")
data class ResponsibilityClass(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: UUID? = null,

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "significance_factor", nullable = false)
    var significanceFactor: String,

    @OneToMany(mappedBy = "responsibilityClass", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var hydroObjects: List<HydroObject> = emptyList()
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ResponsibilityClass) return false
        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "ResponsibilityClass(id=$id, name='$name', " +
                "significanceFactor='$significanceFactor', " +
                "hydroObjects=$hydroObjects)"
    }
}
