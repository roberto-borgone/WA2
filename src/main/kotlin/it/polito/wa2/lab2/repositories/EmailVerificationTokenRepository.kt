package it.polito.wa2.lab2.repositories

import it.polito.wa2.lab2.domain.EmailVerificationToken
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*

@Repository
interface EmailVerificationTokenRepository: CrudRepository<EmailVerificationToken, Long> {
    fun findByTokenAndExpiryDateTimeIsAfter(token: UUID, now: LocalDateTime = LocalDateTime.now()): Optional<EmailVerificationToken>
    fun deleteAllByExpiryDateTimeBefore(now: LocalDateTime = LocalDateTime.now())
}

fun EmailVerificationTokenRepository.findByToken(token: UUID): Optional<EmailVerificationToken> =
    findByTokenAndExpiryDateTimeIsAfter(token)

fun EmailVerificationTokenRepository.clearExpiredTokens() = deleteAllByExpiryDateTimeBefore()