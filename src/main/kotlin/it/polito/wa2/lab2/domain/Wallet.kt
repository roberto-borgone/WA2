package it.polito.wa2.lab2.domain

import it.polito.wa2.lab2.dto.WalletDTO
import java.math.BigDecimal
import javax.persistence.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

@Entity
final class Wallet(
    @field:ManyToOne(fetch = FetchType.LAZY)
    @field:JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    @field:NotNull
    val owner: Customer,
    @field:Min(0)
    @field:Column(nullable = false)
    var currentAmount: BigDecimal = BigDecimal(0.0),
): EntityBase<Long>() {

    init {
        owner.addWallet(this)
    }

    fun addPurchase(transaction: Transaction){
        currentAmount -= transaction.amount
    }

    fun addRecharge(transaction: Transaction){
        currentAmount += transaction.amount
    }

}

fun Wallet.toDTO(): WalletDTO =
    WalletDTO(
        owner.getId(),
        currentAmount,
        getId()
    )