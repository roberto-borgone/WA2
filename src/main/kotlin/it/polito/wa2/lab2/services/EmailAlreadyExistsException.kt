package it.polito.wa2.lab2.services

class EmailAlreadyExistsException(email: String): UserDetailsServiceException("Email $email is already linked to a user")