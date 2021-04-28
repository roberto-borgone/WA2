package it.polito.wa2.lab2.controllers

import it.polito.wa2.lab2.services.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ControllerExceptionHandler {

    @ExceptionHandler(
        CustomerNotFoundException::class,
        TransactionNotFoundException::class,
        WalletNotFoundException::class
    )
    fun notFoundWalletHandler(ex: WalletServiceException): ResponseEntity<Map<String,String>> =
        ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("error" to (ex.message?:"")))

    @ExceptionHandler(NotEnoughFundsException::class, SelfTransactionException::class)
    fun performTransactionHandler(ex: WalletServiceException): ResponseEntity<Map<String,String>> =
        ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("error" to (ex.message?:"")))

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun validatorsExceptionHandler(ex: MethodArgumentNotValidException): ResponseEntity<Map<String,String>>{
        val errors: MutableMap<String, String> = mutableMapOf()
        ex.bindingResult.allErrors.forEach { errors[(it as FieldError).field] = it.defaultMessage?:"" }
        return ResponseEntity.badRequest().body(errors)
    }

    @ExceptionHandler(EmailAlreadyExistsException::class, UserAlreadyExistsException::class)
    fun registrationDuplicateExceptionHandler(ex: UserDetailsServiceException): ResponseEntity<Map<String, String>> =
        ResponseEntity.status(HttpStatus.CONFLICT).body(mapOf("error" to (ex.message?:"")))

    @ExceptionHandler(PasswordConfirmationException::class, InvalidTokenException::class)
    fun registrationBadRequestExceptionHandler(ex: UserDetailsServiceException): ResponseEntity<Map<String, String>> =
        ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("error" to (ex.message?:"")))

    @ExceptionHandler(BadCredentialsException::class)
    fun signinExceptionHandler(ex: UserDetailsServiceException): ResponseEntity<Map<String, String>> =
        ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(mapOf("error" to (ex.message?:"")))
}