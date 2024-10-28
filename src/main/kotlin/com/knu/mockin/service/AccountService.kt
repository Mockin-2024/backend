package com.knu.mockin.service

import com.knu.mockin.exeption.ErrorCode
import com.knu.mockin.model.dto.request.account.KeyPairRequestDto
import com.knu.mockin.model.dto.request.account.UserAccountNumberRequestDto
import com.knu.mockin.model.dto.response.SimpleMessageResponseDto
import com.knu.mockin.model.entity.MockKey
import com.knu.mockin.model.entity.RealKey
import com.knu.mockin.repository.MockKeyRepository
import com.knu.mockin.repository.RealKeyRepository
import com.knu.mockin.repository.UserRepository
import com.knu.mockin.util.ExtensionUtil.orThrow
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service

@Service
class AccountService(
    private val userRepository: UserRepository,
    private val mockKeyRepository: MockKeyRepository,
    private val realKeyRepository: RealKeyRepository
) {
    suspend fun patchUser(
        userAccountNumberRequestDto: UserAccountNumberRequestDto,
        email: String
    ): SimpleMessageResponseDto {
        userRepository.findByEmail(email)
            .orThrow(ErrorCode.USER_NOT_FOUND)
            .awaitFirst()

        userRepository.updateByEmail(email, userAccountNumberRequestDto.accountNumber)
            .awaitSingleOrNull()

        return SimpleMessageResponseDto("Register Complete")
    }

    suspend fun postMockKeyPair(
        keyPairRequestDto: KeyPairRequestDto,
        email: String
    ): SimpleMessageResponseDto {
        val user = userRepository.findByEmail(email)
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
        keyPairRequestDto: KeyPairRequestDto,
        email: String
    ): SimpleMessageResponseDto {
        val user = userRepository.findByEmail(email)
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


}