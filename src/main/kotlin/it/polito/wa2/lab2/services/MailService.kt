package it.polito.wa2.lab2.services

import it.polito.wa2.lab2.dto.TokenDTO

interface MailService {
    fun sendMessage(toMail: String, token: TokenDTO)
}