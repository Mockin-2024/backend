package com.knu.mockin.service

import com.knu.mockin.exception.ErrorCode
import com.knu.mockin.kisclient.KISOauth2Client
import com.knu.mockin.kisclient.KISOauth2RealClient
import com.knu.mockin.model.dto.kisrequest.oauth.KISApprovalRequestDto
import com.knu.mockin.model.dto.kisrequest.oauth.KISTokenRequestDto
import com.knu.mockin.model.dto.response.AccessTokenAPIResponseDto
import com.knu.mockin.model.dto.response.ApprovalKeyResponseDto
import com.knu.mockin.model.enum.Constant
import com.knu.mockin.model.enum.Constant.CLIENT_CREDENTIAL
import com.knu.mockin.repository.UserRepository
import com.knu.mockin.util.ExtensionUtil.orThrow
import com.knu.mockin.util.RedisUtil
import com.knu.mockin.util.tag
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service

@Service
class Oauth2Service(
    private val kisOauth2Client: KISOauth2Client,
    private val kisOauth2RealClient: KISOauth2RealClient,
    private val userRepository: UserRepository,
) {
    suspend fun getMockApprovalKey(
        email: String
    ): ApprovalKeyResponseDto {
        val user = userRepository.findByEmailWithMockKey(email)
            .orThrow(ErrorCode.USER_NOT_FOUND)
            .awaitFirst()

        val requestDto = KISApprovalRequestDto(
            grantType = CLIENT_CREDENTIAL,
            appKey = user.appKey,
            secretKey = user.appSecret)
        return kisOauth2Client.postApproval(requestDto).awaitSingle()
    }

    suspend fun getRealApprovalKey(
        email: String
    ): ApprovalKeyResponseDto {
        val user = userRepository.findByEmailWithRealKey(email)
            .orThrow(ErrorCode.USER_NOT_FOUND)
            .awaitFirst()

        val requestDto = KISApprovalRequestDto(
            grantType = CLIENT_CREDENTIAL,
            appKey = user.appKey,
            secretKey = user.appSecret)
        return kisOauth2RealClient.postApproval(requestDto).awaitSingle()
    }

    suspend fun getMockAccessToken(
        email: String
    ): AccessTokenAPIResponseDto {
        val user = userRepository.findByEmailWithMockKey(email)
            .orThrow(ErrorCode.USER_NOT_FOUND)
            .awaitFirst()

        val requestDto = KISTokenRequestDto(
            grantType = CLIENT_CREDENTIAL,
            appKey = user.appKey,
            appSecret = user.appSecret)
        val dto = kisOauth2Client.postTokenP(requestDto).awaitSingle()

        RedisUtil.saveToken(user.email tag Constant.MOCK, dto.accessToken)

        return dto
    }

    suspend fun getRealAccessToken(
        email: String
    ): AccessTokenAPIResponseDto {
        val user = userRepository.findByEmailWithRealKey(email)
            .orThrow(ErrorCode.USER_NOT_FOUND)
            .awaitFirst()

        val requestDto = KISTokenRequestDto(
            grantType = CLIENT_CREDENTIAL,
            appKey = user.appKey,
            appSecret = user.appSecret)
        val dto = kisOauth2RealClient.postTokenP(requestDto).awaitSingle()

        RedisUtil.saveToken(user.email tag Constant.REAL, dto.accessToken)

        return dto
    }
}