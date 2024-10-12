package com.knu.mockin.controller

import com.knu.mockin.dsl.*
import com.knu.mockin.logging.utils.LogUtil
import com.knu.mockin.model.ARRAY
import com.knu.mockin.model.OBJECT
import com.knu.mockin.model.STRING
import com.knu.mockin.model.dto.kisresponse.trading.KISNCCSResponseDto
import com.knu.mockin.model.dto.kisresponse.trading.KISPsAmountResponseDto
import com.knu.mockin.model.dto.request.trading.NCCSRequestParameterDto
import com.knu.mockin.model.dto.request.trading.PsAmountRequestParameterDto
import com.knu.mockin.service.TradingService
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.StringSpec
import io.mockk.coEvery
import io.mockk.mockk
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.restdocs.ManualRestDocumentation
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@WebMvcTest(TradingController::class)
@WebAppConfiguration
class TradingControllerTest(
    @MockkBean
    val tradingService: TradingService = mockk<TradingService>(),
    val webApplicationContext: WebApplicationContext
): StringSpec({
    val restDocumentation = ManualRestDocumentation()
    lateinit var mockMvc: MockMvc

    beforeTest {
        mockMvc = MockMvcBuilders
            .webAppContextSetup(webApplicationContext)
            .apply<DefaultMockMvcBuilder>(MockMvcRestDocumentation.documentationConfiguration(restDocumentation))
            .build()
        restDocumentation.beforeTest(TradingControllerTest::class.java, it.name.testName)
    }

    afterTest {
        restDocumentation.afterTest()
    }

    val baseUri = "/trading"

    "GET /trading/nccs" {
        val uri = "${baseUri}/nccs"
        val requestParams = NCCSRequestParameterDto(
            overseasExchangeCode = "SZAA",
            sortOrder = "DS",
            continuousSearchCondition200 = "",
            continuousSearchKey200 = "",
            email = "test@naver.com"
        )
        val expectedDto = KISNCCSResponseDto(
            successFailure = "0",
            responseCode = "",
            responseMessage = "",
            continuousSearchCondition200 = "",
            continuousSearchKey200 = "",
            output = listOf()
        )
        println(LogUtil.toJson(expectedDto))
        coEvery { tradingService.getNCCS(any()) } returns expectedDto

        val response = mockMvc.getWithParams(uri, requestParams, expectedDto)

        response.makeDocument(
            "/trading/nccs",
            setParameters(
                "overseasExchangeCode" means "해외거래소코드",
                "sortOrder" means "정렬 순서",
                "continuousSearchCondition200" means "연속조회검색조건200",
                "continuousSearchKey200" means "연속조회키200",
                "email" means "이메일"
            ),
            setResponseBody(
                "rt_cd" type STRING means "성공 여부",
                "msg_cd" type STRING  means "응답 코드",
                "msg1" type STRING means "응답 메세지",
                "ctx_area_fk200" type STRING means "연속조회검색조건200",
                "ctx_area_nk200" type STRING means "연속조회키200",
                "output" type ARRAY isOptional true means "처리 결과"
            )
        )
    }


    "GET /trading/psamount" {
        val uri = "${baseUri}/psamount"
        val requestParams = PsAmountRequestParameterDto(
            overseasExchangeCode = "SZAA",
            overseasOrderUnitPrice = "100",
            itemCode = "1380",
            email = "test@naver.com"
        )
        val expectedDto = KISPsAmountResponseDto(
            successFailure = "0",
            responseCode = "test",
            responseMessage = "테스트 성공",
            output = null
        )

        coEvery { tradingService.getPsAmount(any())} returns expectedDto
        val response = mockMvc.getWithParams(uri, requestParams, expectedDto)

        response.makeDocument(
            "/trading/psamount",
            setParameters(
                "overseasExchangeCode" means "거래소 코드",
                "overseasOrderUnitPrice" means "주당 가격",
                "itemCode" means "종목 코드",
                "email" means "사용자 이메일"
            ),
            setResponseBody(
                "rt_cd" type STRING means "성공 여부",
                "msg_cd" type STRING means "응답 코드",
                "msg1" type STRING means "응답 메세지",
                "output" type OBJECT isOptional true means "처리 결과"
            )
        )
    }
})
