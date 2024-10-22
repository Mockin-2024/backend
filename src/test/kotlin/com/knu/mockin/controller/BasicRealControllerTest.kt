package com.knu.mockin.controller

import com.knu.mockin.controller.util.*
import com.knu.mockin.dsl.*
import com.knu.mockin.dsl.RestDocsUtils.toBody
import com.knu.mockin.dsl.RestDocsUtils.toPairs
import com.knu.mockin.model.dto.kisresponse.basic.*
import com.knu.mockin.model.dto.request.basic.*
import com.knu.mockin.service.BasicRealService
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.StringSpec
import io.mockk.coEvery
import io.mockk.mockk
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.restdocs.ManualRestDocumentation
import org.springframework.test.web.servlet.MockMvc
import org.springframework.web.context.WebApplicationContext

@WebMvcTest(BasicRealController::class)
class BasicRealControllerTest (
        @MockkBean
        val basicRealService: BasicRealService = mockk<BasicRealService>(),
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

    "GET /basic/countries-holiday" {
        val uri = "${baseUri}/countries-holiday"
        val requestParams = RestDocsUtils.readJsonFile(uri, "requestDto.json") toDto CountriesHolidayRequestParameterDto::class.java
        val expectedDto = RestDocsUtils.readJsonFile(uri, "responseDto.json") toDto KISCountriesHolidayResponseDto::class.java

        coEvery { basicRealService.getCountriesHoliday(any()) } returns expectedDto

        val response = mockMvc.getWithParams(uri, requestParams, expectedDto)

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

        val response = mockMvc.getWithParams(uri, requestParams, expectedDto)

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

        val response = mockMvc.getWithParams(uri, requestParams, expectedDto)

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

        val response = mockMvc.getWithParams(uri, requestParams, expectedDto)

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

        val response = mockMvc.getWithParams(uri, requestParams, expectedDto)

        response.makeDocument(
            uri,
            parameters(RestDocsUtils.readJsonFile(uri, "requestDtoDescription.json").toPairs()),
            responseBody(RestDocsUtils.readJsonFile(uri, "responseDtoDescription.json").toBody())
        )
    }

})
