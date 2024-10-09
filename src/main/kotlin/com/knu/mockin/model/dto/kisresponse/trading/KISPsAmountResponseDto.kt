package com.knu.mockin.model.dto.kisresponse.trading

import com.fasterxml.jackson.annotation.JsonProperty

data class KISPsAmountResponseDto(
    @JsonProperty("rt_cd") val successFailure: String,  // 성공 실패 여부
    @JsonProperty("msg_cd") val responseCode: String,   // 응답코드
    @JsonProperty("msg1") val responseMessage: String,  // 응답메세지
    @JsonProperty("output") val output: OutputDto?      // 응답상세1
)

data class OutputDto(
    @JsonProperty("tr_crcy_cd") val transactionCurrencyCode: String?,                       // 거래통화코드
    @JsonProperty("ord_psbl_frcr_amt") val orderPossibleForeignCurrencyAmount: String?,     // 주문가능외화금액
    @JsonProperty("sll_ruse_psbl_amt") val sellReusePossibleAmount: String?,                // 매도재사용가능금액
    @JsonProperty("ovrs_ord_psbl_amt") val overseasOrderPossibleAmount: String?,            // 해외주문가능금액
    @JsonProperty("max_ord_psbl_qty") val maxOrderPossibleQuantity: String?,                // 최대주문가능수량
    @JsonProperty("echm_af_ord_psbl_amt") val exchangeAfterOrderPossibleAmount: String?,    // 환전이후주문가능금액
    @JsonProperty("echm_af_ord_psbl_qty") val exchangeAfterOrderPossibleQuantity: String?,  // 환전이후주문가능수량
    @JsonProperty("ord_psbl_qty") val orderPossibleQuantity: String?,                       // 주문가능수량
    @JsonProperty("exrt") val exchangeRate: String?,                                        // 환율
    @JsonProperty("frcr_ord_psbl_amt1") val foreignOrderPossibleAmount1: String?,           // 외화주문가능금액1
    @JsonProperty("ovrs_max_ord_psbl_qty") val overseasMaxOrderPossibleQuantity: String?    // 해외최대주문가능수량
)