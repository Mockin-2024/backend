package com.knu.mockin.service

import com.knu.mockin.exeption.CustomException
import com.knu.mockin.exeption.ErrorCode
import com.knu.mockin.kisclient.KISOauth2Client
import com.knu.mockin.kisclient.KISOauth2RealClient
import com.knu.mockin.model.dto.kisrequest.oauth.KISApprovalRequestDto
import com.knu.mockin.model.dto.kisrequest.oauth.KISTokenRequestDto
import com.knu.mockin.model.dto.request.account.AccountRequestDto
import com.knu.mockin.model.dto.request.account.KeyPairRequestDto
import com.knu.mockin.model.dto.request.account.UserAccountNumberRequestDto
import com.knu.mockin.model.dto.request.account.UserRequestDto
import com.knu.mockin.model.dto.response.AccessTokenAPIResponseDto
import com.knu.mockin.model.dto.response.ApprovalKeyResponseDto
import com.knu.mockin.model.dto.response.SimpleMessageResponseDto
import com.knu.mockin.model.entity.MockKey
import com.knu.mockin.model.entity.RealKey
import com.knu.mockin.model.entity.User
import com.knu.mockin.repository.MockKeyRepository
import com.knu.mockin.repository.RealKeyRepository
import com.knu.mockin.repository.UserRepository
import com.knu.mockin.util.ExtensionUtil.orThrow
import com.knu.mockin.util.RedisUtil
import com.knu.mockin.util.StringUtil
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service

@Service
class AccountService(
    private val kisOauth2Client: KISOauth2Client,
    private val kisOauth2RealClient: KISOauth2RealClient,
    private val userRepository: UserRepository,
    private val mockKeyRepository: MockKeyRepository,
    private val realKeyRepository: RealKeyRepository
) {
    suspend fun patchUser(
        userAccountNumberRequestDto: UserAccountNumberRequestDto
    ): SimpleMessageResponseDto {
        userRepository.findByEmail(userAccountNumberRequestDto.email)
            .orThrow(ErrorCode.USER_NOT_FOUND)
            .awaitFirst()

        userRepository.updateByEmail(userAccountNumberRequestDto.email, userAccountNumberRequestDto.accountNumber)
            .awaitSingleOrNull() ?: throw CustomException(ErrorCode.INTERNAL_SERVER_ERROR)

        return SimpleMessageResponseDto("Register Complete")
    }

    suspend fun postMockKeyPair(
        keyPairRequestDto: KeyPairRequestDto
    ): SimpleMessageResponseDto {
        val user = userRepository.findByEmail(keyPairRequestDto.email)
            .orThrow(ErrorCode.USER_NOT_FOUND)
            .awaitFirst()

        val mockKey = MockKey(
            email = user.email,
            appKey = keyPairRequestDto.appKey,
            appSecret = keyPairRequestDto.appSecret,
        )
        mockKeyRepository.save(mockKey).awaitSingleOrNull()
        return SimpleMessageResponseDto("Register Complete")
    }

    suspend fun postRealKeyPair(
        keyPairRequestDto: KeyPairRequestDto
    ): SimpleMessageResponseDto {
        val user = userRepository.findByEmail(keyPairRequestDto.email)
            .orThrow(ErrorCode.USER_NOT_FOUND)
            .awaitFirst()

        val realKey = RealKey(
            email = user.email,
            appKey = keyPairRequestDto.appKey,
            appSecret = keyPairRequestDto.appSecret,
        )
        realKeyRepository.save(realKey).awaitSingleOrNull()
        return SimpleMessageResponseDto("Register Complete")
    }

    suspend fun getMockApprovalKey(
        accountRequestDto: AccountRequestDto
    ): ApprovalKeyResponseDto {
        val user = mockKeyRepository.findById(accountRequestDto.email)
            .orThrow(ErrorCode.USER_NOT_FOUND)
            .awaitFirst()

        val requestDto = KISApprovalRequestDto(
            grantType = "client_credentials",
            appKey = user.appKey,
            secretKey = user.appSecret)
        return kisOauth2Client.postApproval(requestDto).awaitSingle()
    }

    suspend fun getRealApprovalKey(
            accountRequestDto: AccountRequestDto
    ): ApprovalKeyResponseDto {
        val user = realKeyRepository.findById(accountRequestDto.email)
            .orThrow(ErrorCode.USER_NOT_FOUND)
            .awaitFirst()

        val requestDto = KISApprovalRequestDto(
                grantType = "client_credentials",
                appKey = user.appKey,
                secretKey = user.appSecret)
        return kisOauth2RealClient.postApproval(requestDto).awaitSingle()
    }

    suspend fun getMockAccessToken(
        accountRequestDto: AccountRequestDto
    ): AccessTokenAPIResponseDto {
        val user = mockKeyRepository.findById(accountRequestDto.email)
            .orThrow(ErrorCode.USER_NOT_FOUND)
            .awaitFirst()

        val requestDto = KISTokenRequestDto(
            grantType = "client_credentials",
            appKey = user.appKey,
            appSecret = user.appSecret)
        val dto = kisOauth2Client.postTokenP(requestDto).awaitSingle()

        RedisUtil.saveToken(user.email, dto.accessToken)

        return dto
    }

    suspend fun getRealAccessToken(
            accountRequestDto: AccountRequestDto
    ): AccessTokenAPIResponseDto {
        val user = realKeyRepository.findById(accountRequestDto.email)
            .orThrow(ErrorCode.USER_NOT_FOUND)
            .awaitFirst()

        val requestDto = KISTokenRequestDto(
                grantType = "client_credentials",
                appKey = user.appKey,
                appSecret = user.appSecret)
        val dto = kisOauth2RealClient.postTokenP(requestDto).awaitSingle()

        RedisUtil.saveToken(StringUtil.appendRealSuffix(user.email), dto.accessToken)

        return dto
    }

}