package com.knu.mockin.model.dto.kisrequest.trading

import com.fasterxml.jackson.annotation.JsonProperty

data class KISOrderReserveRequestBodyDto(
    @JsonProperty("CANO") val accountNumber: String,                            // 종합계좌번호
    @JsonProperty("ACNT_PRDT_CD") val accountProductCode: String = "01",        // 계좌상품코드
    @JsonProperty("SLL_BUY_DVSN_CD") val sellBuyDivisionCode: String?,          // 매도매수구분코드
    @JsonProperty("RVSE_CNCL_DVSN_CD") val reviseCancelDivisionCode: String,    // 정정취소구분코드
    @JsonProperty("PDNO") val productNumber: String,                            // 상품번호
    @JsonProperty("PRDT_TYPE_CD") val productTypeCode: String?,                 // 상품유형코드
    @JsonProperty("OVRS_EXCG_CD") val overseasExchangeCode: String,             // 해외거래소코드
    @JsonProperty("FT_ORD_QTY") val ftOrderQuantity: String,                    // FT주문수량
    @JsonProperty("FT_ORD_UNPR3") val ftOrderUnitPrice3: String,                // FT주문단가3
    @JsonProperty("ORD_SVR_DVSN_CD") val orderServerDivisionCode: String? = "0",// 주문서버구분코드
    @JsonProperty("RSVN_ORD_RCIT_DT") val reservationOrderReceiptDate: String?, // 예약주문접수일자
    @JsonProperty("ORD_DVSN") val orderDivision: String?,                       // 주문구분
    @JsonProperty("OVRS_RSVN_ODNO") val overseasReservationOrderNumber: String? // 해외예약주문번호
)
