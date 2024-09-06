package ru.silonov.bing.model.fillers

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "employee", schema = "bing")
class Employee(

    @Id
    @Column(name = "login", nullable = false, unique = true)
    var login: String,

    @Column(name = "fullname", nullable = false)
    var fullName: String,

    @Column(name = "role_id", nullable = false)
    var roleId: String,

    @Column(name = "position")
    var position: String? = null,

    @Column(name = "lead_id")
    var leadId: String? = null,

    @Column(name = "password", nullable = false)
    var password: String

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false

        other as Employee

        return login == other.login
    }

    override fun hashCode(): Int {
        return Objects.hash(login)
    }
}
