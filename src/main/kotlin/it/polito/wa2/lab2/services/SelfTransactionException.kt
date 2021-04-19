package it.polito.wa2.lab2.services

class SelfTransactionException: WalletServiceException("Self transactions not allowed")