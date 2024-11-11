package com.knu.mockin.aspect

import com.knu.mockin.logging.model.LogControllerEntry
import com.knu.mockin.logging.utils.LogUtil
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.LoggerFactory
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Aspect
@Component
class LoggingAspect {
    private val log = LoggerFactory.getLogger(LoggingAspect::class.java)

    @Around("execution(* com.knu.mockin.controller..*(..))")
    fun logExecutionInController(joinPoint: ProceedingJoinPoint): Mono<Any?> {
        val traceId = LogUtil.generateTraceId()
        val className = joinPoint.signature.declaringTypeName
        val methodName = joinPoint.signature.name
        val startTime = System.currentTimeMillis()
        val timestamp = Instant.now().atZone(ZoneId.of("Asia/Seoul"))
            .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME) // 현재 시간

        return ReactiveSecurityContextHolder.getContext()
            .flatMap { securityContext ->
                val userId = securityContext.authentication.name

                val beforeLog = LogControllerEntry(
                    timestamp = timestamp,
                    traceId = traceId,
                    userId = userId,
                    className = className,
                    methodName = methodName,
                    executionTime = 0,
                    message = "요청 처리 시작"
                )
                log.info("{}", LogUtil.toJson(beforeLog))

                // 실제 메소드 호출
                val result = joinPoint.proceed() // 이 부분은 비동기 처리에 맞게 수정해야 할 수 있음

                // 결과를 Mono로 래핑하여 반환
                Mono.just(result)
                    .doOnNext {
                        val endTime = System.currentTimeMillis()
                        val executionTime = endTime - startTime

                        val afterLog = beforeLog.copy(
                            executionTime = executionTime,
                            message = "요청 처리 종료"
                        )
                        log.info("{}", LogUtil.toJson(afterLog))
                    }
            }
    }
}