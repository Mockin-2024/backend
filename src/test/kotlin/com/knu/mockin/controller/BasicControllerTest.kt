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
        val requestParams = DailyChartPriceRequestParameterDto(
                fidCondMrktDivCode = "N",
                fidInputDate1 = "20220401",
                fidInputDate2 = "20220613",
                fidInputIscd = ".DJI",
                fidPeriodDivCode = "D",
                email = "test@naver.com"
        )
        val expectedDto = KISDailyChartPriceResponseDto(
                successFailureStatus = "0",
                responseCode = "MCA00000",
                responseMessage = "Test success",
                output1 = null,
                output2 = listOf()
        )
        coEvery { basicService.getDailyChartPrice(any()) } returns expectedDto

        val response = mockMvc.getWithParams(uri, requestParams, expectedDto)

        response.makeDocument(
                uri,
                parameters(
                        "fidCondMrktDivCode" means "시장 구분 코드",
                        "fidInputDate1" means "시작 날짜",
                        "fidInputDate2" means "종료 날짜",
                        "fidInputIscd" means "입력 코드",
                        "fidPeriodDivCode" means "기간 구분 코드",
                        "email" means "사용자 이메일"
                ),

                responseBody(
                        "rt_cd" type STRING means "결과 코드",
                        "msg_cd" type STRING means "메시지 코드",
                        "msg1" type STRING means "메시지",
                        "output1" type OBJECT isOptional true means "처리 결과1",
                        "output2" type ARRAY isOptional true means "처리 결과2"
                )
        )
    }

    "GET /basic/search" {
        val uri = "${baseUri}/search"
        val requestParams = SearchRequestParameterDto(
                AUTH = "",
                EXCD = "NAS",
                coYnPricecur = "1",
                coStPricecur = "160",
                coEnPricecur = "161",
                coYnRate = "",
                coStRate = "",
                coEnRate = "",
                coYnValx = "",
                coStValx = "",
                coEnValx = "",
                coYnShar = "",
                coStShar = "",
                coEnShar = "",
                coYnVolume = "",
                coStVolume = "",
                coEnVolume = "",
                coYnAmt = "",
                coStAmt = "",
                coEnAmt = "",
                coYnEps = "",
                coStEps = "",
                coEnEps = "",
                coYnPer = "",
                coStPer = "",
                coEnPer = "",
                KEYB = "",
                email = "test@naver.com"
        )
        val expectedDto = KISSearchResponseDto(
                successFailureStatus = "0",
                responseCode = "MCA00000",
                responseMessage = "Test success",
                output1 = null,
                output2 = listOf()
        )
        coEvery { basicService.getSearch(any()) } returns expectedDto

        val response = mockMvc.getWithParams(uri, requestParams, expectedDto)

        response.makeDocument(
                uri,
                parameters(
                        "AUTH" means "인증",
                        "EXCD" means "거래소 코드",
                        "coYnPricecur" means "가격 현재 여부",
                        "coStPricecur" means "가격 시작",
                        "coEnPricecur" means "가격 종료",
                        "coYnRate" means "비율 현재 여부",
                        "coStRate" means "비율 시작",
                        "coEnRate" means "비율 종료",
                        "coYnValx" means "시가총액 여부",
                        "coStValx" means "시가총액 시작",
                        "coEnValx" means "시가총액 종료",
                        "coYnShar" means "발행 주식 여부",
                        "coStShar" means "발행 주식 시작",
                        "coEnShar" means "발행 주식 종료",
                        "coYnVolume" means "거래량 여부",
                        "coStVolume" means "거래량 시작",
                        "coEnVolume" means "거래량 종료",
                        "coYnAmt" means "거래 금액 여부",
                        "coStAmt" means "거래 금액 시작",
                        "coEnAmt" means "거래 금액 종료",
                        "coYnEps" means "EPS 여부",
                        "coStEps" means "EPS 시작",
                        "coEnEps" means "EPS 종료",
                        "coYnPer" means "PER 여부",
                        "coStPer" means "PER 시작",
                        "coEnPer" means "PER 종료",
                        "KEYB" means "다음 키 버퍼",
                        "email" means "사용자 이메일"
                ),

                responseBody(
                        "rt_cd" type STRING means "결과 코드",
                        "msg_cd" type STRING means "메시지 코드",
                        "msg1" type STRING means "메시지",
                        "output1" type OBJECT isOptional true means "처리 결과1",
                        "output2" type ARRAY isOptional true means "처리 결과2"
                )
        )
    }

})
