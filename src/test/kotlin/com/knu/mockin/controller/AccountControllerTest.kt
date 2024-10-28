package com.knu.mockin.controller

import com.knu.mockin.controller.util.*
import com.knu.mockin.dsl.RestDocsUtils.readJsonFile
import com.knu.mockin.dsl.RestDocsUtils.toBody
import com.knu.mockin.dsl.toDto
import com.knu.mockin.model.dto.response.AccessTokenAPIResponseDto
import com.knu.mockin.model.dto.response.ApprovalKeyResponseDto
import com.knu.mockin.model.dto.response.SimpleMessageResponseDto
import com.knu.mockin.model.entity.User
import com.knu.mockin.repository.UserRepository
import com.knu.mockin.security.JwtUtil
import com.knu.mockin.security.SecurityTestConfig
import com.knu.mockin.service.AccountService
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.StringSpec
import io.kotest.extensions.spring.SpringExtension
import io.mockk.coEvery
import io.mockk.mockk
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Import
import org.springframework.restdocs.ManualRestDocumentation
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono

@WebFluxTest(controllers = [AccountController::class])
@Import(SecurityTestConfig::class)
class AccountControllerTest(
    @MockkBean
    val accountService:AccountService = mockk(),
    @MockkBean
    val userRepository: UserRepository = mockk(),
    @MockkBean
    val jwtUtil: JwtUtil = mockk<JwtUtil>(),
    private val context: ApplicationContext,
): StringSpec({
    val restDocumentation = ManualRestDocumentation()

    lateinit var webTestClient: WebTestClient

    beforeTest {
        webTestClient = buildWebTestClient(context, restDocumentation)
        restDocumentation.beforeTest(AccountControllerTest::class.java, it.name.testName)

        val user = readJsonFile("setting", "user.json") toDto User::class.java
        coEvery { userRepository.findByEmail(user.email) } returns Mono.just(user)
        coEvery { jwtUtil.getUsername(any()) } returns user.email
        coEvery { jwtUtil.isValid(any(), any()) } returns true
    }

    afterTest {
        restDocumentation.afterTest()
    }

    val baseUri = "account"


    "PATCH /account/user" {
        val uri = "${baseUri}/user"
        val uriPatch = "${uri}Patch"
        val requestDto = readJsonFile(uriPatch, "requestDto.json")
        val expectedDto = readJsonFile(uriPatch, "responseDto.json") toDto SimpleMessageResponseDto::class.java
        coEvery { accountService.patchUser(any(), any()) } returns expectedDto

        val response = webTestClient.patchWithBody(uri, requestDto, expectedDto)

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
        coEvery { accountService.postMockKeyPair(any(), any()) } returns expectedDto

        val response = webTestClient.postWithBody(uri, requestDto, expectedDto)

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
        coEvery { accountService.postRealKeyPair(any(), any()) } returns expectedDto

        val response = webTestClient.postWithBody(uri, requestDto, expectedDto)

        response.makeDocument(
            uri,
            requestBody(readJsonFile(uri, "requestDtoDescription.json").toBody()),
            responseBody(readJsonFile(uri, "responseDtoDescription.json").toBody())
        )
    }
}){
    override fun extensions() = listOf(SpringExtension)
}