package it.polito.wa2.lab2.services

import it.polito.wa2.lab2.dto.TransactionDTO
import it.polito.wa2.lab2.dto.WalletDTO
import java.math.BigDecimal
import java.time.LocalDateTime

interface WalletService {

    fun addWalletToCustomer(customerId: Long): WalletDTO
    fun getWallet(walletId: Long): WalletDTO
    fun performTransaction(from: Long, to: Long, amount: BigDecimal): TransactionDTO
    fun getWalletTransactions(walletId: Long): List<TransactionDTO>
    fun getWalletTransactionsInDateRange(walletId: Long, range: ClosedRange<LocalDateTime>): List<TransactionDTO>
    fun getTransaction(walletId: Long, transactionId: Long): TransactionDTO

}