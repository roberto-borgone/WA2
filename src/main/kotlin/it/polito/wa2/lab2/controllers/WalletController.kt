package it.polito.wa2.lab2.controllers

import it.polito.wa2.lab2.dto.AddWalletToCustomerFormDTO
import it.polito.wa2.lab2.dto.PerformTransactionFormDTO
import it.polito.wa2.lab2.dto.TransactionDTO
import it.polito.wa2.lab2.dto.WalletDTO
import it.polito.wa2.lab2.services.WalletService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal
import java.net.URI
import java.time.LocalDateTime
import javax.validation.Valid

@RestController
@RequestMapping("/wallet")
class WalletController(val service: WalletService) {

    @PostMapping("/","")
    fun addWalletToCustomer(@RequestBody @Valid formDTO: AddWalletToCustomerFormDTO): ResponseEntity<WalletDTO>{
        val wallet: WalletDTO = service.addWalletToCustomer(formDTO.customerId?:-1)
        return ResponseEntity.created(URI("/wallet/${wallet.id}")).body(wallet)
    }

    @GetMapping("/{walletID}")
    fun getWallet(@PathVariable("walletID") walletID: Long): ResponseEntity<WalletDTO> =
        ResponseEntity.ok(service.getWallet(walletID))

    @PostMapping("/{walletID}/transaction")
    fun performTransaction(@PathVariable("walletID") from: Long, @RequestBody @Valid formDTO: PerformTransactionFormDTO): ResponseEntity<TransactionDTO>{
        val transaction: TransactionDTO = service.performTransaction(from, formDTO.to?:-1, formDTO.amount?: BigDecimal(-1))
        return ResponseEntity.created(URI("/wallet/$from/transactions/${transaction.id}")).body(transaction)
    }

    @GetMapping("/{walletID}/transactions")
    fun getWalletTransactionsInDateRange(
        @RequestParam("from") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS") from: LocalDateTime,
        @RequestParam("to") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS") to: LocalDateTime,
        @PathVariable("walletID") walletID: Long) : ResponseEntity<List<TransactionDTO>> =
        ResponseEntity.ok(service.getWalletTransactionsInDateRange(walletID, from..to))

    @GetMapping("/{walletID}/transactions/{transactionID}")
    fun getTransaction(@PathVariable("walletID") walletID: Long,
                       @PathVariable("transactionID") transactionID: Long): ResponseEntity<TransactionDTO> =
        ResponseEntity.ok(service.getTransaction(walletID, transactionID))

}