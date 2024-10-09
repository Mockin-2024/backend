package com.knu.mockin.model.dto.kisrequest.trading

import com.fasterxml.jackson.annotation.JsonProperty

data class KISPsAmountRequestParameterDto(
    @JsonProperty("CANO") val accountNumber: String,                    // 종합계좌번호
    @JsonProperty("ACNT_PRDT_CD") val accountProductCode: String,       // 계좌상품코드
    @JsonProperty("OVRS_EXCG_CD") val overseasExchangeCode: String,     // 해외거래소코드
    @JsonProperty("OVRS_ORD_UNPR") val overseasOrderUnitPrice: String,  // 해외주문단가
    @JsonProperty("ITEM_CD") val itemCode: String                       // 종목코드
)
