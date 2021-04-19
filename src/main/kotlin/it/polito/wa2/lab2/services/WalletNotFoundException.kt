package it.polito.wa2.lab2.services

class WalletNotFoundException(walletId: Long): WalletServiceException("Wallet@$walletId not found")