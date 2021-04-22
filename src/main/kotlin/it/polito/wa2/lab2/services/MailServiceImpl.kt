package it.polito.wa2.lab2.services

import it.polito.wa2.lab2.dto.TokenDTO
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMailMessage
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service

@Service
class MailServiceImpl(
    private val mailSender: JavaMailSender,
    @Value("\${spring.mail.username}")
    val fromMail: String
    ): MailService {

    val subject: String = "Confirm your e-mail address on WA2"

    override fun sendMessage(toMail: String, token: TokenDTO) {

        val mailBody: String = """
            <h1>Confirm your e-mail address to start using WA2</h1>
            <p style="font-size: 20px;">Once confirmed that <u><strong>$toMail</strong></u> is your e-mail address
            you will be able to access the web application functionalities.</p>
            <p style="font-size: 20px;">Click on the link below to confirm your e-mail:</p>
            <p style="font-size: 20px;">
            <a style="background-color: #39b359; color: white; " href="http://localhost:8080/auth/registrationConfirm?token=${token.token}">Confirm e-mail address</a>
            </p>
            <p style="font-size: 20px;">If you did not request this e-mail, don't worry, you can ignore it.</p>
            """.trimIndent()

        val mail = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(mail, "utf-8")
        helper.setFrom(fromMail, "WA2")
        helper.setTo(toMail)
        helper.setSubject(subject)
        helper.setText(mailBody, true)
        mailSender.send(mail)
    }
}