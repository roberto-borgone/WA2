package it.polito.wa2.lab2.dto

import java.math.BigDecimal
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

data class WalletDTO(
    @field:NotNull
    val owner: Long,
    @field:Min(0)
    val currentAmount: BigDecimal = BigDecimal(0.0),
    @field:NotNull
    val id: Long
)
