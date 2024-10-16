package com.knu.mockin.exeption

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(CustomException::class)
    fun handleCustomException(exception: CustomException): ResponseEntity<*> {
        return ResponseEntity.status(exception.errorCode.status)
            .body(ExceptionDto(exception.result, exception.errorCode, exception.message))
    }
}