package it.polito.wa2.lab2.dto

import javax.validation.constraints.NotNull

data class CustomerDTO(
    @field:NotNull
    val id: Long,
    val name: String? = null,
    val surname: String? = null,
    val address: String? = null,
    @field:NotNull
    val wallets: List<Long?> = listOf()
)
