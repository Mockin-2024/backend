package com.knu.mockin.service

import com.knu.mockin.kisclient.KISOauth2Client
import com.knu.mockin.kisclient.KISOauth2RealClient
import com.knu.mockin.model.dto.kisrequest.oauth.KISTokenRequestDto
import com.knu.mockin.model.dto.request.account.KeyPairRequestDto
import com.knu.mockin.model.dto.request.account.UserAccountNumberRequestDto
import com.knu.mockin.model.dto.response.SimpleMessageResponseDto
import com.knu.mockin.model.entity.MockKey
import com.knu.mockin.model.entity.RealKey
import com.knu.mockin.model.enum.Constant.CLIENT_CREDENTIAL
import com.knu.mockin.repository.MockKeyRepository
import com.knu.mockin.repository.RealKeyRepository
import com.knu.mockin.repository.UserRepository
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service

@Service
class AccountService(
    private val userRepository: UserRepository,
    private val mockKeyRepository: MockKeyRepository,
    private val realKeyRepository: RealKeyRepository,
    private val kisOauth2Client: KISOauth2Client,
    private val kisOauth2RealClient: KISOauth2RealClient
) {
    suspend fun patchUser(
        userAccountNumberRequestDto: UserAccountNumberRequestDto,
        email: String
    ): SimpleMessageResponseDto {
        userRepository.updateByEmail(email, userAccountNumberRequestDto.accountNumber)
            .awaitSingleOrNull()

        return SimpleMessageResponseDto("Register Complete")
    }

    suspend fun postMockKeyPair(
        keyPairRequestDto: KeyPairRequestDto,
        email: String
    ): SimpleMessageResponseDto {
        validateMockKey(keyPairRequestDto)

        val mockKey = MockKey(
            email = email,
            appKey = keyPairRequestDto.appKey,
            appSecret = keyPairRequestDto.appSecret,
        )
        mockKeyRepository.save(mockKey).awaitSingleOrNull()

        return SimpleMessageResponseDto("Register Complete")
    }

    suspend fun postRealKeyPair(
        keyPairRequestDto: KeyPairRequestDto,
        email: String
    ): SimpleMessageResponseDto {
        validateRealKey(keyPairRequestDto)

        val realKey = RealKey(
            email = email,
            appKey = keyPairRequestDto.appKey,
            appSecret = keyPairRequestDto.appSecret,
        )
        realKeyRepository.save(realKey).awaitSingleOrNull()

        return SimpleMessageResponseDto("Register Complete")
    }

    private suspend fun validateMockKey(keyPairRequestDto: KeyPairRequestDto) {
        val requestDto = KISTokenRequestDto(
            grantType = CLIENT_CREDENTIAL,
            appKey = keyPairRequestDto.appKey,
            appSecret = keyPairRequestDto.appSecret
        )

        kisOauth2Client.postTokenP(requestDto).awaitSingle()
    }

    private suspend fun validateRealKey(keyPairRequestDto: KeyPairRequestDto) {
        val requestDto = KISTokenRequestDto(
            grantType = CLIENT_CREDENTIAL,
            appKey = keyPairRequestDto.appKey,
            appSecret = keyPairRequestDto.appSecret
        )

        kisOauth2RealClient.postTokenP(requestDto).awaitSingle()
    }
}