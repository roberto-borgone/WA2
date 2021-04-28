package it.polito.wa2.lab2.services

import it.polito.wa2.lab2.domain.RoleName
import it.polito.wa2.lab2.dto.CustomerDTO
import it.polito.wa2.lab2.dto.JwtDTO
import it.polito.wa2.lab2.dto.UserDetailsDTO
import org.springframework.security.core.userdetails.UserDetailsService

interface CustomUserDetailsService: UserDetailsService {

    fun createUser(username: String, password: String, confirmPassword: String, email:String): UserDetailsDTO
    fun registrationConfirm(token: String): CustomerDTO
    fun addRoleToUser(username: String, role: RoleName): UserDetailsDTO
    fun removeRoleFromUser(username: String, role: RoleName): UserDetailsDTO
    fun disableUser(username: String): UserDetailsDTO
    fun enableUser(username: String): UserDetailsDTO
    fun authenticateUser(username: String, password: String): JwtDTO
}