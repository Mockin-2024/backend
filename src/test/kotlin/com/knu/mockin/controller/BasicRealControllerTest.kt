package com.knu.mockin.controller

import com.knu.mockin.dsl.*
import com.knu.mockin.model.ARRAY
import com.knu.mockin.model.OBJECT
import com.knu.mockin.model.STRING
import com.knu.mockin.model.dto.kisresponse.basic.*
import com.knu.mockin.model.dto.request.basic.*
import com.knu.mockin.service.BasicRealService
import com.knu.mockin.service.BasicService
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.StringSpec
import io.mockk.coEvery
import io.mockk.mockk
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.restdocs.ManualRestDocumentation
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
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
        val requestParams = CountriesHolidayRequestParameterDto(
                tradDt = "20221227",
                ctxAreaNk = "",
                ctxAreaFk = "",
                email = "test@naver.com"
        )
        val expectedDto = KISCountriesHolidayResponseDto(
                successFailureStatus = "0",
                responseCode = "KIOK0460",
                responseMessage = "조회 되었습니다. (마지막 자료)",
                output = listOf()
        )
        coEvery { basicRealService.getCountriesHoliday(any()) } returns expectedDto

        val response = mockMvc.getWithParams(uri, requestParams, expectedDto)

        response.makeDocument(
                uri,
                parameters(
                        "tradDt" means "기준 일자",
                        "ctxAreaNk" means "연속조회키",
                        "ctxAreaFk" means "연속조회검색조건",
                        "email" means "사용자 이메일"
                ),

                responseBody(
                        "rt_cd" type STRING means "결과 코드",
                        "msg_cd" type STRING means "메시지 코드",
                        "msg1" type STRING means "메시지",
                        "output" type ARRAY isOptional true means "처리 결과"
                )
        )
    }

    "GET /basic/price-detail" {
        val uri = "${baseUri}/price-detail"
        val requestParams = PriceDetailRequestParameterDto(
                AUTH = "",
                EXCD = "NAS",
                SYMB = "TSLA",
                email = "test@naver.com"
        )
        val expectedDto = KISPriceDetailResponseDto(
                successFailureStatus = "0",
                responseCode = "MCA00000",
                responseMessage = "정상처리 되었습니다.",
                output = null
        )
        coEvery { basicRealService.getPriceDetail(any()) } returns expectedDto

        val response = mockMvc.getWithParams(uri, requestParams, expectedDto)

        response.makeDocument(
                uri,
                parameters(
                        "AUTH" means "사용자 권한정보",
                        "EXCD" means "거래소명",
                        "SYMB" means "종목코드",
                        "email" means "사용자 이메일"
                ),

                responseBody(
                        "rt_cd" type STRING means "결과 코드",
                        "msg_cd" type STRING means "메시지 코드",
                        "msg1" type STRING means "메시지",
                        "output" type OBJECT isOptional true means "처리 결과"
                )
        )
    }

    "GET /basic/item-chart-price" {
    val uri = "${baseUri}/item-chart-price"
    val requestParams = ItemChartPriceRequestParameterDto(
            AUTH = "",
            EXCD = "NAS",
            SYMB = "TSLA",
            NMIN = "5",
            PINC = "1",
            NEXT = "",
            NREC = "120",
            FILL = "",
            KEYB = "",
            email = "test@naver.com"
    )
    val expectedDto = KISItemChartPriceResponseDto(
            successFailureStatus = "0",
            responseCode = "MCA00000",
            responseMessage = "정상처리 되었습니다.",
            output1 = null,
            output2 = listOf()
    )

    coEvery { basicRealService.getItemChartPrice(any()) } returns expectedDto

    val response = mockMvc.getWithParams(uri, requestParams, expectedDto)

    response.makeDocument(
            uri,
            parameters(
                    "AUTH" means "사용자 권한정보",
                    "EXCD" means "거래소명",
                    "SYMB" means "종목코드",
                    "NMIN" means "분갭",
                    "PINC" means "전일포함여부",
                    "NEXT" means "다음여부",
                    "NREC" means "요청갯수",
                    "FILL" means "미체결채움구분",
                    "KEYB" means "다음 키 버퍼",
                    "email" means "사용자 이메일"
            ),

            responseBody(
                    "rt_cd" type STRING means "결과 코드",
                    "msg_cd" type STRING means "메시지 코드",
                    "msg1" type STRING means "메시지",
                    "output1" type OBJECT isOptional true means "응답 상세 1",
                    "output2" type ARRAY isOptional true means "응답 상세 2"
            )
    )
}

})
