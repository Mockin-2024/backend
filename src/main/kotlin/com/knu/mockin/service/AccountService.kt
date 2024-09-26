package com.knu.mockin.service

import com.knu.mockin.kisclient.KISOauth2Client
import com.knu.mockin.model.dto.request.KISApprovalRequestDto
import com.knu.mockin.model.dto.request.KISTokenRequestDto
import com.knu.mockin.model.dto.response.AccessTokenAPIResponseDto
import com.knu.mockin.model.dto.response.ApprovalKeyResponseDto
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class AccountService(
    private val kisOauth2Client: KISOauth2Client
) {
    @Value("\${ki.app-key}")
    lateinit var appKey: String
    @Value("\${ki.app-secret}")
    lateinit var appSecret: String

    fun getApprovalKey(): Mono<ResponseEntity<ApprovalKeyResponseDto>> {
        val requestDto = KISApprovalRequestDto(
            grantType = "client_credentials",
            appKey = appKey,
            secretKey = appSecret)
        return kisOauth2Client.postApproval(requestDto)
            .map { dto -> ResponseEntity.ok(dto) }
    }

    fun getAccessToken(): Mono<ResponseEntity<AccessTokenAPIResponseDto>>{
        val requestDto = KISTokenRequestDto(
            grantType = "client_credentials",
            appKey = appKey,
            appSecret = appSecret)
        return kisOauth2Client.postTokenP(requestDto)
            .map { dto -> ResponseEntity.ok(dto) }
    }
}