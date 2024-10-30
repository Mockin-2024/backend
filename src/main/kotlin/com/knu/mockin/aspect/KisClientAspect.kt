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
    fun handleKISWebClientException(joinPoint: ProceedingJoinPoint): Any {
        val traceId = LogUtil.generateTraceId()
        val userId = 1L
        val className = joinPoint.signature.declaringTypeName
        val methodName = joinPoint.signature.name
        val timestamp = Instant.now().atZone(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME) // 현재 시간
        val args = joinPoint.args

        val beforeLog = LogKisClientEntry(timestamp,traceId,userId, className, methodName, args,"요청 처리 시작")

        return try {
            log.info("{}", toJson(beforeLog))
            val result = joinPoint.proceed() as Mono<*>
            result.subscribe { value ->
                val logEntry = LogKisClientEntry(timestamp, traceId, userId, className, methodName, value, "요청 처리 종료")
                log.info("{}", toJson(logEntry))
            }
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
                        else -> CustomException(ErrorCode.INTERNAL_SERVER_ERROR, ex.message)
                    }
                }.returnWhenSuccess()
        } catch (ex: Exception) {
            Mono.error<CustomException>(CustomException(ErrorCode.INTERNAL_SERVER_ERROR, ex.message))
        }
    }
    private fun <T> Mono<T>.returnWhenSuccess():Mono<T> {
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