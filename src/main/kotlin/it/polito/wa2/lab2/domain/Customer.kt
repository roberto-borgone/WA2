package it.polito.wa2.lab2.domain

import it.polito.wa2.lab2.dto.CustomerDTO
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
class Customer(
    @field:NotBlank
    @field:Column(nullable = false)
    val name: String,
    @field:NotBlank
    @field:Column(nullable = false)
    val surname: String,
    @field:NotBlank
    @field:Column(nullable = false)
    val address: String,
    @field:OneToMany(mappedBy = "owner", targetEntity = Wallet::class)
    val wallets: MutableSet<Wallet> = mutableSetOf(),
    @field:OneToOne(fetch = FetchType.LAZY)
    @field:JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    val userProfile: User
): EntityBase<Long>() {

    fun addWallet(wallet: Wallet){
        wallets.add(wallet)
    }
}

fun Customer.toDTO(): CustomerDTO =
    CustomerDTO(
        name,
        surname,
        address,
        wallets.map { it.getId() }.toList(),
        getId()
    )