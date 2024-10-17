package com.knu.mockin.controller

import com.knu.mockin.dsl.*
import com.knu.mockin.dsl.RestDocsUtils.readJsonFile
import com.knu.mockin.dsl.RestDocsUtils.toBody
import com.knu.mockin.model.NUMBER
import com.knu.mockin.model.STRING
import com.knu.mockin.model.dto.request.account.AccountRequestDto
import com.knu.mockin.model.dto.request.account.KeyPairRequestDto
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
        val requestDto = readJsonFile(uri, "requestDto.json")
        val expectedDto = readJsonFile(uri, "responseDto.json") toDto SimpleMessageResponseDto::class.java
        coEvery { accountService.postUser(any()) } returns expectedDto

        val response = mockMvc.postWithBody(uri, requestDto, expectedDto)

        response.makeDocument(
            uri,
            requestBodyTemp(readJsonFile(uri, "requestDtoDescription.json").toBody()),
            responseBodyTemp(readJsonFile(uri, "responseDtoDescription.json").toBody())
        )
    }

    "PATCH /account/user" {
        val uri = "${baseUri}/user"
        val uriPatch = "${uri}Patch"
        val requestDto = readJsonFile(uriPatch, "requestDto.json")
        val expectedDto = readJsonFile(uriPatch, "responseDto.json") toDto SimpleMessageResponseDto::class.java
        coEvery { accountService.patchUser(any()) } returns expectedDto

        val response = mockMvc.patchWithBody(uri, requestDto, expectedDto)

        response.makeDocument(
            uriPatch,
            requestBodyTemp(readJsonFile(uriPatch, "requestDtoDescription.json").toBody()),
            responseBodyTemp(readJsonFile(uriPatch, "responseDtoDescription.json").toBody())
        )
    }

    "POST /account/mock-key" {
        val uri = "${baseUri}/mock-key"
        val requestDto = readJsonFile(uri, "requestDto.json")
        val expectedDto = readJsonFile(uri, "responseDto.json") toDto SimpleMessageResponseDto::class.java
        coEvery { accountService.postMockKeyPair(any()) } returns expectedDto

        val response = mockMvc.postWithBody(uri, requestDto, expectedDto)

        response.makeDocument(
            uri,
            requestBodyTemp(readJsonFile(uri, "requestDtoDescription.json").toBody()),
            responseBodyTemp(readJsonFile(uri, "responseDtoDescription.json").toBody())
        )
    }

    "POST /account/real-key" {
        val uri = "${baseUri}/real-key"
        val requestDto = readJsonFile(uri, "requestDto.json")
        val expectedDto = readJsonFile(uri, "responseDto.json") toDto SimpleMessageResponseDto::class.java
        coEvery { accountService.postRealKeyPair(any()) } returns expectedDto

        val response = mockMvc.postWithBody(uri, requestDto, expectedDto)

        response.makeDocument(
            uri,
            requestBodyTemp(readJsonFile(uri, "requestDtoDescription.json").toBody()),
            responseBodyTemp(readJsonFile(uri, "responseDtoDescription.json").toBody())
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