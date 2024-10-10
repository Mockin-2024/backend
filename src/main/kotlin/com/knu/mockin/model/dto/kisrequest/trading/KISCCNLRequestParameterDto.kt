package com.knu.mockin.model.dto.kisrequest.trading

import com.fasterxml.jackson.annotation.JsonProperty

data class KISCCNLRequestParameterDto(
    @JsonProperty("CANO") val accountNumber: String,                                // 종합계좌번호
    @JsonProperty("ACNT_PRDT_CD") val accountProductCode: String,                   // 계좌상품코드
    @JsonProperty("PDNO") val productNumber: String = "%",                          // 상품번호
    @JsonProperty("ORD_STRT_DT") val orderStartDate: String,                        // 주문시작일자
    @JsonProperty("ORD_END_DT") val orderEndDate: String,                           // 주문종료일자
    @JsonProperty("SLL_BUY_DVSN") val sellBuyDivision: String = "00",               // 매도매수구분
    @JsonProperty("CCLD_NCCS_DVSN") val executionNonExecutionDivision: String = "", // 체결미체결구분
    @JsonProperty("OVRS_EXCG_CD") val overseasExchangeCode: String = "%",           // 해외거래소코드
    @JsonProperty("SORT_SQN") val sortOrder: String = "DS",                         // 정렬순서
    @JsonProperty("ORD_DT") val orderDate: String = "",                             // 주문일자
    @JsonProperty("ORD_GNO_BRNO") val orderBranchNumber: String = "",               // 주문채번지점번호
    @JsonProperty("ODNO") val orderNumber: String = "",                             // 주문번호
    @JsonProperty("CTX_AREA_NK200") val continuousSearchKey200: String = "",        // 연속조회키200
    @JsonProperty("CTX_AREA_FK200") val continuousSearchCondition200: String = ""   // 연속조회검색조건200
)
