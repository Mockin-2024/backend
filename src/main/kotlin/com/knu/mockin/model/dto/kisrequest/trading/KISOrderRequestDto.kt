package com.knu.mockin.model.dto.kisrequest.trading

import com.fasterxml.jackson.annotation.JsonProperty

data class KISOrderRequestDto(
    @JsonProperty("CANO") val accountNumber: String,                         // 종합계좌번호
    @JsonProperty("ACNT_PRDT_CD") val accountProductCode: String,            // 계좌상품코드
    @JsonProperty("OVRS_EXCG_CD") val overseasExchangeCode: String,          // 해외거래소코드
    @JsonProperty("PDNO") val productNumber: String,                         // 상품번호
    @JsonProperty("ORD_QTY") val orderQuantity: String,                      // 주문수량
    @JsonProperty("OVRS_ORD_UNPR") val overseasOrderUnitPrice: String,       // 해외주문단가
    @JsonProperty("CTAC_TLNO") val contactTelephoneNumber: String? = null,   // 연락전화번호
    @JsonProperty("MGCO_APTM_ODNO") val managementDesignatedOrderNumber: String? = null,   // 운용사지정주문번호
    @JsonProperty("SLL_TYPE") val saleType: String? = null,                  // 판매유형
    @JsonProperty("ORD_SVR_DVSN_CD") val orderServerDivisionCode: String = "0",    // 주문서구분코드
    @JsonProperty("ORD_DVSN") val orderDivision: String? = null                     // 주문구분
)