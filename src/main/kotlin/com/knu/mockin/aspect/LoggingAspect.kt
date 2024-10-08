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
        val className = joinPoint.signature.declaringTypeName
        val methodName = joinPoint.signature.name
        val startTime = System.currentTimeMillis()
        val timestamp = Instant.now().atZone(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME) // 현재 시간

        val beforeLog = LogControllerEntry(timestamp, traceId, userId, className, methodName, 0, "요청 처리 시작")
        log.info("{}", LogUtil.toJson(beforeLog))

        val result = joinPoint.proceed()
        val endTime = System.currentTimeMillis()
        val executionTime = endTime - startTime

        val afterLog = beforeLog.copy(executionTime = executionTime, message = "요청 처리 종료")
        log.info("{}", LogUtil.toJson(afterLog))

        return result
    }
}