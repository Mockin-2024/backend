package com.knu.mockin.controller

import com.knu.mockin.logging.model.LogEntry
import com.knu.mockin.model.dto.response.AccessTokenAPIResponseDto
import com.knu.mockin.model.dto.response.ApprovalKeyResponseDto
import com.knu.mockin.service.AccountService
import com.knu.mockin.logging.utils.LogUtil
import com.knu.mockin.model.dto.kisrequest.oauth.KISApprovalRequestDto
import com.knu.mockin.model.dto.kisrequest.oauth.KISTokenRequestDto
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
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
    @Value("\${ki.app-key}")
    lateinit var appKey: String
    @Value("\${ki.app-secret}")
    lateinit var appSecret: String

    private val log = LoggerFactory.getLogger(AccountController::class.java)
    @PostMapping("")
    fun getApprovalKey(): Mono<ResponseEntity<ApprovalKeyResponseDto>> {
        val traceId = LogUtil.generateTraceId()
        val userId = 1L
        log.info("{}", LogUtil.toJson(LogEntry(traceId, userId, "/account", "요청 처리 시작")))
        val requestDto = KISApprovalRequestDto(
            grantType = "client_credentials",
            appKey = appKey,
            secretKey = appSecret)
        val result = accountService.getApprovalKey(requestDto)
            .doOnNext {
                result ->
                log.info("{}", LogUtil.toJson(LogEntry(traceId, userId, "/account", LogUtil.toJson(result))))
            }
        return result.map {
            dto-> ResponseEntity.ok(dto)
        }
    }

    @PostMapping("/token")
    fun getAccessToken(): Mono<ResponseEntity<AccessTokenAPIResponseDto>> {
        val traceId = LogUtil.generateTraceId()
        val userId = 1L
        log.info("{}", LogUtil.toJson(LogEntry(traceId, userId, "/account/token", "요청 처리 시작")))
        val requestDto = KISTokenRequestDto(
            grantType = "client_credentials",
            appKey = appKey,
            appSecret = appSecret)
        val result = accountService.getAccessToken(requestDto)
        log.info("{}", LogUtil.toJson(LogEntry(traceId, userId, "/account/token", "요청 처리 종료")))

        return result
            .map { dto -> ResponseEntity.ok(dto) }
    }
}