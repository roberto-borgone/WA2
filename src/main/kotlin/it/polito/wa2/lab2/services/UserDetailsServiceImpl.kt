package it.polito.wa2.lab2.services

import it.polito.wa2.lab2.domain.RoleName
import it.polito.wa2.lab2.domain.User
import it.polito.wa2.lab2.domain.toDTO
import it.polito.wa2.lab2.dto.UserDetailsDTO
import it.polito.wa2.lab2.repositories.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class UserDetailsServiceImpl(private val userRepo: UserRepository): CustomUserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails =
        userRepo.findByUsername(username).orElseThrow{ UsernameNotFoundException("User@$username not found") }.toDTO()

    override fun createUser(username: String, password: String, email:String): UserDetailsDTO =
        when {
            userRepo.existsByUsername(username) -> throw UserAlreadyExistsException(username)
            userRepo.existsByEmail(email) -> throw EmailAlreadyExistsException(email)
            else -> userRepo.save(
                User(username,
                    password,
                    email,
                    "CUSTOMER"
                )
            ).toDTO()
        }

    override fun addRoleToUser(username: String, role: RoleName): UserDetailsDTO{
        val user: User = userRepo.findByUsername(username).orElseThrow{ UsernameNotFoundException("User@$username not found") }
        user.addRole(role)
        return user.toDTO()
    }

    override fun removeRoleFromUser(username: String, role: RoleName): UserDetailsDTO{
        val user: User = userRepo.findByUsername(username).orElseThrow{ UsernameNotFoundException("User@$username not found") }
        user.removeRole(role)
        return user.toDTO()
    }

    override fun disableUser(username: String): UserDetailsDTO{
        val user: User = userRepo.findByUsername(username).orElseThrow{ UsernameNotFoundException("User@$username not found") }
        user.isEnabled = false
        return user.toDTO()
    }

    override fun enableUser(username: String): UserDetailsDTO{
        val user: User = userRepo.findByUsername(username).orElseThrow{ UsernameNotFoundException("User@$username not found") }
        user.isEnabled = true
        return user.toDTO()
    }
}