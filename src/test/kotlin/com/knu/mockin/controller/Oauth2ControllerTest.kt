package com.knu.mockin.controller

import com.knu.mockin.controller.util.*
import com.knu.mockin.dsl.RestDocsUtils
import com.knu.mockin.dsl.RestDocsUtils.toBody
import com.knu.mockin.dsl.toDto
import com.knu.mockin.model.dto.response.AccessTokenAPIResponseDto
import com.knu.mockin.model.dto.response.ApprovalKeyResponseDto
import com.knu.mockin.model.entity.User
import com.knu.mockin.repository.UserRepository
import com.knu.mockin.security.JwtUtil
import com.knu.mockin.security.SecurityTestConfig
import com.knu.mockin.service.Oauth2Service
import com.knu.mockin.service.TradingService
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.StringSpec
import io.mockk.coEvery
import io.mockk.mockk
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Import
import org.springframework.restdocs.ManualRestDocumentation
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono

@WebFluxTest(controllers = [Oauth2Controller::class])
@Import(SecurityTestConfig::class)
class Oauth2ControllerTest(
    @MockkBean
    val oauth2Service: Oauth2Service = mockk<Oauth2Service>(),
    @MockkBean
    val userRepository: UserRepository = mockk(),
    @MockkBean
    val jwtUtil: JwtUtil = mockk<JwtUtil>(),
    private val context: ApplicationContext,
) :StringSpec({
    val restDocumentation = ManualRestDocumentation()
    lateinit var webTestClient: WebTestClient

    beforeTest {
        webTestClient = buildWebTestClient(context, restDocumentation)
        restDocumentation.beforeTest(Oauth2ControllerTest::class.java, it.name.testName)

        val user = RestDocsUtils.readJsonFile("setting", "user.json") toDto User::class.java
        coEvery { userRepository.findByEmail(user.email) } returns Mono.just(user)
        coEvery { jwtUtil.getUsername(any()) } returns user.email
        coEvery { jwtUtil.isValid(any(), any()) } returns true
    }

    afterTest {
        restDocumentation.afterTest()
    }

    val baseUri = "oauth2"
    "POST /oauth2/mock-approval-key" {
        val uri = "${baseUri}/mock-approval-key"
        val requestDto = RestDocsUtils.readJsonFile(uri, "requestDto.json")
        val expectedDto = RestDocsUtils.readJsonFile(uri, "responseDto.json") toDto ApprovalKeyResponseDto::class.java
        coEvery { oauth2Service.getMockApprovalKey(any()) } returns expectedDto

        val response = webTestClient.postWithBody(uri, requestDto, expectedDto)

        response.makeDocument(
            uri,
            requestBody(RestDocsUtils.readJsonFile(uri, "requestDtoDescription.json").toBody()),
            responseBody(RestDocsUtils.readJsonFile(uri, "responseDtoDescription.json").toBody())
        )
    }

    "POST /oauth2/real-approval-key" {
        val uri = "${baseUri}/real-approval-key"
        val requestDto = RestDocsUtils.readJsonFile(uri, "requestDto.json")
        val expectedDto = RestDocsUtils.readJsonFile(uri, "responseDto.json") toDto ApprovalKeyResponseDto::class.java
        coEvery { oauth2Service.getRealApprovalKey(any()) } returns expectedDto

        val response = webTestClient.postWithBody(uri, requestDto, expectedDto)

        response.makeDocument(
            uri,
            requestBody(RestDocsUtils.readJsonFile(uri, "requestDtoDescription.json").toBody()),
            responseBody(RestDocsUtils.readJsonFile(uri, "responseDtoDescription.json").toBody())
        )
    }

    "POST /oauth2/mock-token" {
        val uri = "${baseUri}/mock-token"
        val requestDto = RestDocsUtils.readJsonFile(uri, "requestDto.json")
        val expectedDto = RestDocsUtils.readJsonFile(uri, "responseDto.json") toDto AccessTokenAPIResponseDto::class.java
        coEvery { oauth2Service.getMockAccessToken(any()) } returns expectedDto

        val response = webTestClient.postWithBody(uri, requestDto, expectedDto)

        response.makeDocument(
            uri,
            requestBody(RestDocsUtils.readJsonFile(uri, "requestDtoDescription.json").toBody()),
            responseBody(RestDocsUtils.readJsonFile(uri, "responseDtoDescription.json").toBody())
        )
    }

    "POST /oauth2/real-token" {
        val uri = "${baseUri}/real-token"
        val requestDto = RestDocsUtils.readJsonFile(uri, "requestDto.json")
        val expectedDto = RestDocsUtils.readJsonFile(uri, "responseDto.json") toDto AccessTokenAPIResponseDto::class.java
        coEvery { oauth2Service.getRealAccessToken(any()) } returns expectedDto

        val response = webTestClient.postWithBody(uri, requestDto, expectedDto)

        response.makeDocument(
            uri,
            requestBody(RestDocsUtils.readJsonFile(uri, "requestDtoDescription.json").toBody()),
            responseBody(RestDocsUtils.readJsonFile(uri, "responseDtoDescription.json").toBody())
        )
    }
})