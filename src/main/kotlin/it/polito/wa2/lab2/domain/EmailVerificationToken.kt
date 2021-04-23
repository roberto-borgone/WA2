package it.polito.wa2.lab2.domain

import it.polito.wa2.lab2.dto.TokenDTO
import org.hibernate.annotations.Type
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
class EmailVerificationToken(
    @field:NotNull
    @field:Column(nullable = false, unique = true)
    val email: String,
    @field:OneToOne(fetch = FetchType.LAZY)
    @field:JoinColumn(name="user_id", referencedColumnName = "id", nullable = false)
    val user: User,
    @field:NotNull
    @field:Column(nullable = false, columnDefinition = "DATETIME(3)")
    val expiryDateTime: LocalDateTime = LocalDateTime.now().plusHours(1),
    @field:NotNull
    @field:Column(nullable = false)
    @Type(type = "org.hibernate.type.UUIDCharType")
    val token: UUID = UUID.randomUUID()
    ): EntityBase<Long>()

fun EmailVerificationToken.toDTO(): TokenDTO = TokenDTO(token, expiryDateTime)