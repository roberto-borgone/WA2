package it.polito.wa2.lab2.domain

import it.polito.wa2.lab2.dto.UserDetailsDTO
import org.springframework.security.core.GrantedAuthority
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

enum class RoleName{

    CUSTOMER, ADMIN;

    override fun toString(): String = when{
        this == ADMIN -> "ROLE_ADMIN"
        this == CUSTOMER -> "ROLE_CUSTOMER"
        else -> ""
    }
}

fun String.toRoleName(): RoleName? = when{
    this == "ROLE_ADMIN" -> RoleName.ADMIN
    this == "ROLE_CUSTOMER" -> RoleName.CUSTOMER
    else -> null
}

/*
 * TODO: admin creation
 *
 * Admin creation process is not yet defined so here i directly create a customer
 * without checking user role, when admin creation process will be defined this must be
 * modified
 *
 */

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
    var roles: String = "ROLE_CUSTOMER",
    @field:Column(nullable = false)
    @field:NotNull
    var isEnabled: Boolean = false,
    @field:OneToOne(mappedBy = "userProfile", fetch = FetchType.LAZY , targetEntity = Customer::class, cascade = [CascadeType.REMOVE])
    var customerProfile: Customer? = null
): EntityBase<Long>() {

    fun getRoles(): Set<RoleName> = roles.split(":").map { it.toRoleName() as RoleName }.toSet()

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

fun User.toDTO(): UserDetailsDTO =
    UserDetailsDTO(
        username,
        getRoles().map { GrantedAuthority { it.toString() } }.toMutableSet()
    )