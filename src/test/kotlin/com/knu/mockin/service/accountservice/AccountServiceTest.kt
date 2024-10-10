package com.knu.mockin.service.accountservice

import com.knu.mockin.kisclient.KISOauth2Client
import com.knu.mockin.model.dto.kisrequest.oauth.KISApprovalRequestDto
import com.knu.mockin.model.dto.kisrequest.oauth.KISTokenRequestDto
import com.knu.mockin.model.dto.request.account.AccountRequestDto
import com.knu.mockin.model.dto.response.AccessTokenAPIResponseDto
import com.knu.mockin.model.dto.response.ApprovalKeyResponseDto
import com.knu.mockin.model.entity.MockKey
import com.knu.mockin.model.entity.User
import com.knu.mockin.repository.MockKeyRepository
import com.knu.mockin.service.AccountService
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import reactor.core.publisher.Mono

class AccountServiceTest: BehaviorSpec({
    val kisOauth2Client: KISOauth2Client = mockk<KISOauth2Client>()
    val mockKeyRepository: MockKeyRepository = mockk()
    val accountService = AccountService(kisOauth2Client, mockKeyRepository)

    Given("get approval key test"){
        val mockKey = MockKey("test", "test appKey", "test appSecret")
        val accountRequestDto = AccountRequestDto("test")
        val requestDto = KISApprovalRequestDto(
            grantType = "client_credentials",
            appKey = mockKey.appKey,
            secretKey = mockKey.appSecret)
        val expectedDto = ApprovalKeyResponseDto("test")

        every { kisOauth2Client.postApproval(requestDto) } returns Mono.just(expectedDto)
        every { mockKeyRepository.findById("test")} returns Mono.just(mockKey)

        When("서비스 계층에 요청을 보내면:"){
            val result = accountService.getApprovalKey(accountRequestDto)

            Then("키가 반환된다"){
                result shouldBe expectedDto
            }
        }
    }

    Given("get access token test"){
        val mockKey = MockKey("test", "test appKey", "test appSecret")
        val accountRequestDto = AccountRequestDto("test")
        val requestDto = KISTokenRequestDto(
            grantType = "client_credentials",
            appKey = mockKey.appKey,
            appSecret = mockKey.appSecret)
        val expectedDto = AccessTokenAPIResponseDto(
            "test",
            "Bearer",
            7776000,
            "2024-10-31")

        every { kisOauth2Client.postTokenP(requestDto) } returns Mono.just(expectedDto)
        every { mockKeyRepository.findById("test")} returns Mono.just(mockKey)

        When("서비스 계층에 요청을 보내면:"){
            val result = accountService.getAccessToken(accountRequestDto)

            Then("토큰이 반환된다"){
                result shouldBe expectedDto
            }
        }
    }
})