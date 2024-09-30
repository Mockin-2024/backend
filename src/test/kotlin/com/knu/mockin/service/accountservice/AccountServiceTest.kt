package com.knu.mockin.service.accountservice

import com.knu.mockin.kisclient.KISOauth2Client
import com.knu.mockin.model.dto.request.KISApprovalRequestDto
import com.knu.mockin.model.dto.request.KISTokenRequestDto
import com.knu.mockin.model.dto.response.AccessTokenAPIResponseDto
import com.knu.mockin.model.dto.response.ApprovalKeyResponseDto
import com.knu.mockin.service.AccountService
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import reactor.core.publisher.Mono

class AccountServiceTest: BehaviorSpec({
    val kisOauth2Client: KISOauth2Client = mockk<KISOauth2Client>()
    val accountService = AccountService(kisOauth2Client)

    Given("get approval key test"){
        val requestDto = KISApprovalRequestDto(
            grantType = "client_credentials",
            appKey = "test appKey",
            secretKey = "test appSecret")
        val expectedDto = ApprovalKeyResponseDto("test")
        every { kisOauth2Client.postApproval(requestDto) } returns Mono.just(expectedDto)

        When("서비스 계층에 요청을 보내면:"){
            val result = accountService.getApprovalKey(requestDto).block()

            Then("키가 반환된다"){
                result shouldBe expectedDto
            }
        }
    }

    Given("get access token test"){
        val requestDto = KISTokenRequestDto(
            grantType = "client_credentials",
            appKey = "test appKey",
            appSecret = "test appSecret")
        val expectedDto = AccessTokenAPIResponseDto(
            "test",
            "Bearer",
            7776000,
            "2024-10-31")
        every { kisOauth2Client.postTokenP(requestDto) } returns Mono.just(expectedDto)

        When("서비스 계층에 요청을 보내면:"){
            val result = accountService.getAccessToken(requestDto).block()

            Then("토큰이 반환된다"){
                result shouldBe expectedDto
            }
        }
    }
})