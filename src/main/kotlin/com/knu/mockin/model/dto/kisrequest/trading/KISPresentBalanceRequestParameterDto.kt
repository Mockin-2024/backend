package com.knu.mockin.model.dto.kisrequest.trading

import com.fasterxml.jackson.annotation.JsonProperty

data class KISPresentBalanceRequestParameterDto(
    @JsonProperty("CANO") val accountNumber: String,                     // 종합계좌번호 (8자리)
    @JsonProperty("ACNT_PRDT_CD") val accountProductCode: String,        // 계좌상품코드 (2자리)
    @JsonProperty("WCRC_FRCR_DVSN_CD") val currencyDivisionCode: String, // 원화외화구분코드 (01: 원화, 02: 외화)
    @JsonProperty("NATN_CD") val countryCode: String,                    // 국가코드 (3자리)
    @JsonProperty("TR_MKET_CD") val marketCode: String,                  // 거래시장코드 (2자리)
    @JsonProperty("INQR_DVSN_CD") val inquiryDivisionCode: String        // 조회구분코드 (2자리)
)
