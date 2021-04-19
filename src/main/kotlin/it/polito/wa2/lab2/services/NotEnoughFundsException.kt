package it.polito.wa2.lab2.services

class NotEnoughFundsException(walletId: Long): WalletServiceException("Wallet@$walletId funds are not sufficient")