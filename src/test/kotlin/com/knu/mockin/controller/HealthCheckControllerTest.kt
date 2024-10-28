package com.knu.mockin.controller

import com.knu.mockin.controller.util.*
import com.knu.mockin.dsl.RestDocsUtils
import com.knu.mockin.dsl.RestDocsUtils.readJsonFile
import com.knu.mockin.dsl.RestDocsUtils.toBody
import com.knu.mockin.dsl.RestDocsUtils.toPairs
import com.knu.mockin.dsl.toDto
import com.knu.mockin.model.dto.request.account.UserAccountNumberRequestDto
import com.knu.mockin.model.dto.request.trading.NCCSRequestParameterDto
import com.knu.mockin.model.dto.response.SimpleMessageResponseDto
import com.knu.mockin.model.entity.User
import com.knu.mockin.repository.UserRepository
import com.knu.mockin.security.JwtUtil
import com.knu.mockin.security.SecurityTestConfig
import com.knu.mockin.service.HealthCheckService
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

@WebFluxTest(controllers = [HealthCheckController::class])
@Import(SecurityTestConfig::class)
class HealthCheckControllerTest(
    @MockkBean
    private val healthCheckService: HealthCheckService = mockk<HealthCheckService>(),
    @MockkBean
    val userRepository: UserRepository = mockk(),
    @MockkBean
    val jwtUtil: JwtUtil = mockk<JwtUtil>(),
    private val context: ApplicationContext,
): StringSpec({
    val restDocumentation = ManualRestDocumentation()
    lateinit var webTestClient: WebTestClient
    val user = readJsonFile("setting", "user.json") toDto User::class.java
    beforeTest {
        webTestClient = buildWebTestClient(context, restDocumentation)
        restDocumentation.beforeTest(HealthCheckControllerTest::class.java, it.name.testName)
        coEvery { userRepository.findByEmail(user.email) } returns Mono.just(user)
        coEvery { jwtUtil.getUsername(any()) } returns user.email
        coEvery { jwtUtil.isValid(any(), any()) } returns true
    }

    afterTest {
        restDocumentation.afterTest()
    }

    val baseUri = "health"

    "GET /health" {
        val requestParams = readJsonFile(baseUri, "requestDto.json") toDto UserAccountNumberRequestDto::class.java
        val expectedDto = readJsonFile(baseUri, "responseDto.json") toDto SimpleMessageResponseDto::class.java

        coEvery { healthCheckService.healthCheck() } returns expectedDto

        val response = webTestClient.getWithParams(baseUri, requestParams, expectedDto)

        response.makeDocument(
            baseUri,
            parameters(readJsonFile(baseUri, "requestDtoDescription.json").toPairs()),
            responseBody(readJsonFile(baseUri, "responseDtoDescription.json").toBody())
        )
    }
})