package com.knu.mockin.service

import com.knu.mockin.kisclient.KISOauth2Client
import com.knu.mockin.model.dto.kisrequest.oauth.KISApprovalRequestDto
import com.knu.mockin.model.dto.kisrequest.oauth.KISTokenRequestDto
import com.knu.mockin.model.dto.response.AccessTokenAPIResponseDto
import com.knu.mockin.model.dto.response.ApprovalKeyResponseDto
import com.knu.mockin.repository.UserRepository
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.stereotype.Service

@Service
class AccountService(
    private val kisOauth2Client: KISOauth2Client,
    private val userRepository: UserRepository
) {
    suspend fun getApprovalKey(requestDto: KISApprovalRequestDto): ApprovalKeyResponseDto {
        return kisOauth2Client.postApproval(requestDto).awaitSingle()
    }

    suspend fun getMockAccessToken(requestDto: KISTokenRequestDto): AccessTokenAPIResponseDto {
        val user = userRepository.findById(1).awaitFirst() // Mono<User>를 awaitFirst()로 변환
        val dto = kisOauth2Client.postMockTokenP(requestDto).awaitSingle() // Mono<AccessTokenAPIResponseDto>를 awaitSingle()로 변환

        user.token = dto.accessToken
        userRepository.save(user).awaitSingle() // 사용자 저장 후 완료 대기

        return dto // 결과 반환
    }

    suspend fun getRealAccessToken(requestDto: KISTokenRequestDto): AccessTokenAPIResponseDto {
        val user = userRepository.findById(2).awaitFirst() // Mono<User>를 awaitFirst()로 변환
        val dto = kisOauth2Client.postRealTokenP(requestDto).awaitSingle() // Mono<AccessTokenAPIResponseDto>를 awaitSingle()로 변환

        user.token = dto.accessToken
        userRepository.save(user).awaitSingle() // 사용자 저장 후 완료 대기

        return dto // 결과 반환
    }
}