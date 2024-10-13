package ru.silonov.bing.model.fillers

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import ru.silonov.bing.model.dictionaries.HydroObject
import ru.silonov.bing.model.dictionaries.ResponsibilityClass
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "template", schema = "bing")
class Template(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: UUID? = null,

    @ManyToOne
    @JoinColumn(name = "object_id", nullable = false)
    var objectId: HydroObject? = null,

    @ManyToOne
    @JoinColumn(name = "class_id", nullable = false)
    var classId: ResponsibilityClass? = null,

    @Column(name = "author_login", nullable = false)
    var authorLogin: String,

    @Column(name = "name", nullable = false)
    var name: String,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime? = null,

    @UpdateTimestamp
    @Column(name = "modified_at", nullable = false)
    var modifiedAt: LocalDateTime? = null

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false

        other as Template

        return id == other.id
    }

    override fun hashCode(): Int {
        return Objects.hash(id)
    }
}

