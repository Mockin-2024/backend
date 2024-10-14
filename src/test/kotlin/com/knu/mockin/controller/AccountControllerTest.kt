package com.knu.mockin.controller

import com.knu.mockin.dsl.*
import com.knu.mockin.model.NUMBER
import com.knu.mockin.model.STRING
import com.knu.mockin.model.dto.request.account.AccountRequestDto
import com.knu.mockin.model.dto.request.account.KeyPairRequestDto
import com.knu.mockin.model.dto.request.account.UserAccountNumberRequestDto
import com.knu.mockin.model.dto.request.account.UserRequestDto
import com.knu.mockin.model.dto.response.AccessTokenAPIResponseDto
import com.knu.mockin.model.dto.response.ApprovalKeyResponseDto
import com.knu.mockin.model.dto.response.SimpleMessageResponseDto
import com.knu.mockin.service.AccountService
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.StringSpec
import io.kotest.extensions.spring.SpringExtension
import io.mockk.coEvery
import io.mockk.mockk
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.restdocs.ManualRestDocumentation
import org.springframework.test.web.servlet.MockMvc
import org.springframework.web.context.WebApplicationContext

@WebMvcTest(AccountController::class)
class AccountControllerTest(
    @MockkBean
    val accountService:AccountService = mockk(),
    private val webApplicationContext: WebApplicationContext
): StringSpec({
    val restDocumentation = ManualRestDocumentation()
    lateinit var mockMvc: MockMvc

    beforeTest {
        mockMvc = buildMockMvc(webApplicationContext, restDocumentation)
        restDocumentation.beforeTest(TradingControllerTest::class.java, it.name.testName)
    }

    afterTest {
        restDocumentation.afterTest()
    }

    val baseUri = "/account"

    "POST /account/user" {
        val uri = "${baseUri}/user"
        val requestDto = UserRequestDto(
            email = "test@naver.com",
            name = "test"
        )
        val expectedDto = SimpleMessageResponseDto("Register Complete")
        coEvery { accountService.postUser(requestDto) } returns expectedDto

        val response = mockMvc.postWithBody(uri, requestDto, expectedDto)

        response.makeDocument(
            identifier = "/account/user",
            requestBody = requestBody(
                "email" type STRING means "사용자 이메일",
                "name" type STRING means "사용자 이름",
            ),
            responseBody =  responseBody(
                "message" type STRING means "응답 메세지",
            )
        )
    }

    "PATCH /account/user" {
        val uri = "${baseUri}/user"
        val requestDto = UserAccountNumberRequestDto(
                email = "test@naver.com",
                accountNumber = "123456789"
        )
        val expectedDto = SimpleMessageResponseDto("Register Complete")
        coEvery { accountService.patchUser(requestDto) } returns expectedDto

        val response = mockMvc.patchWithBody(uri, requestDto, expectedDto)

        response.makeDocument(
                identifier = "/account/user-patch",
                requestBody = requestBody(
                        "email" type STRING means "사용자 이메일",
                        "accountNumber" type STRING means "사용자 계좌 번호"
                ),
                responseBody = responseBody(
                        "message" type STRING means "응답 메세지"
                )
        )
    }

    "POST /account/mock-key" {
        val uri = "${baseUri}/mock-key"
        val requestDto = KeyPairRequestDto(
                email = "test@naver.com",
                appKey = "mockAppKey",
                appSecret = "mockAppSecret"
        )
        val expectedDto = SimpleMessageResponseDto("Register Complete")
        coEvery { accountService.postMockKey(requestDto) } returns expectedDto

        val response = mockMvc.postWithBody(uri, requestDto, expectedDto)

        response.makeDocument(
                identifier = "/account/mock-key",
                requestBody = requestBody(
                        "email" type STRING means "사용자 이메일",
                        "appKey" type STRING means "모의 앱 키",
                        "appSecret" type STRING means "모의 앱 비밀 키"
                ),
                responseBody = responseBody(
                        "message" type STRING means "응답 메세지"
                )
        )
    }

    "POST /account/real-key" {
        val uri = "${baseUri}/real-key"
        val requestDto = KeyPairRequestDto(
                email = "test@naver.com",
                appKey = "realAppKey",
                appSecret = "realAppSecret"
        )
        val expectedDto = SimpleMessageResponseDto("Register Complete")
        coEvery { accountService.postRealKey(requestDto) } returns expectedDto

        val response = mockMvc.postWithBody(uri, requestDto, expectedDto)

        response.makeDocument(
                identifier = "/account/real-key",
                requestBody = requestBody(
                        "email" type STRING means "사용자 이메일",
                        "appKey" type STRING means "실제 앱 키",
                        "appSecret" type STRING means "실제 앱 비밀 키"
                ),
                responseBody = responseBody(
                        "message" type STRING means "응답 메세지"
                )
        )
    }

    "POST /account/mock-approval-key" {
        val uri = "${baseUri}/mock-approval-key"
        val requestDto = AccountRequestDto(email = "test@naver.com")
        val expectedDto = ApprovalKeyResponseDto(
                approvalKey = "Mock Approval Key"
        )
        coEvery { accountService.getMockApprovalKey(requestDto) } returns expectedDto

        val response = mockMvc.postWithBody(uri, requestDto, expectedDto)

        response.makeDocument(
                identifier = "/account/mock-approval-key",
                requestBody = requestBody(
                        "email" type STRING means "사용자 이메일"
                ),
                responseBody = responseBody(
                        "approval_key" type STRING means "모의 승인 키"
                )
        )
    }

    "POST /account/real-approval-key" {
        val uri = "${baseUri}/real-approval-key"
        val requestDto = AccountRequestDto(email = "test@naver.com")
        val expectedDto = ApprovalKeyResponseDto(
                approvalKey = "Real Approval Key"
        )
        coEvery { accountService.getRealApprovalKey(requestDto) } returns expectedDto

        val response = mockMvc.postWithBody(uri, requestDto, expectedDto)

        response.makeDocument(
                identifier = "/account/real-approval-key",
                requestBody = requestBody(
                        "email" type STRING means "사용자 이메일"
                ),
                responseBody = responseBody(
                        "approval_key" type STRING means "실제 승인 키"
                )
        )
    }

    "POST /account/mock-token" {
        val uri = "${baseUri}/mock-token"
        val requestDto = AccountRequestDto(email = "test@naver.com")
        val expectedDto = AccessTokenAPIResponseDto(
                accessToken = "MockAccessToken",
                accessTokenTokenExpired = "2023-12-22 08:16:59",
                expiresIn = 86400,
                tokenType = "Bearer"
        )
        coEvery { accountService.getMockAccessToken(requestDto) } returns expectedDto

        val response = mockMvc.postWithBody(uri, requestDto, expectedDto)

        response.makeDocument(
                identifier = "/account/mock-token",
                requestBody = requestBody(
                        "email" type STRING means "사용자 이메일"
                ),
                responseBody = responseBody(
                        "access_token" type STRING means "모의 액세스 토큰",
                        "token_type" type STRING means "접근토큰유형",
                        "expire_in" type NUMBER means "유효기간(초)",
                        "access_token_token_expired" type STRING means "유효기간(년:월:일 시:분:초)"
                )
        )
    }

    "POST /account/real-token" {
        val uri = "${baseUri}/real-token"
        val requestDto = AccountRequestDto(email = "test@naver.com")
        val expectedDto = AccessTokenAPIResponseDto(
                accessToken = "RealAccessToken",
                accessTokenTokenExpired = "2023-12-22 08:16:59",
                expiresIn = 86400,
                tokenType = "Bearer"
        )
        coEvery { accountService.getRealAccessToken(requestDto) } returns expectedDto

        val response = mockMvc.postWithBody(uri, requestDto, expectedDto)

        response.makeDocument(
                identifier = "/account/real-token",
                requestBody = requestBody(
                        "email" type STRING means "사용자 이메일"
                ),
                responseBody = responseBody(
                        "access_token" type STRING means "실제 액세스 토큰",
                        "token_type" type STRING means "접근토큰유형",
                        "expire_in" type NUMBER means "유효기간(초)",
                        "access_token_token_expired" type STRING means "유효기간(년:월:일 시:분:초)"
                )
        )
    }



}){
    override fun extensions() = listOf(SpringExtension)
}