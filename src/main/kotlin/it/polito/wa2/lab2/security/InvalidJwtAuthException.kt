package it.polito.wa2.lab2.security

import org.springframework.security.core.AuthenticationException

class InvalidJwtAuthException: AuthenticationException("Invalid Jwt")