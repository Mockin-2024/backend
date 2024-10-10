package com.knu.mockin.kisclient

import com.knu.mockin.model.dto.kisrequest.oauth.KISApprovalRequestDto
import com.knu.mockin.model.dto.kisrequest.oauth.KISTokenRequestDto
import com.knu.mockin.model.dto.response.AccessTokenAPIResponseDto
import com.knu.mockin.model.dto.response.ApprovalKeyResponseDto
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class KISOauth2RealClient (
        private val webClientReal: WebClient
) {
    fun postApproval(kisApprovalRequestDto: KISApprovalRequestDto): Mono<ApprovalKeyResponseDto> {
        return webClientReal
                .post()
                .uri("/oauth2/Approval")
                .header("Content-Type", "application/json;charset=UTF-8")
                .bodyValue(kisApprovalRequestDto)
                .retrieve()
                .bodyToMono(ApprovalKeyResponseDto::class.java)
    }

    fun postTokenP(kisTokenRequestDto: KISTokenRequestDto): Mono<AccessTokenAPIResponseDto> {
        return webClientReal
                .post()
                .uri("/oauth2/tokenP")
                .header("Content-Type", "application/json;charset=UTF-8")
                .bodyValue(kisTokenRequestDto)
                .retrieve()
                .bodyToMono(AccessTokenAPIResponseDto::class.java)
    }
}