package com.knu.mockin.controller

import com.knu.mockin.controller.util.*
import com.knu.mockin.dsl.*
import com.knu.mockin.dsl.RestDocsUtils.readJsonFile
import com.knu.mockin.dsl.RestDocsUtils.toBody
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


    "PATCH /account/user" {
        val uri = "${baseUri}/user"
        val uriPatch = "${uri}Patch"
        val requestDto = readJsonFile(uriPatch, "requestDto.json")
        val expectedDto = readJsonFile(uriPatch, "responseDto.json") toDto SimpleMessageResponseDto::class.java
        coEvery { accountService.patchUser(any()) } returns expectedDto

        val response = mockMvc.patchWithBody(uri, requestDto, expectedDto)

        response.makeDocument(
            uriPatch,
            requestBody(readJsonFile(uriPatch, "requestDtoDescription.json").toBody()),
            responseBody(readJsonFile(uriPatch, "responseDtoDescription.json").toBody())
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
            requestBody(readJsonFile(uri, "requestDtoDescription.json").toBody()),
            responseBody(readJsonFile(uri, "responseDtoDescription.json").toBody())
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
            requestBody(readJsonFile(uri, "requestDtoDescription.json").toBody()),
            responseBody(readJsonFile(uri, "responseDtoDescription.json").toBody())
        )
    }

    "POST /account/mock-approval-key" {
        val uri = "${baseUri}/mock-approval-key"
        val requestDto = readJsonFile(uri, "requestDto.json")
        val expectedDto = readJsonFile(uri, "responseDto.json") toDto ApprovalKeyResponseDto::class.java
        coEvery { accountService.getMockApprovalKey(any()) } returns expectedDto

        val response = mockMvc.postWithBody(uri, requestDto, expectedDto)

        response.makeDocument(
            uri,
            requestBody(readJsonFile(uri, "requestDtoDescription.json").toBody()),
            responseBody(readJsonFile(uri, "responseDtoDescription.json").toBody())
        )
    }

    "POST /account/real-approval-key" {
        val uri = "${baseUri}/real-approval-key"
        val requestDto = readJsonFile(uri, "requestDto.json")
        val expectedDto = readJsonFile(uri, "responseDto.json") toDto ApprovalKeyResponseDto::class.java
        coEvery { accountService.getRealApprovalKey(any()) } returns expectedDto

        val response = mockMvc.postWithBody(uri, requestDto, expectedDto)

        response.makeDocument(
            uri,
            requestBody(readJsonFile(uri, "requestDtoDescription.json").toBody()),
            responseBody(readJsonFile(uri, "responseDtoDescription.json").toBody())
        )
    }

    "POST /account/mock-token" {
        val uri = "${baseUri}/mock-token"
        val requestDto = readJsonFile(uri, "requestDto.json")
        val expectedDto = readJsonFile(uri, "responseDto.json") toDto AccessTokenAPIResponseDto::class.java
        coEvery { accountService.getMockAccessToken(any()) } returns expectedDto

        val response = mockMvc.postWithBody(uri, requestDto, expectedDto)

        response.makeDocument(
            uri,
            requestBody(readJsonFile(uri, "requestDtoDescription.json").toBody()),
            responseBody(readJsonFile(uri, "responseDtoDescription.json").toBody())
        )
    }

    "POST /account/real-token" {
        val uri = "${baseUri}/real-token"
        val requestDto = readJsonFile(uri, "requestDto.json")
        val expectedDto = readJsonFile(uri, "responseDto.json") toDto AccessTokenAPIResponseDto::class.java
        coEvery { accountService.getRealAccessToken(any()) } returns expectedDto

        val response = mockMvc.postWithBody(uri, requestDto, expectedDto)

        response.makeDocument(
            uri,
            requestBody(readJsonFile(uri, "requestDtoDescription.json").toBody()),
            responseBody(readJsonFile(uri, "responseDtoDescription.json").toBody())
        )
    }



}){
    override fun extensions() = listOf(SpringExtension)
}