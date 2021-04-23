package it.polito.wa2.lab2

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.scheduling.annotation.Scheduled
import java.util.*

@SpringBootApplication
class Lab202102Application{

	@Bean
	fun getMailSender(
		@Value("\${spring.mail.host}") host: String,
		@Value("\${spring.mail.port}") port: Int,
		@Value("\${spring.mail.username}") username: String,
		@Value("\${spring.mail.password}") password: String,
		@Value("\${spring.mail.properties.mail.smtp.auth}") auth: String,
		@Value("\${spring.mail.properties.mail.smtp.starttls.enable}") starttls: String,
		@Value("\${spring.mail.properties.mail.debug}") debug: String
	): JavaMailSender = JavaMailSenderImpl().also {

		it.host = host
		it.port = port

		it.username = username
		it.password = password

		val props: Properties = it.javaMailProperties
		props["mail.smtp.auth"] = auth
		props["mail.smtp.starttls.enable"] = starttls
		props["mail.debug"] = debug
	}
}

fun main(args: Array<String>) {
	runApplication<Lab202102Application>(*args)
}
