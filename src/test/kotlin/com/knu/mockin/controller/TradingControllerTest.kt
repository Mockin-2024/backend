package com.knu.mockin.controller

import com.knu.mockin.dsl.*
import com.knu.mockin.model.OBJECT
import com.knu.mockin.model.STRING
import com.knu.mockin.model.dto.kisresponse.trading.KISPsAmountResponseDto
import com.knu.mockin.model.dto.request.trading.PsAmountRequestParameterDto
import com.knu.mockin.service.TradingService
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.StringSpec
import io.mockk.coEvery
import io.mockk.mockk
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.restdocs.ManualRestDocumentation
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder

@WebMvcTest(TradingController::class)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class TradingControllerTest(
    @MockkBean
    val tradingService: TradingService = mockk<TradingService>()
): StringSpec({
    lateinit var mockMvc: MockMvc
    val restDocumentation = ManualRestDocumentation()

    beforeTest {
        mockMvc = MockMvcBuilders
            .standaloneSetup(TradingController(tradingService))
            .apply<StandaloneMockMvcBuilder>(MockMvcRestDocumentation.documentationConfiguration(restDocumentation))
            .build()
        restDocumentation.beforeTest(TradingControllerTest::class.java, it.name.testName)
    }

    afterTest {
        restDocumentation.afterTest()
    }

    "GET psamount" {
        val uri = "/trading/psamount"
        val requestParams = PsAmountRequestParameterDto(
            overseasExchangeCode = "SZAA",
            overseasOrderUnitPrice = "100",
            itemCode = "1380",
            email = "test@gmail.com"
        )
        val expectedDto = KISPsAmountResponseDto(
            successFailure = "0",
            responseCode = "test",
            responseMessage = "테스트 성공",
            output = null
        )

        coEvery { tradingService.getPsAmount(any())} returns expectedDto
        val response = mockMvc.getWithParams(uri, requestParams)

        response.makeDocument(
            "/trading/psamount",
            setParameters(
                "overseasExchangeCode" means "거래소 코드",
                "overseasOrderUnitPrice" means "주당 가격",
                "itemCode" means "종목 코드",
                "email" means "사용자 이메일"
            ),
            setResponseBody(
                "successFailure" type STRING withDefaultValue "기본 값" means "성공 여부",
                "responseCode" type STRING  withDefaultValue "기본 값" means "응답 코드",
                "responseMessage" type STRING withDefaultValue "기본 값" means "응답 메세지",
                "output" type OBJECT  withDefaultValue "기본 값" isOptional true  means "처리 결과"
            )
        )
    }
})

//                    responseFields(
//                        fieldWithPath("rt_cd").description("성공 코드"),
//                        fieldWithPath("msg_cd").description("응답 코드"),
//                        fieldWithPath("msg1").description("응답 메세지"),
//                        fieldWithPath("output").description("output")
//                    )