package com.knu.mockin.aspect

import com.knu.mockin.exception.CustomException
import com.knu.mockin.exception.ErrorCode
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClientResponseException
import reactor.core.publisher.Mono
import kotlin.reflect.full.memberProperties

@Aspect
@Component
class ErrorAspect {
    @Around("execution(* com.knu.mockin.kisclient..*(..))")
    fun handleKISWebClientException(joinPoint: ProceedingJoinPoint): Any {
        return try {
            val result = joinPoint.proceed() as Mono<*>

            result
                .onErrorMap { ex ->
                    when (ex) {
                        is WebClientResponseException -> {
                            when (ex.statusCode.value()) {
                                403 -> CustomException(ErrorCode.FORBIDDEN)
                                else -> CustomException(
                                    ErrorCode.INTERNAL_SERVER_ERROR,
                                    "KIS 서버에 문제가 생겼습니다. 그러나 잘못된 요청이 원인일 수 있습니다."
                                )
                            }
                        }
                        else -> ex
                    }
                }.returnWhenSuccess()
        } catch (ex: Exception) {
            Mono.error(CustomException(ErrorCode.INTERNAL_SERVER_ERROR, ex.message))
        }
    }
    private fun <T> Mono<T>.returnWhenSuccess():Mono<T> {
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