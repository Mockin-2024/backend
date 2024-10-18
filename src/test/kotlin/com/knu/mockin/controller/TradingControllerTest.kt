package com.knu.mockin.controller

import com.knu.mockin.controller.util.*
import com.knu.mockin.dsl.*
import com.knu.mockin.dsl.RestDocsUtils.readJsonFile
import com.knu.mockin.dsl.RestDocsUtils.toBody
import com.knu.mockin.dsl.RestDocsUtils.toPairs
import com.knu.mockin.model.dto.kisresponse.trading.*
import com.knu.mockin.model.dto.request.trading.*
import com.knu.mockin.service.TradingService
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.StringSpec
import io.mockk.coEvery
import io.mockk.mockk
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.restdocs.ManualRestDocumentation
import org.springframework.test.web.servlet.MockMvc
import org.springframework.web.context.WebApplicationContext

@WebMvcTest(TradingController::class)
class TradingControllerTest(
    @MockkBean
    val tradingService: TradingService = mockk<TradingService>(),
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

    val baseUri = "/trading"

    "POST /trading/order" {
        val uri = "${baseUri}/order"
        val requestDto = readJsonFile(uri,"requestDto.json")
        val expectedDto = readJsonFile(uri, "responseDto.json") toDto KISOrderResponseDto::class.java
        coEvery { tradingService.postOrder(any()) } returns expectedDto

        val response = mockMvc.postWithBody(uri, requestDto, expectedDto)

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
        coEvery { tradingService.postOrderReverse(any()) } returns expectedDto

        val response = mockMvc.postWithBody(uri, requestDto, expectedDto)

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

        coEvery { tradingService.getNCCS(any()) } returns expectedDto

        val response = mockMvc.getWithParams(uri, requestParams, expectedDto)

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

        coEvery { tradingService.getBalance(any()) } returns expectedDto

        val response = mockMvc.getWithParams(uri, requestParams, expectedDto)

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

        coEvery { tradingService.getPsAmount(any()) } returns expectedDto

        val response = mockMvc.getWithParams(uri, requestParams, expectedDto)

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

        coEvery { tradingService.getPresentBalance(any()) } returns expectedDto

        val response = mockMvc.getWithParams(uri, requestParams, expectedDto)

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

        coEvery { tradingService.getCCNL(any()) } returns expectedDto

        val response = mockMvc.getWithParams(uri, requestParams, expectedDto)

        response.makeDocument(
            uri,
            parameters(readJsonFile(uri, "requestDtoDescription.json").toPairs()),
            responseBody(readJsonFile(uri, "responseDtoDescription.json").toBody())
        )
    }
})
