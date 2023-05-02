package com.acumulus.acumulusassignment.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.server.reactive.HttpHandlerDecoratorFactory
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver

@ControllerAdvice
class GlobalExceptionHandler: ExceptionHandlerExceptionResolver() {
    class ErrorResponse(code: HttpStatus, message: String)


    @ExceptionHandler(DuplicateUserException::class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    fun handleDuplicateErrorException(): ResponseEntity<ErrorResponse>{
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY,"User is already registered"))
    }

    @ExceptionHandler(UserNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleUserNotFoundException(): ResponseEntity<ErrorResponse>{
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ErrorResponse(HttpStatus.NOT_FOUND,"User not found"))
    }

    
}