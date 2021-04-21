package it.polito.wa2.lab2.dto

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class CustomerDTO(
    @field:NotNull
    val id: Long,
    @field:NotBlank
    val name: String,
    @field:NotBlank
    val surname: String,
    @field:NotBlank
    val address: String,
    @field:NotNull
    val wallets: List<Long?> = listOf()
)
