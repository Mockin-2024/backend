package com.knu.mockin.controller.quotations.basic.mock

import com.knu.mockin.controller.quotations.basic.BasicController
import com.knu.mockin.controller.util.*
import com.knu.mockin.dsl.*
import com.knu.mockin.dsl.RestDocsUtils.readJsonFile
import com.knu.mockin.dsl.RestDocsUtils.toBody
import com.knu.mockin.dsl.RestDocsUtils.toPairs
import com.knu.mockin.dsl.toDto
import com.knu.mockin.model.dto.kisresponse.quotations.basic.mock.KISCurrentPriceResponseDto
import com.knu.mockin.model.dto.kisresponse.quotations.basic.mock.KISDailyChartPriceResponseDto
import com.knu.mockin.model.dto.kisresponse.quotations.basic.mock.KISSearchResponseDto
import com.knu.mockin.model.dto.kisresponse.quotations.basic.mock.KISTermPriceResponseDto
import com.knu.mockin.model.dto.request.quotations.basic.mock.CurrentPriceRequestParameterDto
import com.knu.mockin.model.dto.request.quotations.basic.mock.DailyChartPriceRequestParameterDto
import com.knu.mockin.model.dto.request.quotations.basic.mock.SearchRequestParameterDto
import com.knu.mockin.model.dto.request.quotations.basic.mock.TermPriceRequestParameterDto
import com.knu.mockin.service.quotations.basic.mock.BasicService
import com.knu.mockin.model.entity.User
import com.knu.mockin.repository.UserRepository
import com.knu.mockin.security.JwtUtil
import com.knu.mockin.security.SecurityTestConfig
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

@WebFluxTest(controllers = [BasicController::class])
@Import(SecurityTestConfig::class)
class BasicControllerTest(
    @MockkBean
    val basicService: BasicService = mockk<BasicService>(),
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
        restDocumentation.beforeTest(BasicControllerTest::class.java, it.name.testName)

        val user = readJsonFile("setting", "user.json") toDto User::class.java
        coEvery { userRepository.findByEmail(user.email) } returns Mono.just(user)
        coEvery { jwtUtil.getUsername(any()) } returns user.email
        coEvery { jwtUtil.isValid(any(), any()) } returns true
    }

    afterTest {
        restDocumentation.afterTest()
    }

    val baseUri = "/quotations/basic"

    "GET /quotations/basic/current" {
        val uri = "${baseUri}/current"
        val requestParams = readJsonFile(uri, "requestDto.json") toDto CurrentPriceRequestParameterDto::class.java
        val expectedDto = readJsonFile(uri, "responseDto.json") toDto KISCurrentPriceResponseDto::class.java

        coEvery { basicService.getCurrentPrice(any(), any()) } returns expectedDto

        val response = webTestClient.getWithParams(uri, requestParams, expectedDto)

        response.makeDocument(
            uri,
            parameters(readJsonFile(uri, "requestDtoDescription.json").toPairs()),
            responseBody(readJsonFile(uri, "responseDtoDescription.json").toBody())
        )
    }

    "GET /quotations/basic/term" {
        val uri = "${baseUri}/term"
        val requestParams = readJsonFile(uri, "requestDto.json") toDto TermPriceRequestParameterDto::class.java
        val expectedDto = readJsonFile(uri, "responseDto.json") toDto KISTermPriceResponseDto::class.java

        coEvery { basicService.getTermPrice(any(), any()) } returns expectedDto

        val response = webTestClient.getWithParams(uri, requestParams, expectedDto)

        response.makeDocument(
            uri,
            parameters(readJsonFile(uri, "requestDtoDescription.json").toPairs()),
            responseBody(readJsonFile(uri, "responseDtoDescription.json").toBody())
        )
    }

    "GET /quotations/basic/daily-chart-price" {
        val uri = "${baseUri}/daily-chart-price"
        val requestParams = readJsonFile(uri, "requestDto.json") toDto DailyChartPriceRequestParameterDto::class.java
        val expectedDto = readJsonFile(uri, "responseDto.json") toDto KISDailyChartPriceResponseDto::class.java

        coEvery { basicService.getDailyChartPrice(any(), any()) } returns expectedDto

        val response = webTestClient.getWithParams(uri, requestParams, expectedDto)

        response.makeDocument(
            uri,
            parameters(readJsonFile(uri, "requestDtoDescription.json").toPairs()),
            responseBody(readJsonFile(uri, "responseDtoDescription.json").toBody())
        )
    }

    "GET /quotations/basic/search" {
        val uri = "${baseUri}/search"
        val requestParams = readJsonFile(uri, "requestDto.json") toDto SearchRequestParameterDto::class.java
        val expectedDto = readJsonFile(uri, "responseDto.json") toDto KISSearchResponseDto::class.java

        coEvery { basicService.getSearch(any(), any()) } returns expectedDto

        val response = webTestClient.getWithParams(uri, requestParams, expectedDto)

        response.makeDocument(
            uri,
            parameters(readJsonFile(uri, "requestDtoDescription.json").toPairs()),
            responseBody(readJsonFile(uri, "responseDtoDescription.json").toBody())
        )
    }

})
