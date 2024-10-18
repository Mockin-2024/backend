package com.knu.mockin.controller

import com.knu.mockin.dsl.*
import com.knu.mockin.dsl.RestDocsUtils.readJsonFile
import com.knu.mockin.dsl.RestDocsUtils.toBody
import com.knu.mockin.dsl.RestDocsUtils.toPairs
import com.knu.mockin.model.ARRAY
import com.knu.mockin.model.OBJECT
import com.knu.mockin.model.STRING
import com.knu.mockin.model.dto.kisresponse.basic.KISCurrentPriceResponseDto
import com.knu.mockin.model.dto.kisresponse.basic.KISDailyChartPriceResponseDto
import com.knu.mockin.model.dto.kisresponse.basic.KISSearchResponseDto
import com.knu.mockin.model.dto.kisresponse.basic.KISTermPriceResponseDto
import com.knu.mockin.model.dto.kisresponse.trading.KISNCCSResponseDto
import com.knu.mockin.model.dto.request.basic.CurrentPriceRequestParameterDto
import com.knu.mockin.model.dto.request.basic.DailyChartPriceRequestParameterDto
import com.knu.mockin.model.dto.request.basic.SearchRequestParameterDto
import com.knu.mockin.model.dto.request.basic.TermPriceRequestParameterDto
import com.knu.mockin.model.dto.request.trading.NCCSRequestParameterDto
import com.knu.mockin.service.BasicService
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.StringSpec
import io.mockk.coEvery
import io.mockk.mockk
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.restdocs.ManualRestDocumentation
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@WebMvcTest(BasicController::class)
class BasicControllerTest(
        @MockkBean
        val basicService: BasicService = mockk<BasicService>(),
        private val webApplicationContext: WebApplicationContext
) : StringSpec({
    val restDocumentation = ManualRestDocumentation()
    lateinit var mockMvc: MockMvc

    beforeTest {
        mockMvc = buildMockMvc(webApplicationContext, restDocumentation)
        restDocumentation.beforeTest(BasicControllerTest::class.java, it.name.testName)
    }

    afterTest {
        restDocumentation.afterTest()
    }

    val baseUri = "/basic"

    "GET /basic/current" {
        val uri = "${baseUri}/current"
        val requestParams = readJsonFile(uri, "requestDto.json") toDto CurrentPriceRequestParameterDto::class.java
        val expectedDto = readJsonFile(uri, "responseDto.json") toDto KISCurrentPriceResponseDto::class.java

        coEvery { basicService.getCurrentPrice(any()) } returns expectedDto

        val response = mockMvc.getWithParams(uri, requestParams, expectedDto)

        response.makeDocument(
            uri,
            parametersTemp(readJsonFile(uri, "requestDtoDescription.json").toPairs()),
            responseBodyTemp(readJsonFile(uri, "responseDtoDescription.json").toBody())
        )
    }

    "GET /basic/term" {
        val uri = "${baseUri}/term"
        val requestParams = readJsonFile(uri, "requestDto.json") toDto TermPriceRequestParameterDto::class.java
        val expectedDto = readJsonFile(uri, "responseDto.json") toDto KISTermPriceResponseDto::class.java

        coEvery { basicService.getTermPrice(any()) } returns expectedDto

        val response = mockMvc.getWithParams(uri, requestParams, expectedDto)

        response.makeDocument(
            uri,
            parametersTemp(readJsonFile(uri, "requestDtoDescription.json").toPairs()),
            responseBodyTemp(readJsonFile(uri, "responseDtoDescription.json").toBody())
        )
    }

    "GET /basic/daily-chart-price" {
        val uri = "${baseUri}/daily-chart-price"
        val requestParams = readJsonFile(uri, "requestDto.json") toDto DailyChartPriceRequestParameterDto::class.java
        val expectedDto = readJsonFile(uri, "responseDto.json") toDto KISDailyChartPriceResponseDto::class.java

        coEvery { basicService.getDailyChartPrice(any()) } returns expectedDto

        val response = mockMvc.getWithParams(uri, requestParams, expectedDto)

        response.makeDocument(
            uri,
            parametersTemp(readJsonFile(uri, "requestDtoDescription.json").toPairs()),
            responseBodyTemp(readJsonFile(uri, "responseDtoDescription.json").toBody())
        )
    }

    "GET /basic/search" {
        val uri = "${baseUri}/search"
        val requestParams = readJsonFile(uri, "requestDto.json") toDto SearchRequestParameterDto::class.java
        val expectedDto = readJsonFile(uri, "responseDto.json") toDto KISSearchResponseDto::class.java

        coEvery { basicService.getSearch(any()) } returns expectedDto

        val response = mockMvc.getWithParams(uri, requestParams, expectedDto)

        response.makeDocument(
            uri,
            parametersTemp(readJsonFile(uri, "requestDtoDescription.json").toPairs()),
            responseBodyTemp(readJsonFile(uri, "responseDtoDescription.json").toBody())
        )
    }

})
