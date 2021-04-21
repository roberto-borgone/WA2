package it.polito.wa2.lab2.dto

import java.math.BigDecimal
import java.time.LocalDateTime
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

data class TransactionDTO(
    @field:NotNull
    val id: Long,
    @field:Min(0)
    val amount: BigDecimal,
    @field:NotNull
    val from: Long,
    @field:NotNull
    val to: Long,
    @field:NotNull
    val timestamp: LocalDateTime
)
