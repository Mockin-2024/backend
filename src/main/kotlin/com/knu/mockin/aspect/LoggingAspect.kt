package com.knu.mockin.aspect

import com.knu.mockin.logging.model.LogControllerEntry
import com.knu.mockin.logging.utils.LogUtil
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Aspect
@Component
class LoggingAspect {
    private val log = LoggerFactory.getLogger(LoggingAspect::class.java)

    @Around("execution(* com.knu.mockin.controller..*(..))")
    fun logExecutionInController(joinPoint: ProceedingJoinPoint): Any? {
        val traceId = LogUtil.generateTraceId()
        val userId = 1L
        val className = joinPoint.signature.declaringTypeName   // 클래스 이름
        val methodName = joinPoint.signature.name               // 메서드 이름
        val startTime = System.currentTimeMillis()              // 시작 시간
        val timestamp = Instant.now().atZone(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME) // 현재 시간

        val logEntry = LogControllerEntry(timestamp, traceId, userId, className, methodName, 0, "요청 처리 시작")

        log.info("{}", LogUtil.toJson(logEntry))
        val result = joinPoint.proceed()
        val endTime = System.currentTimeMillis() // 종료 시간
        val executionTime = endTime - startTime // 실행 시간 계산
        val updatedLogEntry = logEntry.copy(executionTime = executionTime, message = "요청 처리 종료")
        log.info("{}", LogUtil.toJson(updatedLogEntry))

        return result
    }
}