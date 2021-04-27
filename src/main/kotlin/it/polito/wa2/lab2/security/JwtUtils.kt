package it.polito.wa2.lab2.security

import it.polito.wa2.lab2.dto.UserDetailsDTO
import org.springframework.security.core.Authentication

interface JwtUtils {
    fun generateJwtToken (authentication: Authentication): String
    fun validateJwtToken (authToken: String): Boolean
    fun getDetailsFromJwtToken (authToken: String): UserDetailsDTO
}