package com.knu.mockin.controller

import com.knu.mockin.controller.util.*
import com.knu.mockin.dsl.RestDocsUtils
import com.knu.mockin.dsl.RestDocsUtils.toBody
import com.knu.mockin.dsl.RestDocsUtils.toPairs
import com.knu.mockin.dsl.toDto
import com.knu.mockin.model.dto.kisresponse.basic.*
import com.knu.mockin.model.dto.request.basic.*
import com.knu.mockin.model.entity.User
import com.knu.mockin.repository.UserRepository
import com.knu.mockin.security.JwtUtil
import com.knu.mockin.security.SecurityTestConfig
import com.knu.mockin.service.BasicRealService
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

@WebFluxTest(controllers = [BasicRealController::class])
@Import(SecurityTestConfig::class)
class BasicRealControllerTest (
    @MockkBean
    val basicRealService: BasicRealService = mockk<BasicRealService>(),
    @MockkBean
    val userRepository: UserRepository = mockk(),
    @MockkBean
    val jwtUtil: JwtUtil = mockk<JwtUtil>(),
    private val context: ApplicationContext,
) : StringSpec({
    val restDocumentation = ManualRestDocumentation()
    lateinit var webTestClient: WebTestClient

    beforeTest {
        webTestClient = buildWebTestClient(context, restDocumentation)
        restDocumentation.beforeTest(TradingControllerTest::class.java, it.name.testName)

        val user = RestDocsUtils.readJsonFile("setting", "user.json") toDto User::class.java
        coEvery { userRepository.findByEmail(user.email) } returns Mono.just(user)
        coEvery { jwtUtil.getUsername(any()) } returns user.email
        coEvery { jwtUtil.isValid(any(), any()) } returns true
    }

    afterTest {
        restDocumentation.afterTest()
    }

    val baseUri = "/basic"

    "GET /basic/countries-holiday" {
        val uri = "${baseUri}/countries-holiday"
        val requestParams = RestDocsUtils.readJsonFile(uri, "requestDto.json") toDto CountriesHolidayRequestParameterDto::class.java
        val expectedDto = RestDocsUtils.readJsonFile(uri, "responseDto.json") toDto KISCountriesHolidayResponseDto::class.java

        coEvery { basicRealService.getCountriesHoliday(any()) } returns expectedDto

        val response = webTestClient.getWithParams(uri, requestParams, expectedDto)

        response.makeDocument(
            uri,
            parameters(RestDocsUtils.readJsonFile(uri, "requestDtoDescription.json").toPairs()),
            responseBody(RestDocsUtils.readJsonFile(uri, "responseDtoDescription.json").toBody())
        )
    }

    "GET /basic/price-detail" {
        val uri = "${baseUri}/price-detail"
        val requestParams = RestDocsUtils.readJsonFile(uri, "requestDto.json") toDto PriceDetailRequestParameterDto::class.java
        val expectedDto = RestDocsUtils.readJsonFile(uri, "responseDto.json") toDto KISPriceDetailResponseDto::class.java

        coEvery { basicRealService.getPriceDetail(any()) } returns expectedDto

        val response = webTestClient.getWithParams(uri, requestParams, expectedDto)

        response.makeDocument(
            uri,
            parameters(RestDocsUtils.readJsonFile(uri, "requestDtoDescription.json").toPairs()),
            responseBody(RestDocsUtils.readJsonFile(uri, "responseDtoDescription.json").toBody())
        )
    }

    "GET /basic/item-chart-price" {
        val uri = "${baseUri}/item-chart-price"
        val requestParams = RestDocsUtils.readJsonFile(uri, "requestDto.json") toDto ItemChartPriceRequestParameterDto::class.java
        val expectedDto = RestDocsUtils.readJsonFile(uri, "responseDto.json") toDto KISItemChartPriceResponseDto::class.java

        coEvery { basicRealService.getItemChartPrice(any()) } returns expectedDto

        val response = webTestClient.getWithParams(uri, requestParams, expectedDto)

        response.makeDocument(
            uri,
            parameters(RestDocsUtils.readJsonFile(uri, "requestDtoDescription.json").toPairs()),
            responseBody(RestDocsUtils.readJsonFile(uri, "responseDtoDescription.json").toBody())
        )
    }


    "GET /basic/index-chart-price" {
        val uri = "${baseUri}/index-chart-price"
        val requestParams = RestDocsUtils.readJsonFile(uri, "requestDto.json") toDto IndexChartPriceRequestParameterDto::class.java
        val expectedDto = RestDocsUtils.readJsonFile(uri, "responseDto.json") toDto KISIndexChartPriceResponseDto::class.java

        coEvery { basicRealService.getIndexChartPrice(any()) } returns expectedDto

        val response = webTestClient.getWithParams(uri, requestParams, expectedDto)

        response.makeDocument(
            uri,
            parameters(RestDocsUtils.readJsonFile(uri, "requestDtoDescription.json").toPairs()),
            responseBody(RestDocsUtils.readJsonFile(uri, "responseDtoDescription.json").toBody())
        )
    }

    "GET /basic/search-info" {
        val uri = "${baseUri}/search-info"
        val requestParams = RestDocsUtils.readJsonFile(uri, "requestDto.json") toDto SearchInfoRequestParameterDto::class.java
        val expectedDto = RestDocsUtils.readJsonFile(uri, "responseDto.json") toDto KISSearchInfoResponseDto::class.java

        coEvery { basicRealService.getSearchInfo(any()) } returns expectedDto
        val response = webTestClient.getWithParams(uri, requestParams, expectedDto)

        response.makeDocument(
            uri,
            parameters(RestDocsUtils.readJsonFile(uri, "requestDtoDescription.json").toPairs()),
            responseBody(RestDocsUtils.readJsonFile(uri, "responseDtoDescription.json").toBody())
        )
    }

    "GET /basic/news-title" {
        val uri = "${baseUri}/news-title"
        val requestParams = RestDocsUtils.readJsonFile(uri, "requestDto.json") toDto NewsTitleRequestParameterDto::class.java
        val expectedDto = RestDocsUtils.readJsonFile(uri, "responseDto.json") toDto KISNewsTitleResponseDto::class.java

        coEvery { basicRealService.getNewsTitle(any()) } returns expectedDto
        val response = webTestClient.getWithParams(uri, requestParams, expectedDto)

        response.makeDocument(
            uri,
            parameters(RestDocsUtils.readJsonFile(uri, "requestDtoDescription.json").toPairs()),
            responseBody(RestDocsUtils.readJsonFile(uri, "responseDtoDescription.json").toBody())
        )
    }
})
