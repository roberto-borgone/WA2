package it.polito.wa2.lab2.services

class UserAlreadyExistsException(username: String): UserDetailsServiceException("User@$username already exists")