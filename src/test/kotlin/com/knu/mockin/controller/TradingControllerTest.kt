package com.knu.mockin.controller

import com.knu.mockin.dsl.*
import com.knu.mockin.model.ARRAY
import com.knu.mockin.model.OBJECT
import com.knu.mockin.model.STRING
import com.knu.mockin.model.dto.kisresponse.trading.*
import com.knu.mockin.model.dto.request.trading.*
import com.knu.mockin.model.enum.ExchangeCode
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
        val requestDto = OrderRequestBodyDto(
            transactionId = "",
            overseasExchangeCode = ExchangeCode.SHAA.name,
            productNumber = "1380",
            orderQuantity = "1",
            overseasOrderUnitPrice = "0",
            email = "test@naver.com"
        )
        val expectedDto = KISOrderResponseDto(
            successFailureStatus = "0",
            responseCode = "000",
            responseMessage = "Test success",
            output = null
        )
        coEvery { tradingService.postOrder(any()) } returns expectedDto

        val response = mockMvc.postWithBody(uri, requestDto, expectedDto)

        response.makeDocument(
            uri,
            requestBody(
                "transactionId" type STRING means "요청 종류",
                "overseasExchangeCode" type STRING means "해외 거래소 코드",
                "productNumber" type STRING means "상품 번호",
                "orderQuantity" type STRING means "주문 수량",
                "overseasOrderUnitPrice" type STRING means "해외 주문 단가",
                "email" type STRING means "이메일"
            ),
            responseBody(
                "rt_cd" type STRING means "성공 여부",
                "msg_cd" type STRING  means "응답 코드",
                "msg1" type STRING means "응답 메세지",
                "output" type ARRAY isOptional true means "처리 결과"
            )
        )
    }

    "GET /trading/nccs" {
        val uri = "${baseUri}/nccs"
        val requestParams = NCCSRequestParameterDto(
            overseasExchangeCode = ExchangeCode.SHAA.name,
            sortOrder = "DS",
            continuousSearchCondition200 = "",
            continuousSearchKey200 = "",
            email = "test@naver.com"
        )
        val expectedDto = KISNCCSResponseDto(
            successFailureStatus = "0",
            responseCode = "70070000",
            responseMessage = "Test success",
            continuousSearchCondition200 = "",
            continuousSearchKey200 = "",
            output = listOf()
        )
        coEvery { tradingService.getNCCS(any()) } returns expectedDto

        val response = mockMvc.getWithParams(uri, requestParams, expectedDto)

        response.makeDocument(
            uri,
            parameters(
                "overseasExchangeCode" means "해외거래소코드",
                "sortOrder" means "정렬 순서",
                "continuousSearchCondition200" means "연속조회검색조건200",
                "continuousSearchKey200" means "연속조회키200",
                "email" means "이메일"
            ),
            responseBody(
                "rt_cd" type STRING means "성공 여부",
                "msg_cd" type STRING  means "응답 코드",
                "msg1" type STRING means "응답 메세지",
                "ctx_area_fk200" type STRING means "연속조회검색조건200",
                "ctx_area_nk200" type STRING means "연속조회키200",
                "output" type ARRAY isOptional true means "처리 결과"
            )
        )
    }

    "GET /trading/balance" {
        val uri = "${baseUri}/balance"
        val requestParams = BalanceRequestParameterDto(
            overseasExchangeCode = ExchangeCode.SHAA.name,
            transactionCurrencyCode = "CNY",
            continuousSearchCondition200 = "",
            continuousSearchKey200 = "",
            email = "test@naver.com"
        )
        val expectedDto = KISBalanceResponseDto(
            successFailureStatus = "0",
            responseCode = "70070000",
            responseMessage = "Test success",
            continuousSearchCondition200 = "",
            continuousSearchKey200 = "",
            output1 = listOf(),
            output2 = null
        )
        coEvery { tradingService.getBalance(any()) } returns expectedDto

        val response = mockMvc.getWithParams(uri, requestParams, expectedDto)

        response.makeDocument(
            uri,
            parameters(
                "overseasExchangeCode" means "해외거래소코드",
                "transactionCurrencyCode" means "거래통화코드",
                "continuousSearchCondition200" means "연속조회검색조건200",
                "continuousSearchKey200" means "연속조회키200",
                "email" means "이메일"
            ),
            responseBody(
                "rt_cd" type STRING means "성공 여부",
                "msg_cd" type STRING  means "응답 코드",
                "msg1" type STRING means "응답 메세지",
                "ctx_area_fk200" type STRING means "연속조회검색조건200",
                "ctx_area_nk200" type STRING means "연속조회키200",
                "output1" type ARRAY isOptional true means "처리 결과1",
                "output2" type OBJECT isOptional true means "처리 결과2"
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
            successFailureStatus = "0",
            responseCode = "test",
            responseMessage = "test success!",
            output = null
        )

        coEvery { tradingService.getPsAmount(any())} returns expectedDto
        val response = mockMvc.getWithParams(uri, requestParams, expectedDto)

        response.makeDocument(
            uri,
            parameters(
                "overseasExchangeCode" means "거래소 코드",
                "overseasOrderUnitPrice" means "주당 가격",
                "itemCode" means "종목 코드",
                "email" means "사용자 이메일"
            ),
            responseBody(
                "rt_cd" type STRING means "성공 여부",
                "msg_cd" type STRING means "응답 코드",
                "msg1" type STRING means "응답 메세지",
                "output" type OBJECT isOptional true means "처리 결과"
            )
        )
    }

    "GET /trading/present-balance" {
        val uri = "${baseUri}/present-balance"
        val requestParams = PresentBalanceRequestParameterDto(
            currencyDivisionCode = "CNY",
            countryCode = "000",
            marketCode = "000",
            inquiryDivisionCode = "00",
            email = "test@naver.com"
        )
        val expectedDto = KISPresentBalanceResponseDto(
            successFailureStatus = "0",
            responseCode = "test",
            responseMessage = "test success!",
            output1 = listOf(),
            output2 = listOf(),
            output3 = null
        )

        coEvery { tradingService.getPresentBalance(any())} returns expectedDto
        val response = mockMvc.getWithParams(uri, requestParams, expectedDto)

        response.makeDocument(
            uri,
            parameters(
                "currencyDivisionCode" means "원화외화구분코드",
                "countryCode" means "국가코드",
                "marketCode" means "거래시장코드",
                "inquiryDivisionCode" means "조회구분코드",
                "email" means "이메일"
            ),
            responseBody(
                "rt_cd" type STRING means "성공 여부",
                "msg_cd" type STRING means "응답 코드",
                "msg1" type STRING means "응답 메세지",
                "output1" type ARRAY isOptional true means "응답상세1",
                "output2" type ARRAY isOptional true means "응답상세2",
                "output3" type OBJECT isOptional true means "응답상세3"
            )
        )
    }

    "GET /trading/ccnl" {
        val uri = "${baseUri}/ccnl"
        val requestParams = CCNLRequestParameterDto(
            orderStartDate = "20241010",
            orderEndDate = "20241015",
            email = "test@naver.com"
        )
        val expectedDto = KISCCNLResponseDto(
            successFailureStatus = "0",
            messageCode = "test",
            responseMessage = "test success!",
            continuousQuerySearchCondition200 = "",
            continuousQueryKey200 = "listOf()",
            output = listOf()
        )

        coEvery { tradingService.getCCNL(any())} returns expectedDto
        val response = mockMvc.getWithParams(uri, requestParams, expectedDto)

        response.makeDocument(
            uri,
            parameters(
                "orderStartDate" means "주문시작일자",
                "orderEndDate" means "주문종료일자",
                "email" means "이메일"
            ),
            responseBody(
                "rt_cd" type STRING means "성공 여부",
                "msg_cd" type STRING means "응답 코드",
                "msg1" type STRING means "응답 메세지",
                "ctx_area_fk200" type STRING means "연속조회검색조건200",
                "ctx_area_nk200" type STRING means "연속조회키200",
                "output" type ARRAY isOptional true means "응답상세3"
            )
        )
    }
})
