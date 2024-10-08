package com.knu.mockin.service.accountservice

import com.knu.mockin.kisclient.KISOauth2Client
import com.knu.mockin.model.dto.kisrequest.oauth.KISApprovalRequestDto
import com.knu.mockin.model.dto.kisrequest.oauth.KISTokenRequestDto
import com.knu.mockin.model.dto.response.AccessTokenAPIResponseDto
import com.knu.mockin.model.dto.response.ApprovalKeyResponseDto
import com.knu.mockin.model.entity.User
import com.knu.mockin.repository.UserRepository
import com.knu.mockin.service.AccountService
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import reactor.core.publisher.Mono

class AccountServiceTest: BehaviorSpec({
    val kisOauth2Client: KISOauth2Client = mockk<KISOauth2Client>()
    val userRepository: UserRepository = mockk()
    val accountService = AccountService(kisOauth2Client, userRepository)

    Given("get approval key test"){
        val user = User(1, "test appKey", "test appSecret", "100", "")
        val requestDto = KISApprovalRequestDto(
            grantType = "client_credentials",
            appKey = user.appKey,
            secretKey = user.appSecret)
        val expectedDto = ApprovalKeyResponseDto("test")
        every { kisOauth2Client.postApproval(requestDto) } returns Mono.just(expectedDto)
        every { userRepository.findById(1)} returns Mono.just(user)

        When("서비스 계층에 요청을 보내면:"){
            val result = accountService.getApprovalKey()

            Then("키가 반환된다"){
                result shouldBe expectedDto
            }
        }
    }

    Given("get access token test"){
        val user = User(1, "test appKey", "test appSecret", "100", "")
        val requestDto = KISTokenRequestDto(
            grantType = "client_credentials",
            appKey = user.appKey,
            appSecret = user.appSecret)
        val expectedDto = AccessTokenAPIResponseDto(
            "test",
            "Bearer",
            7776000,
            "2024-10-31")

        every { kisOauth2Client.postTokenP(requestDto) } returns Mono.just(expectedDto)
        every { userRepository.findById(1)} returns Mono.just(user)
        every { userRepository.save(user)} returns Mono.just(user)

        When("서비스 계층에 요청을 보내면:"){
            val result = accountService.getAccessToken()

            Then("토큰이 반환된다"){
                result shouldBe expectedDto
            }
        }
    }
})