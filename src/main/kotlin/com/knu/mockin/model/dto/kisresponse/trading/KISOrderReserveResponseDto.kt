package com.knu.mockin.model.dto.kisresponse.trading

import com.fasterxml.jackson.annotation.JsonProperty

data class KISOrderReserveResponseDto(
    @JsonProperty("rt_cd") val successFailureStatus: String, // 성공 실패 여부
    @JsonProperty("msg_cd") val responseCode: String, // 응답코드
    @JsonProperty("msg1") val responseMessage: String, // 응답메세지
    @JsonProperty("output") val output: OutputDetails // 응답상세
)

data class OutputDetails(
    @JsonProperty("ODNO") val koreaExchangeOrderOrgNumber: String, // 한국거래소전송주문조직번호
    @JsonProperty("RSVN_ORD_RCIT_DT") val reservationOrderReceiptDate: String = "", // 예약주문접수일자
    @JsonProperty("OVRS_RSVN_ODNO") val overseasReservationOrderNumber: String = "" // 해외예약주문번호
)