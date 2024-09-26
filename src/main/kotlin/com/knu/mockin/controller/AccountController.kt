package com.knu.mockin.controller

import com.knu.mockin.logging.model.LogEntry
import com.knu.mockin.model.dto.response.AccessTokenAPIResponseDto
import com.knu.mockin.model.dto.response.ApprovalKeyResponseDto
import com.knu.mockin.service.AccountService
import com.knu.mockin.logging.utils.LogUtil
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/account")
class AccountController(
    private val accountService: AccountService
) {

    private val log = LoggerFactory.getLogger(AccountController::class.java)
    @PostMapping("")
    fun getApprovalKey(): Mono<ResponseEntity<ApprovalKeyResponseDto>> {
        val traceId = LogUtil.generateTraceId()
        val userId = 1L
        log.info("{}", LogUtil.toJson(LogEntry(traceId, userId, "/account", "요청 처리 시작")))
        val result = accountService.getApprovalKey()
        log.info("{}", LogUtil.toJson(LogEntry(traceId, userId, "/account", "요청 처리 종료")))
        return result
    }

    @PostMapping("/token")
    fun getAccessToken(): Mono<ResponseEntity<AccessTokenAPIResponseDto>> {
        val traceId = LogUtil.generateTraceId()
        val userId = 1L
        log.info("{}", LogUtil.toJson(LogEntry(traceId, userId, "/account/token", "요청 처리 시작")))
        val result = accountService.getAccessToken()
        log.info("{}", LogUtil.toJson(LogEntry(traceId, userId, "/account/token", "요청 처리 종료")))

        return result
    }
}