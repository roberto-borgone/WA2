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
    fun notFoundHandler(ex: WalletServiceException): ResponseEntity<Map<String,String>> =
        ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("error" to (ex.message?:"")))

    @ExceptionHandler(NotEnoughFundsException::class, SelfTransactionException::class)
    fun performTransactionHandler(ex: WalletServiceException): ResponseEntity<Map<String,String>> =
        ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(mapOf("error" to (ex.message?:"")))

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun validatorsExceptionHandler(ex: MethodArgumentNotValidException): ResponseEntity<Map<String,String>>{
        val errors: MutableMap<String, String> = mutableMapOf()
        ex.bindingResult.allErrors.forEach { errors[(it as FieldError).field] = it.defaultMessage?:"" }
        return ResponseEntity.badRequest().body(errors)
    }

    // TODO: implement registration and registrationConfirm exceptions handlers
}