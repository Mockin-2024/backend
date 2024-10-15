package com.knu.mockin.aspect

import com.knu.mockin.exeption.CustomException
import com.knu.mockin.exeption.ErrorCode
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClientResponseException
import reactor.core.publisher.Mono

@Aspect
@Component
class ErrorAspect {
    private val log = LoggerFactory.getLogger(ErrorAspect::class.java)

    @Around("execution(* com.knu.mockin.kisclient..*(..))")
    fun handleWebClientException(joinPoint: ProceedingJoinPoint): Any {
        return try {
            val result = joinPoint.proceed()
            if (result is Mono<*>) {
                result.onErrorMap { ex ->
                    when (ex) {
                        is WebClientResponseException -> {
                            when(ex.statusCode.value()){
                                403 -> CustomException(ErrorCode.FORBIDDEN)
                                else -> CustomException(ErrorCode.INTERNAL_SERVER_ERROR,
                                    "KIS 서버에 문제가 생겼습니다. 그러나 잘못된 요청이 원인일 수 있습니다.")
                            }
                        }
                        else -> ex
                    }
                }
            } else {
                result
            }
        } catch (ex: Exception) {
            CustomException(ErrorCode.INTERNAL_SERVER_ERROR)
        }
    }
}