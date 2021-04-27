package it.polito.wa2.lab2.services

import it.polito.wa2.lab2.domain.EmailVerificationToken
import it.polito.wa2.lab2.domain.User
import it.polito.wa2.lab2.domain.toDTO
import it.polito.wa2.lab2.dto.TokenDTO
import it.polito.wa2.lab2.repositories.EmailVerificationTokenRepository
import it.polito.wa2.lab2.repositories.clearExpiredTokens
import it.polito.wa2.lab2.repositories.findByToken
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
@Transactional
class NotificationServiceImpl(
    val tokenRepository: EmailVerificationTokenRepository
    ) : NotificationService {

    override fun createToken(email: String, user: User): TokenDTO = tokenRepository.save(EmailVerificationToken(email, user)).toDTO()

    override fun validateToken(token: String): Optional<User> =
        tokenRepository.findByToken(UUID.fromString(token))
            .also { it.ifPresent { tkn -> tokenRepository.delete(tkn) } }
            .map { it.user }

    @Configuration
    @EnableScheduling
    @Transactional
    inner class ExpiredTokensCollector{

        @Scheduled(fixedRate = 1000*60*60) // 1 hour
        fun collect() = tokenRepository.clearExpiredTokens()
    }
}