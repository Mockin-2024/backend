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
    suspend fun getApprovalKey(): ApprovalKeyResponseDto {
        val user = userRepository.findById(1).awaitFirst()
        val requestDto = KISApprovalRequestDto(
            grantType = "client_credentials",
            appKey = user.appKey,
            secretKey = user.appSecret)
        return kisOauth2Client.postApproval(requestDto).awaitSingle()
    }

    suspend fun getAccessToken(): AccessTokenAPIResponseDto {
        val user = userRepository.findById(1).awaitFirst()
        val requestDto = KISTokenRequestDto(
            grantType = "client_credentials",
            appKey = user.appKey,
            appSecret = user.appSecret)
        val dto = kisOauth2Client.postTokenP(requestDto).awaitSingle()

        user.token = dto.accessToken
        userRepository.save(user).awaitSingle()

        return dto
    }
}