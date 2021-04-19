package it.polito.wa2.lab2.services

class TransactionNotFoundException(transactionId: Long): WalletServiceException("Transaction@$transactionId not found")