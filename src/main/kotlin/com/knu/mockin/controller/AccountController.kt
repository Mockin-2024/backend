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

@RestController
@RequestMapping("/account")
class AccountController(
    private val accountService: AccountService
) {
    @Value("\${ki.mock.app-key}")
    lateinit var mockAppKey: String
    @Value("\${ki.mock.app-secret}")
    lateinit var mockAppSecret: String

    @Value("\${ki.real.app-key}")
    lateinit var realAppKey : String
    @Value("\${ki.real.app-secret}")
    lateinit var realAppSecret : String

    private val log = LoggerFactory.getLogger(AccountController::class.java)
    @PostMapping("mock-approval")
    suspend fun getMockApprovalKey(): ResponseEntity<ApprovalKeyResponseDto> {
        val traceId = LogUtil.generateTraceId()
        val userId = 1L
        log.info("{}", LogUtil.toJson(LogEntry(traceId, userId, "/account/mock-approval", "요청 처리 시작")))
        val requestDto = KISApprovalRequestDto(
            grantType = "client_credentials",
            appKey = mockAppKey,
            secretKey = mockAppSecret)
        val result = accountService.getApprovalKey(requestDto)
        log.info("{}", LogUtil.toJson(LogEntry(traceId, userId, "/account/mock-approval", LogUtil.toJson(result))))
        return ResponseEntity.ok(result)
    }

    @PostMapping("real-approval")
    suspend fun getRealApprovalKey(): ResponseEntity<ApprovalKeyResponseDto> {
        val traceId = LogUtil.generateTraceId()
        val userId = 1L
        log.info("{}", LogUtil.toJson(LogEntry(traceId, userId, "/account/real-approval", "요청 처리 시작")))
        val requestDto = KISApprovalRequestDto(
                grantType = "client_credentials",
                appKey = realAppKey,
                secretKey = realAppSecret)
        val result = accountService.getApprovalKey(requestDto)
        log.info("{}", LogUtil.toJson(LogEntry(traceId, userId, "/account/real-approval", LogUtil.toJson(result))))
        return ResponseEntity.ok(result)
    }

    @PostMapping("/mock-token")
    suspend fun getMockAccessToken(): ResponseEntity<AccessTokenAPIResponseDto> {
        val traceId = LogUtil.generateTraceId()
        val userId = 1L
        log.info("{}", LogUtil.toJson(LogEntry(traceId, userId, "/account/mock-token", "요청 처리 시작")))
        val requestDto = KISTokenRequestDto(
            grantType = "client_credentials",
            appKey = mockAppKey,
            appSecret = mockAppSecret)
        val result = accountService.getMockAccessToken(requestDto)
        log.info("{}", LogUtil.toJson(LogEntry(traceId, userId, "/account/mock-token", "요청 처리 종료")))

        return ResponseEntity.ok(result)
    }

    @PostMapping("/real-token")
    suspend fun getRealAccessToken(): ResponseEntity<AccessTokenAPIResponseDto> {
        val traceId = LogUtil.generateTraceId()
        val userId = 1L
        log.info("{}", LogUtil.toJson(LogEntry(traceId, userId, "/account/real-token", "요청 처리 시작")))
        val requestDto = KISTokenRequestDto(
                grantType = "client_credentials",
                appKey = realAppKey,
                appSecret = realAppSecret)
        val result = accountService.getRealAccessToken(requestDto)
        log.info("{}", LogUtil.toJson(LogEntry(traceId, userId, "/account/real-token", "요청 처리 종료")))

        return ResponseEntity.ok(result)
    }
}