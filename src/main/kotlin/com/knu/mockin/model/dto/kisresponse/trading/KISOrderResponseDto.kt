package com.knu.mockin.model.dto.kisresponse.trading

import com.fasterxml.jackson.annotation.JsonProperty

data class KISOrderResponseDto(
    @JsonProperty("rt_cd") val successFailureStatus: String,    // 성공 실패 여부
    @JsonProperty("msg_cd") val responseCode: String,           // 응답코드
    @JsonProperty("msg1") val responseMessage: String,          // 응답메세지
    @JsonProperty("output") val output: OrderOutputDto          // 응답상세
)
data class OrderOutputDto(
    @JsonProperty("KRX_FWDG_ORD_ORGNO") val krxForwardOrderOrgNo: String,   // 한국거래소전송주문조직번호
    @JsonProperty("ODNO") val orderNumber: String,                          // 주문번호
    @JsonProperty("ORD_TMD") val orderTime: String                          // 주문시각
)