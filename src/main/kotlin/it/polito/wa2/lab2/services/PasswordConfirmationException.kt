package it.polito.wa2.lab2.services

class PasswordConfirmationException(): UserDetailsServiceException("Password and password confirmation does not match")