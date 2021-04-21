package it.polito.wa2.lab2.controllers

import it.polito.wa2.lab2.dto.CustomerDTO
import it.polito.wa2.lab2.dto.RegistrationFormDTO
import it.polito.wa2.lab2.dto.UserDetailsDTO
import it.polito.wa2.lab2.services.CustomUserDetailsService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import javax.validation.Valid

@RestController
@RequestMapping("/auth")
class AuthController(val userDetailsService: CustomUserDetailsService) {

    @GetMapping("/registrationConfirm")
    fun confirmRegistration(@RequestParam("token") token: String): ResponseEntity<CustomerDTO> =
        /*
        * TODO: implement this method
        *
        * it should create a Customer if the user related to the token
        * is of type Customer and return its DTO. Response Entity created if success
        * ResponseEntity of a kind of error (Exception must be defined) if the token
        * is non existing or expired
        */
        ResponseEntity.ok(CustomerDTO(1,"","",""))

    @PostMapping("/register")
    fun register(@RequestBody @Valid formDTO: RegistrationFormDTO): ResponseEntity<UserDetailsDTO>{

        // TODO: test this method

        val user = userDetailsService.createUser(
            formDTO.username?:"",
            formDTO.password?:"",
            formDTO.confirmPassword?:"",
            formDTO.email?:""
        )
        return ResponseEntity.created(URI("/auth/signin")).body(user)
    }

    @PostMapping("/signin")
    fun signin(@RequestBody @Valid formDTO: RegistrationFormDTO) =

        // TODO: implement this method

        ResponseEntity.ok()

}