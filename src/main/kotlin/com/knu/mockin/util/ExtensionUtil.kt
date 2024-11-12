package com.knu.mockin.util

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.knu.mockin.exception.CustomException
import com.knu.mockin.exception.ErrorCode
import reactor.core.publisher.Mono

object ExtensionUtil {
    private val objectMapper = jacksonObjectMapper()

    fun <T> Mono<T>.orThrow(errorCode: ErrorCode): Mono<T> {
        return this.switchIfEmpty(Mono.error(CustomException(errorCode)))
    }

    infix fun <T> String.toDto(dto: Class<T>): T {
        return objectMapper.readValue(this, dto)
    }
}