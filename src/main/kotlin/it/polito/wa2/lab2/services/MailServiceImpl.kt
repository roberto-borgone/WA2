package it.polito.wa2.lab2.services

import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class MailServiceImpl(
    private val mailSender: JavaMailSender,
    @Value("\${spring.mail.username}")
    val fromMail: String
    ): MailService {

    val subject: String = "[WA2] Please confirm your email"
    val mailBody: String = "Please click on the link below to confirm your registration"

    override fun sendMessage(toMail: String) {
        val mail: SimpleMailMessage = SimpleMailMessage()
        mail.setFrom(fromMail)
        mail.setTo(toMail)
        mail.setSubject(subject)
        mail.setText(mailBody)
        mailSender.send(mail)
    }
}