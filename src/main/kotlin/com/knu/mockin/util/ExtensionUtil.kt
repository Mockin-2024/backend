package com.knu.mockin.util

import com.knu.mockin.exception.CustomException
import com.knu.mockin.exception.ErrorCode
import reactor.core.publisher.Mono

object ExtensionUtil {
    fun <T> Mono<T>.orThrow(errorCode: ErrorCode): Mono<T> {
        return this.switchIfEmpty(Mono.error(CustomException(errorCode)))
    }
}