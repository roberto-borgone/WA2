package it.polito.wa2.lab2.dto

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class RegistrationFormDTO(
    @field:NotBlank
    val username: String?,
    @field:NotBlank
    val password: String?,
    @field:NotBlank
    val confirmPassword: String?,
    @field:NotBlank
    @field:Email
    val email:String?
    )

data class SigninFormDTO(
    @field:NotBlank
    val username: String?,
    @field:NotBlank
    val password: String?
)