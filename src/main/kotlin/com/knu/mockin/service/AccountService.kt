package com.knu.mockin.service

import com.knu.mockin.kisclient.KISOauth2Client
import com.knu.mockin.model.dto.kisrequest.oauth.KISApprovalRequestDto
import com.knu.mockin.model.dto.kisrequest.oauth.KISTokenRequestDto
import com.knu.mockin.model.dto.request.account.AccountRequestDto
import com.knu.mockin.model.dto.response.AccessTokenAPIResponseDto
import com.knu.mockin.model.dto.response.ApprovalKeyResponseDto
import com.knu.mockin.repository.MockKeyRepository
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.stereotype.Service

@Service
class AccountService(
    private val kisOauth2Client: KISOauth2Client,
    private val mockKeyRepository: MockKeyRepository
) {
    suspend fun getApprovalKey(
        accountRequestDto: AccountRequestDto
    ): ApprovalKeyResponseDto {
        val user = mockKeyRepository.findById(accountRequestDto.email).awaitFirst()
        val requestDto = KISApprovalRequestDto(
            grantType = "client_credentials",
            appKey = user.appKey,
            secretKey = user.appSecret)
        return kisOauth2Client.postApproval(requestDto).awaitSingle()
    }

    suspend fun getAccessToken(
        accountRequestDto: AccountRequestDto
    ): AccessTokenAPIResponseDto {
        val user = mockKeyRepository.findById(accountRequestDto.email).awaitFirst()
        val requestDto = KISTokenRequestDto(
            grantType = "client_credentials",
            appKey = user.appKey,
            appSecret = user.appSecret)
        val dto = kisOauth2Client.postTokenP(requestDto).awaitSingle()

        // TODO Redis 추가 후 Redis에 저장하도록 설정
//        user.token = dto.accessToken
//        userRepository.save(user).awaitSingle()

        return dto
    }
}