package com.knu.mockin.kisclient

import com.knu.mockin.model.dto.kisrequest.oauth.KISApprovalRequestDto
import com.knu.mockin.model.dto.kisrequest.oauth.KISTokenRequestDto
import com.knu.mockin.model.dto.response.AccessTokenAPIResponseDto
import com.knu.mockin.model.dto.response.ApprovalKeyResponseDto
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class KISOauth2Client(
    private val webClientMock: WebClient
) {
    val oauthUri = "/oauth2"
    fun postApproval(
        bodyDto: KISApprovalRequestDto
    ): Mono<ApprovalKeyResponseDto>{
        val uri = "$oauthUri/Approval"
        return webClientMock.postWithBody(
            uri = uri,
            bodyDto = bodyDto,
            responseType = ApprovalKeyResponseDto::class.java
        )
    }

    fun postTokenP(
        bodyDto: KISTokenRequestDto
    ): Mono<AccessTokenAPIResponseDto> {
        val uri = "$oauthUri/tokenP"
        return webClientMock.postWithBody(
            uri = uri,
            bodyDto = bodyDto,
            responseType = AccessTokenAPIResponseDto::class.java
        )
    }
}