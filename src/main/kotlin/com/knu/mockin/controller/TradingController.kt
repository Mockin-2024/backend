package com.knu.mockin.controller

import com.knu.mockin.logging.model.LogEntry
import com.knu.mockin.logging.utils.LogUtil
import com.knu.mockin.model.dto.kisresponse.order.KISOverSeaResponseDto
import com.knu.mockin.service.TradingService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/trading")
class TradingController(
    private val tradingService: TradingService,
) {
    private val log = LoggerFactory.getLogger(TradingController::class.java)
    @PostMapping("/order")
    suspend fun order(): ResponseEntity<KISOverSeaResponseDto>{
        val traceId = LogUtil.generateTraceId()
        val userId = 1L

        log.info("{}", LogUtil.toJson(LogEntry(traceId, userId, "/order", "요청 처리 시작")))
        val result = tradingService.order()
        log.info("{}", LogUtil.toJson(LogEntry(traceId, userId, "/order", "요청 처리 종료")))

        return ResponseEntity.ok(result)
    }
}