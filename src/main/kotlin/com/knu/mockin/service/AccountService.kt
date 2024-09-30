package com.knu.mockin.service

import com.knu.mockin.kisclient.KISOauth2Client
import com.knu.mockin.logging.model.LogAPIEntry
import com.knu.mockin.logging.utils.LogUtil
import com.knu.mockin.model.dto.request.KISApprovalRequestDto
import com.knu.mockin.model.dto.request.KISTokenRequestDto
import com.knu.mockin.model.dto.response.AccessTokenAPIResponseDto
import com.knu.mockin.model.dto.response.ApprovalKeyResponseDto
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class AccountService(
    private val kisOauth2Client: KISOauth2Client
) {

    private val log = LoggerFactory.getLogger(AccountService::class.java)
    fun getApprovalKey(requestDto: KISApprovalRequestDto): Mono<ApprovalKeyResponseDto> {
        val result = kisOauth2Client.postApproval(requestDto)
        result.doOnNext{
            dto ->
            log.info("{}", LogUtil.toJson(LogAPIEntry(dto)))
        }.subscribe()
        return result
    }

    fun getAccessToken(requestDto: KISTokenRequestDto): Mono<AccessTokenAPIResponseDto>{
        return kisOauth2Client.postTokenP(requestDto)
    }
}