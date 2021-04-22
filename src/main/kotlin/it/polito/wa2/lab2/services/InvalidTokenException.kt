package it.polito.wa2.lab2.services

class InvalidTokenException(): UserDetailsServiceException("Invalid token, it could be expired")