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
    @Value("\${ki.app-key}")
    lateinit var appKey: String
    @Value("\${ki.app-secret}")
    lateinit var appSecret: String

    @PostMapping("")
    suspend fun getApprovalKey(): ResponseEntity<ApprovalKeyResponseDto> {
        val requestDto = KISApprovalRequestDto(
            grantType = "client_credentials",
            appKey = appKey,
            secretKey = appSecret)
        val result = accountService.getApprovalKey(requestDto)
        return ResponseEntity.ok(result)
    }

    @PostMapping("/token")
    suspend fun getAccessToken(): ResponseEntity<AccessTokenAPIResponseDto> {
        val requestDto = KISTokenRequestDto(
            grantType = "client_credentials",
            appKey = appKey,
            appSecret = appSecret)
        val result = accountService.getAccessToken(requestDto)

        return ResponseEntity.ok(result)
    }
}