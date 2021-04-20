package it.polito.wa2.lab2.domain

import it.polito.wa2.lab2.dto.TransactionDTO
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

@Entity
final class Transaction(
    @field:Min(0)
    @field:Column(nullable = false)
    val amount: BigDecimal,
    @field:NotNull
    @field:ManyToOne(fetch = FetchType.LAZY)
    @field:JoinColumn(name = "wallet_from_id", referencedColumnName = "id", nullable = false)
    val from: Wallet,
    @field:NotNull
    @field:ManyToOne(fetch = FetchType.LAZY)
    @field:JoinColumn(name = "wallet_to_id", referencedColumnName = "id", nullable = false)
    val to: Wallet,
    @field:NotNull
    @field:Column(nullable = false, columnDefinition = "DATETIME(3)")
    val timestamp: LocalDateTime = LocalDateTime.now()
): EntityBase<Long>() {

    init {
        from.addPurchase(this)
        to.addRecharge(this)
    }
}

fun Transaction.toDTO(): TransactionDTO =
    TransactionDTO(
        amount,
        from.getId(),
        to.getId(),
        timestamp,
        getId()
    )