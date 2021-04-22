package it.polito.wa2.lab2.dto

import java.time.LocalDateTime
import java.util.*
import javax.validation.constraints.NotNull

data class TokenDTO(
    @field:NotNull
    val token: UUID,
    @field:NotNull
    val expiryDateTime: LocalDateTime
)
