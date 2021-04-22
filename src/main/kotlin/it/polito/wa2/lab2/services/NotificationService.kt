package it.polito.wa2.lab2.services

import it.polito.wa2.lab2.domain.EmailVerificationToken
import it.polito.wa2.lab2.domain.User
import it.polito.wa2.lab2.dto.TokenDTO
import java.util.*

interface NotificationService {
    fun createToken(email: String, user: User): TokenDTO
    fun validateToken(token: String): Optional<User>
}