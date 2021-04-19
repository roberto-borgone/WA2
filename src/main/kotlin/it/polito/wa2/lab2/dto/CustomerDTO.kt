package it.polito.wa2.lab2.dto

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class CustomerDTO(
    @field:NotBlank
    val name: String,
    @field:NotBlank
    val surname: String,
    @field:NotBlank
    val address: String,
    @field:NotBlank
    @field:Email
    val email: String,
    @field:NotNull
    val wallets: List<Long?> = listOf(),
    val id: Long? = null
)
