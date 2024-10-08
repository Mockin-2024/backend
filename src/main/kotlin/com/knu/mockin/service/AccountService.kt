package com.knu.mockin.service

import com.knu.mockin.kisclient.KISOauth2Client
import com.knu.mockin.model.dto.kisrequest.oauth.KISApprovalRequestDto
import com.knu.mockin.model.dto.kisrequest.oauth.KISTokenRequestDto
import com.knu.mockin.model.dto.request.account.AccountRequestDto
import com.knu.mockin.model.dto.request.account.KeyPairRequestDto
import com.knu.mockin.model.dto.request.account.UserAccountNumberRequestDto
import com.knu.mockin.model.dto.request.account.UserRequestDto
import com.knu.mockin.model.dto.response.AccessTokenAPIResponseDto
import com.knu.mockin.model.dto.response.ApprovalKeyResponseDto
import com.knu.mockin.model.entity.MockKey
import com.knu.mockin.model.entity.RealKey
import com.knu.mockin.model.entity.User
import com.knu.mockin.repository.MockKeyRepository
import com.knu.mockin.repository.RealKeyRepository
import com.knu.mockin.repository.UserRepository
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service

@Service
class AccountService(
    private val kisOauth2Client: KISOauth2Client,
    private val userRepository: UserRepository,
    private val mockKeyRepository: MockKeyRepository,
    private val realKeyRepository: RealKeyRepository
) {
    suspend fun postUser(
        userRequestDto: UserRequestDto
    ){
        val user = User(
            email = userRequestDto.email,
            name = userRequestDto.name
        )
        userRepository.save(user).awaitSingleOrNull()
    }

    suspend fun patchUser(
        userAccountNumberRequestDto: UserAccountNumberRequestDto
    ){
        userRepository.updateByEmail(userAccountNumberRequestDto.email, userAccountNumberRequestDto.accountNumber).awaitSingleOrNull()
    }

    suspend fun postMockKey(
        keyPairRequestDto: KeyPairRequestDto
    ){
        val user = userRepository.findByEmail(keyPairRequestDto.email).awaitFirst()

        val mockKey = MockKey(
            email = user.email,
            appKey = keyPairRequestDto.appKey,
            appSecret = keyPairRequestDto.appSecret,
        )
        mockKeyRepository.save(mockKey).awaitSingleOrNull()
    }

    suspend fun postRealKey(
        keyPairRequestDto: KeyPairRequestDto
    ){
        val user = userRepository.findByEmail(keyPairRequestDto.email).awaitFirst()
        println(user.email)
        val realKey = RealKey(
            email = user.email,
            appKey = keyPairRequestDto.appKey,
            appSecret = keyPairRequestDto.appSecret,
        )
        realKeyRepository.save(realKey).awaitSingleOrNull()
    }

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