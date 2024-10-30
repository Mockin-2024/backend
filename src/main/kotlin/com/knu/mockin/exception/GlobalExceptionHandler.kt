package com.knu.mockin.exception

import com.knu.mockin.logging.utils.LogUtil
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {
    private val log = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)
    @ExceptionHandler(CustomException::class)
    fun handleCustomException(exception: CustomException): ResponseEntity<*> {
        log.error("{}", LogUtil.toJson(ExceptionDto(exception.errorCode.status, exception.errorCode, exception.message)))
        return ResponseEntity.status(exception.errorCode.status)
            .body(ExceptionDto(exception.result, exception.errorCode, exception.message))
    }
}