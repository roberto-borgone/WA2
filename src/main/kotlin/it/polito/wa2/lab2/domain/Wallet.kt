package it.polito.wa2.lab2.domain

import it.polito.wa2.lab2.dto.WalletDTO
import java.math.BigDecimal
import java.time.LocalDateTime
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
    var currentAmount: BigDecimal = BigDecimal(0.0),
    @field:OneToMany(mappedBy = "from", targetEntity = Transaction::class)
    val purchases: MutableSet<Transaction> = mutableSetOf(),
    @field:OneToMany(mappedBy = "to", targetEntity = Transaction::class)
    val recharges: MutableSet<Transaction> = mutableSetOf(),
): EntityBase<Long>() {

    init {
        owner.addWallet(this)
    }

    fun addPurchase(transaction: Transaction){
        purchases.add(transaction)
        currentAmount -= transaction.amount
    }

    fun addRecharge(transaction: Transaction){
        recharges.add(transaction)
        currentAmount += transaction.amount
    }

    fun getTransactions(): List<Transaction> = (purchases + recharges).sortedBy { it.timestamp }

    fun getTransactionsInRange(range: ClosedRange<LocalDateTime>): List<Transaction> =
        (purchases + recharges)
            .filter { range.contains(it.timestamp) }
            .sortedBy { it.timestamp }
}

fun Wallet.toDTO(): WalletDTO =
    WalletDTO(
        owner.getId(),
        currentAmount,
        getId()
    )