package com.knu.mockin.kisclient

import com.knu.mockin.model.dto.request.KISApprovalRequestDto
import com.knu.mockin.model.dto.request.KISTokenRequestDto
import com.knu.mockin.model.dto.response.AccessTokenAPIResponseDto
import com.knu.mockin.model.dto.response.ApprovalKeyResponseDto
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class KISOauth2Client(
    private val webClient: WebClient
) {
    fun postApproval(KSIApprovalRequestDto: KISApprovalRequestDto): Mono<ApprovalKeyResponseDto>{
        return webClient
            .post()
            .uri("/oauth2/Approval")
            .header("Content-Type", "application/json;charset=UTF-8")
            .bodyValue(KSIApprovalRequestDto)
            .retrieve()
            .bodyToMono(ApprovalKeyResponseDto::class.java)
    }

    fun postTokenP(kisTokenRequestDto: KISTokenRequestDto): Mono<AccessTokenAPIResponseDto> {
        return webClient
            .post()
            .uri("/oauth2/tokenP")
            .header("Content-Type", "application/json;charset=UTF-8")
            .bodyValue(kisTokenRequestDto)
            .retrieve()
            .bodyToMono(AccessTokenAPIResponseDto::class.java)
    }
}