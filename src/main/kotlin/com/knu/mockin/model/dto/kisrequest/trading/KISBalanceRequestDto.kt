package com.knu.mockin.model.dto.kisrequest.trading

import com.fasterxml.jackson.annotation.JsonProperty

data class KISBalanceRequestDto(
    @JsonProperty("CANO") val accountNumber: String,                            // 종합계좌번호 (8자리)
    @JsonProperty("ACNT_PRDT_CD") val accountProductCode: String,               // 계좌상품코드 (2자리)
    @JsonProperty("OVRS_EXCG_CD") val overseasExchangeCode: String,             // 해외거래소코드 (4자리)
    @JsonProperty("TR_CRCY_CD") val transactionCurrencyCode: String,            // 거래통화코드 (3자리)
    @JsonProperty("CTX_AREA_FK200") val continuousSearchCondition200: String,   // 연속조회검색조건200 (200자리)
    @JsonProperty("CTX_AREA_NK200") val continuousSearchKey200: String          // 연속조회키200 (200자리)
)