package it.polito.wa2.lab2.dto

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.Column
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class UserDetailsDTO(
    @field:Column(unique = true, nullable = false)
    @field:NotBlank
    private val username: String,
    @field:Column(nullable = false)
    @field:NotBlank
    private var password: String,
    @field:Column(unique = true, nullable = false)
    @field:NotBlank
    @field:Email
    val email: String,
    @field:Column(nullable = false)
    @field:Size(min = 1)
    val roles: MutableSet<GrantedAuthority>,
    @field:Column(nullable = false)
    @field:NotNull
    private var isEnabled: Boolean = false
): UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = roles

    override fun getPassword(): String = password

    override fun getUsername(): String = username

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = isEnabled
}
