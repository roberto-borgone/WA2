package it.polito.wa2.lab2.repositories

import it.polito.wa2.lab2.domain.Transaction
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TransactionRepository: CrudRepository<Transaction, Long> {
    fun findByIdAndFromIdOrIdAndToId(transactionId1: Long, fromWalletId: Long, transactionId2: Long, toWalletId: Long): Optional<Transaction>
}

/*
 * Facade method to hide the awkward Spring JPA
 * method signature
 */
fun TransactionRepository.findInWalletById(transactionId: Long, walletId: Long): Optional<Transaction> =
    findByIdAndFromIdOrIdAndToId(transactionId, walletId, transactionId, walletId)