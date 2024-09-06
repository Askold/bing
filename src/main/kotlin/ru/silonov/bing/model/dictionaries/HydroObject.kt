package ru.silonov.bing.model.dictionaries

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "hydro_object", schema = "bing")
data class HydroObject(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: UUID? = null,

    @Column(name = "name", nullable = false)
    var name: String,

    @ManyToOne
    @JoinColumn(name = "class_id", nullable = false)
    var responsibilityClass: ResponsibilityClass
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HydroObject

        return id == other.id
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "HydroObject(id=$id, name='$name', responsibilityClass=$responsibilityClass)"
    }
}
