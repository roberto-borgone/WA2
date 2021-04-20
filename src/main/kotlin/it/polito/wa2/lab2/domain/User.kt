package it.polito.wa2.lab2.domain

import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

enum class RoleName{

    CUSTOMER, ADMIN;

    override fun toString(): String = if(this == ADMIN) "ADMIN" else "CUSTOMER"
}

fun String.toRoleName() = if (this == "ADMIN") RoleName.ADMIN else RoleName.CUSTOMER

@Entity
@Table(indexes = [Index(name = "index", columnList = "username", unique = true)])
class User(
    @field:Column(unique = true, nullable = false)
    @field:NotBlank
    val username: String,
    @field:Column(nullable = false)
    @field:NotBlank
    var password: String,
    @field:Column(unique = true, nullable = false)
    @field:NotBlank
    @field:Email
    val email: String,
    @field:Column(nullable = false)
    @field:NotBlank
    var roles: String,
    @field:Column(nullable = false)
    @field:NotNull
    var isEnabled: Boolean = false,
    @field:OneToOne(mappedBy = "userProfile", fetch = FetchType.LAZY ,targetEntity = Customer::class, cascade = [CascadeType.REMOVE])
    var customerProfile: Customer? = null
): EntityBase<Long>() {

    fun getRoles(): Set<RoleName> = roles.split(":").map { it.toRoleName() }.toSet()
    fun addRole(role: RoleName){
        if(!roles.contains(role.toString())) {
            if (roles.isEmpty()) roles += role else roles += ":$role"
        }
    }
    fun removeRole(role: RoleName){
        if(roles.contains(role.toString()) && roles.contains(":")) {
            roles = if (roles.startsWith(role.toString())) roles.replace("$role:", "") else roles.replace(":$role", "")
        }
    }
}