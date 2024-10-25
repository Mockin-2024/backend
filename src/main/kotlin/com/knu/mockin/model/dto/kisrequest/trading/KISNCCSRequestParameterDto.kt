package com.knu.mockin.model.dto.kisrequest.trading

import com.fasterxml.jackson.annotation.JsonProperty

data class KISNCCSRequestParameterDto(
    @JsonProperty("CANO") val accountNumber: String,                            // 종합계좌번호
    @JsonProperty("ACNT_PRDT_CD") val accountProductCode: String = "01",               // 계좌상품코드
    @JsonProperty("OVRS_EXCG_CD") val overseasExchangeCode: String,             // 해외거래소코드
    @JsonProperty("SORT_SQN") val sortOrder: String,                            // 정렬순서
    @JsonProperty("CTX_AREA_FK200") val continuousSearchCondition200: String,   // 연속조회검색조건200
    @JsonProperty("CTX_AREA_NK200") val continuousSearchKey200: String          // 연속조회키200
)
