package com.knu.mockin.controller

import com.knu.mockin.controller.util.*
import com.knu.mockin.dsl.RestDocsUtils.readJsonFile
import com.knu.mockin.dsl.RestDocsUtils.toBody
import com.knu.mockin.dsl.RestDocsUtils.toPairs
import com.knu.mockin.dsl.toDto
import com.knu.mockin.model.dto.kisresponse.trading.*
import com.knu.mockin.model.dto.request.trading.*
import com.knu.mockin.model.entity.User
import com.knu.mockin.repository.UserRepository
import com.knu.mockin.security.JwtUtil
import com.knu.mockin.security.SecurityTestConfig
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

@WebFluxTest(controllers = [TradingController::class])
@Import(SecurityTestConfig::class)
class TradingControllerTest(
    @MockkBean
    val tradingService: TradingService = mockk<TradingService>(),
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
        restDocumentation.beforeTest(TradingControllerTest::class.java, it.name.testName)

        val user = readJsonFile("setting", "user.json") toDto User::class.java
        coEvery { userRepository.findByEmail(user.email) } returns Mono.just(user)
        coEvery { jwtUtil.getUsername(any()) } returns user.email
        coEvery { jwtUtil.isValid(any(), any()) } returns true
    }

    afterTest {
        restDocumentation.afterTest()
    }

    val baseUri = "trading"

    "POST /trading/order" {
        val uri = "${baseUri}/order"
        val requestDto = readJsonFile(uri, "requestDto.json")
        val expectedDto = readJsonFile(uri, "responseDto.json") toDto KISOrderResponseDto::class.java

        coEvery { tradingService.postOrder(any(), any()) } returns expectedDto

        val response = webTestClient.postWithBody(uri, requestDto, expectedDto)
        response.makeDocument(
            uri,
            requestBody(readJsonFile(uri, "requestDtoDescription.json").toBody()),
            responseBody(readJsonFile(uri, "responseDtoDescription.json").toBody())
        )
    }

    "POST /trading/order-reverse"{
        val uri = "${baseUri}/order-reverse"
        val requestDto = readJsonFile(uri,"requestDto.json")
        val expectedDto = readJsonFile(uri, "responseDto.json") toDto KISOrderReverseResponseDto::class.java

        coEvery { tradingService.postOrderReverse(any(), any()) } returns expectedDto

        val response = webTestClient.postWithBody(uri, requestDto, expectedDto)

        response.makeDocument(
            uri,
            requestBody(readJsonFile(uri, "requestDtoDescription.json").toBody()),
            responseBody(readJsonFile(uri, "responseDtoDescription.json").toBody())
        )
    }

    "GET /trading/nccs" {
        val uri = "${baseUri}/nccs"
        val requestParams = readJsonFile(uri, "requestDto.json") toDto NCCSRequestParameterDto::class.java
        val expectedDto = readJsonFile(uri, "responseDto.json") toDto KISNCCSResponseDto::class.java

        coEvery { tradingService.getNCCS(any(), any()) } returns expectedDto

        val response = webTestClient.getWithParams(uri, requestParams, expectedDto)

        response.makeDocument(
            uri,
            parameters(readJsonFile(uri, "requestDtoDescription.json").toPairs()),
            responseBody(readJsonFile(uri, "responseDtoDescription.json").toBody())
        )
    }

    "GET /trading/balance" {
        val uri = "${baseUri}/balance"
        val requestParams = readJsonFile(uri, "requestDto.json") toDto BalanceRequestParameterDto::class.java
        val expectedDto = readJsonFile(uri, "responseDto.json") toDto KISBalanceResponseDto::class.java

        coEvery { tradingService.getBalance(any(), any()) } returns expectedDto

        val response = webTestClient.getWithParams(uri, requestParams, expectedDto)

        response.makeDocument(
            uri,
            parameters(readJsonFile(uri, "requestDtoDescription.json").toPairs()),
            responseBody(readJsonFile(uri, "responseDtoDescription.json").toBody())
        )
    }

    "GET /trading/psamount" {
        val uri = "${baseUri}/psamount"
        val requestParams = readJsonFile(uri, "requestDto.json") toDto PsAmountRequestParameterDto::class.java
        val expectedDto = readJsonFile(uri, "responseDto.json") toDto KISPsAmountResponseDto::class.java

        coEvery { tradingService.getPsAmount(any(), any()) } returns expectedDto

        val response = webTestClient.getWithParams(uri, requestParams, expectedDto)

        response.makeDocument(
            uri,
            parameters(readJsonFile(uri, "requestDtoDescription.json").toPairs()),
            responseBody(readJsonFile(uri, "responseDtoDescription.json").toBody())
        )
    }

    "GET /trading/present-balance" {
        val uri = "${baseUri}/present-balance"
        val requestParams = readJsonFile(uri, "requestDto.json") toDto PresentBalanceRequestParameterDto::class.java
        val expectedDto = readJsonFile(uri, "responseDto.json") toDto KISPresentBalanceResponseDto::class.java

        coEvery { tradingService.getPresentBalance(any(), any()) } returns expectedDto

        val response = webTestClient.getWithParams(uri, requestParams, expectedDto)

        response.makeDocument(
            uri,
            parameters(readJsonFile(uri, "requestDtoDescription.json").toPairs()),
            responseBody(readJsonFile(uri, "responseDtoDescription.json").toBody())
        )
    }

    "GET /trading/ccnl" {
        val uri = "${baseUri}/ccnl"
        val requestParams = readJsonFile(uri, "requestDto.json") toDto CCNLRequestParameterDto::class.java
        val expectedDto = readJsonFile(uri, "responseDto.json") toDto KISCCNLResponseDto::class.java

        coEvery { tradingService.getCCNL(any(), any()) } returns expectedDto

        val response = webTestClient.getWithParams(uri, requestParams, expectedDto)

        response.makeDocument(
            uri,
            parameters(readJsonFile(uri, "requestDtoDescription.json").toPairs()),
            responseBody(readJsonFile(uri, "responseDtoDescription.json").toBody())
        )
    }
})
