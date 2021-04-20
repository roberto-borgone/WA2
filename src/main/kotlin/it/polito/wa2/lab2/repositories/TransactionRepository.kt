package it.polito.wa2.lab2.repositories

import it.polito.wa2.lab2.domain.Transaction
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*

@Repository
interface TransactionRepository: CrudRepository<Transaction, Long> {
    fun findByIdAndFromIdOrIdAndToId(transactionId1: Long, fromWalletId: Long, transactionId2: Long, toWalletId: Long): Optional<Transaction>
    fun findAllByFromIdOrToIdOrderByTimestamp(fromWalletId: Long, toWalletId: Long): List<Transaction>
    fun findAllByTimestampBetweenAndFromIdOrTimestampBetweenAndToIdOrderByTimestamp(fromTime: LocalDateTime,
                                                                                    toTime: LocalDateTime,
                                                                                    fromWalletId: Long,
                                                                                    fromTime2: LocalDateTime,
                                                                                    toTime1: LocalDateTime,
                                                                                    toWalletId: Long): List<Transaction>
}

/*
 * Facade methods to hide the awkward Spring JPA
 * method signature
 */
fun TransactionRepository.findInWalletById(transactionId: Long, walletId: Long): Optional<Transaction> =
    findByIdAndFromIdOrIdAndToId(transactionId, walletId, transactionId, walletId)

fun TransactionRepository.findAllInWallet(walletId: Long): List<Transaction> =
    findAllByFromIdOrToIdOrderByTimestamp(walletId, walletId)

fun TransactionRepository.findAllInWalletInRange(walletId: Long, range: ClosedRange<LocalDateTime>): List<Transaction> =
    findAllByTimestampBetweenAndFromIdOrTimestampBetweenAndToIdOrderByTimestamp(range.start,
        range.endInclusive, walletId, range.start, range.endInclusive, walletId)
