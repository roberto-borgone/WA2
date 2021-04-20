package it.polito.wa2.lab2.repositories

import it.polito.wa2.lab2.domain.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: CrudRepository<User, Long> {
    fun findByUsername(username: String): User?
}