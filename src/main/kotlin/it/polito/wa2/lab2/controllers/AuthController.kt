package it.polito.wa2.lab2.controllers

import it.polito.wa2.lab2.dto.*
import it.polito.wa2.lab2.services.CustomUserDetailsService
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.web.bind.annotation.*
import java.net.URI
import javax.validation.Valid

@RestController
@RequestMapping("/auth")
class AuthController(
    val userDetailsService: CustomUserDetailsService,
    val authenticationManager: AuthenticationManager
) {

    @GetMapping("/registrationConfirm")
    fun confirmRegistration(@RequestParam("token") token: String): ResponseEntity<CustomerDTO> =
        ResponseEntity.ok(userDetailsService.registrationConfirm(token))

    @PostMapping("/register")
    fun register(@RequestBody @Valid formDTO: RegistrationFormDTO): ResponseEntity<UserDetailsDTO>{

        val user = userDetailsService.createUser(
            formDTO.username?:"",
            formDTO.password?:"",
            formDTO.confirmPassword?:"",
            formDTO.email?:""
        )
        return ResponseEntity.created(URI("/auth/signin")).body(user)
    }

    @PostMapping("/signin")
    fun signin(@RequestBody @Valid formDTO: SigninFormDTO): ResponseEntity<JwtDTO> =
        ResponseEntity.ok(userDetailsService.authenticateUser(formDTO.username?:"", formDTO.password?:"", authenticationManager))

}