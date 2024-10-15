package com.knu.mockin.util

import com.knu.mockin.exeption.CustomException
import com.knu.mockin.exeption.ErrorCode
import reactor.core.publisher.Mono
import kotlin.reflect.full.memberProperties

object ExtensionUtil {
    fun <T> Mono<T>.orThrow(errorCode: ErrorCode): Mono<T> {
        return this.switchIfEmpty(Mono.error(CustomException(errorCode)))
    }

    fun <T> Mono<T>.returnWhenSuccess():Mono<T> {
        return this.flatMap { instance ->
            val properties = instance!!::class.memberProperties
            val successFailureCode = properties.find { it.name == "successFailureStatus" }?.call(instance)
            val message = properties.find { it.name == "responseMessage" }?.call(instance)

            if (successFailureCode != null && successFailureCode != "0") {
                return@flatMap Mono.error(CustomException(ErrorCode.KIS_API_FAILED, message.toString()))
            }

            Mono.just(instance)
        }
    }
}