package com.knu.mockin.aspect

import com.knu.mockin.exception.CustomException
import com.knu.mockin.exception.ErrorCode
import com.knu.mockin.logging.model.LogKisClientEntry
import com.knu.mockin.logging.utils.LogUtil
import com.knu.mockin.logging.utils.LogUtil.toJson
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.LoggerFactory
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClientResponseException
import reactor.core.publisher.Mono
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.reflect.full.memberProperties

@Aspect
@Component
class KisClientAspect {
    private val log = LoggerFactory.getLogger(KisClientAspect::class.java)

    @Around("execution(* com.knu.mockin.kisclient..*(..))")
    fun handleKISWebClientException(joinPoint: ProceedingJoinPoint): Mono<Any?> {
        val traceId = LogUtil.generateTraceId()
        val className = joinPoint.signature.declaringTypeName
        val methodName = joinPoint.signature.name
        val timestamp = Instant.now().atZone(ZoneId.of("Asia/Seoul"))
            .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        val args = joinPoint.args

        return ReactiveSecurityContextHolder.getContext()
                .flatMap { securityContext ->
                    val userId = securityContext.authentication.name

                    val beforeLog = LogKisClientEntry(timestamp, traceId, userId, className, methodName, args, "요청 처리 시작")
                    log.info("{}", toJson(beforeLog))

                    val result = joinPoint.proceed() as Mono<*>
                    result
                        .doOnNext { value ->
                            val afterLog = LogKisClientEntry(timestamp, traceId, userId, className, methodName, value, "요청 처리 종료")
                            log.info("{}", toJson(afterLog))
                        }
                        .onErrorMap { ex ->
                            handleWebClientResponseException(ex)
                        }.returnWhenSuccess()
                }

    }

    private fun handleWebClientResponseException(ex: Throwable): CustomException {
        return when (ex) {
            is WebClientResponseException -> {
                when (ex.statusCode.value()) {
                    403 -> CustomException(ErrorCode.FORBIDDEN)
                    else -> CustomException(
                        ErrorCode.INTERNAL_SERVER_ERROR,
                        "KIS 서버에 문제가 생겼습니다. 그러나 잘못된 요청이 원인일 수 있습니다."
                    )
                }
            }
            else -> CustomException(ErrorCode.INTERNAL_SERVER_ERROR, ex.message)
        }
    }

    private fun <T> Mono<T>.returnWhenSuccess(): Mono<T> {
        return this.flatMap { instance ->
            val properties = instance!!::class.memberProperties
            val successFailureCode = properties.find { it.name == "successFailureStatus" }?.call(instance)
            val message = properties.find { it.name == "responseMessage" }?.call(instance)

            if (successFailureCode != null && successFailureCode != "0") {
                val exception = CustomException(ErrorCode.KIS_API_FAILED, message.toString())
                return@flatMap Mono.error(exception)
            }

            Mono.just(instance)
        }
    }
}