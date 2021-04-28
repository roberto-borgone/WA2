package it.polito.wa2.lab2.services

class BadCredentialsException: UserDetailsServiceException("Invalid username/password")