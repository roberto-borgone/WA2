package it.polito.wa2.lab2.services

import it.polito.wa2.lab2.domain.Customer
import it.polito.wa2.lab2.domain.Transaction
import it.polito.wa2.lab2.domain.Wallet
import it.polito.wa2.lab2.domain.toDTO
import it.polito.wa2.lab2.dto.TransactionDTO
import it.polito.wa2.lab2.dto.WalletDTO
import it.polito.wa2.lab2.repositories.*
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.transaction.Transactional

@Service
@Transactional
class WalletServiceImpl(
    val walletRepo: WalletRepository,
    val customerRepo: CustomerRepository,
    val transactionRepo: TransactionRepository
    ): WalletService {

    override fun addWalletToCustomer(customerId: Long): WalletDTO {
        val customer: Customer = customerRepo.findById(customerId).orElseThrow { CustomerNotFoundException(customerId) }
        val wallet: Wallet = walletRepo.save(Wallet(customer))
        return wallet.toDTO()
    }

    override fun getWallet(walletId: Long): WalletDTO =
        walletRepo.findById(walletId).orElseThrow { WalletNotFoundException(walletId) }.toDTO()


    override fun performTransaction(from: Long, to: Long, amount: BigDecimal): TransactionDTO {

        if(from == to)
            throw SelfTransactionException()

        val fromWallet: Wallet = walletRepo.findById(from).orElseThrow { WalletNotFoundException(from) }
        val toWallet: Wallet = walletRepo.findById(to).orElseThrow { WalletNotFoundException(to) }
        if(fromWallet.currentAmount < amount)
            throw NotEnoughFundsException(from)

        val transaction: Transaction = transactionRepo.save(Transaction(amount, fromWallet, toWallet))

        return transaction.toDTO()
    }

    override fun getWalletTransactions(walletId: Long): List<TransactionDTO> =
        if(walletRepo.existsById(walletId))
            transactionRepo.findAllInWallet(walletId).map { it.toDTO() }
        else throw WalletNotFoundException(walletId)


    override fun getWalletTransactionsInDateRange(walletId: Long, range: ClosedRange<LocalDateTime>): List<TransactionDTO> =
        if(walletRepo.existsById(walletId))
            transactionRepo.findAllInWalletInRange(walletId, range).map { it.toDTO() }
        else throw WalletNotFoundException(walletId)

    override fun getTransaction(walletId: Long, transactionId: Long): TransactionDTO =
        if(walletRepo.existsById(walletId))
            transactionRepo.findInWalletById(transactionId, walletId)
                .orElseThrow { TransactionNotFoundException(transactionId) }.toDTO()
        else throw WalletNotFoundException(walletId)
}