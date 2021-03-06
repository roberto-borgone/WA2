package it.polito.wa2.lab2.services

import it.polito.wa2.lab2.domain.*
import it.polito.wa2.lab2.dto.CustomerDTO
import it.polito.wa2.lab2.dto.JwtDTO
import it.polito.wa2.lab2.dto.UserDetailsDTO
import it.polito.wa2.lab2.repositories.UserRepository
import it.polito.wa2.lab2.security.JwtUtils
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class UserDetailsServiceImpl(
    private val userRepo: UserRepository,
    val mailService: MailService,
    val notificationService: NotificationService,
    val walletService: WalletService,
    val jwtUtils: JwtUtils
    ): CustomUserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails =
        userRepo.findByUsername(username).orElseThrow{ UsernameNotFoundException("User@$username not found") }.toDTO(true)

    /*
    * TODO: admin creation
    *
    * Admin creation process is not yet defined so here i directly create a customer
    * without checking user role, when admin creation process will be defined this must be
    * modified
    *
    */
    override fun createUser(username: String, password: String, confirmPassword: String, email:String): UserDetailsDTO =
        when {
            password != confirmPassword -> throw PasswordConfirmationException()
            userRepo.existsByUsername(username) -> throw UserAlreadyExistsException(username)
            userRepo.existsByEmail(email) -> throw EmailAlreadyExistsException(email)
            else -> userRepo.save(User(username, password, email))
                .also {
                    mailService.sendMessage(email, notificationService.createToken(it.email, it))
                }.toDTO()
        }

    /*
    * TODO: admin creation
    *
    * Admin creation process is not yet defined so here i directly create a customer
    * without checking user role, when admin creation process will be defined this must be
    * modified
    *
    */
    override fun registrationConfirm(token: String): CustomerDTO =
        walletService.createCustomer(notificationService.validateToken(token).orElseThrow{ InvalidTokenException() }.also { it.isEnabled = true })

    @PreAuthorize("hasRole('ADMIN')")
    override fun addRoleToUser(username: String, role: RoleName): UserDetailsDTO{
        val user: User = userRepo.findByUsername(username).orElseThrow{ UsernameNotFoundException("User@$username not found") }
        user.addRole(role)
        return user.toDTO()
    }

    @PreAuthorize("hasRole('ADMIN')")
    override fun removeRoleFromUser(username: String, role: RoleName): UserDetailsDTO{
        val user: User = userRepo.findByUsername(username).orElseThrow{ UsernameNotFoundException("User@$username not found") }
        user.removeRole(role)
        return user.toDTO()
    }

    @PreAuthorize("hasRole('ADMIN')")
    override fun disableUser(username: String): UserDetailsDTO{
        val user: User = userRepo.findByUsername(username).orElseThrow{ UsernameNotFoundException("User@$username not found") }
        user.isEnabled = false
        return user.toDTO()
    }

    @PreAuthorize("hasRole('ADMIN')")
    override fun enableUser(username: String): UserDetailsDTO{
        val user: User = userRepo.findByUsername(username).orElseThrow{ UsernameNotFoundException("User@$username not found") }
        user.isEnabled = true
        return user.toDTO()
    }

    override fun authenticateUser(username: String, password: String, authenticationManager: AuthenticationManager): JwtDTO {
        try{
            val authentication: Authentication = authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, password))
            return JwtDTO(authentication.name, jwtUtils.generateJwtToken(authentication))
        }catch(ex: AuthenticationException){
            throw BadCredentialsException()
        }
    }


}