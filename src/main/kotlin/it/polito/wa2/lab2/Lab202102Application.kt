package it.polito.wa2.lab2

import it.polito.wa2.lab2.domain.RoleName
import it.polito.wa2.lab2.dto.UserDetailsDTO
import it.polito.wa2.lab2.services.CustomUserDetailsService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder

@SpringBootApplication
class Lab202102Application{
	@Bean
	fun passwordEncoder(): PasswordEncoder =
		PasswordEncoderFactories.createDelegatingPasswordEncoder()
}

fun main(args: Array<String>) {
	runApplication<Lab202102Application>(*args)
}
