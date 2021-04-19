package it.polito.wa2.lab2.services

class CustomerNotFoundException(customerId: Long): WalletServiceException("Customer@$customerId not found")