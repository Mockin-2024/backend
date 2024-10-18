package com.knu.mockin.model.dto.kisresponse.trading

import com.fasterxml.jackson.annotation.JsonProperty

data class KISOrderReverseResponseDto(
    @JsonProperty("rt_cd") var successFailureStatus: String = "" ,  // 성공 실패 여부
    @JsonProperty("msg_cd") var responseCode: String = "" ,         // 응답코드
    @JsonProperty("msg1") var responseMessage: String = "" ,        // 응답메세지
    @JsonProperty("output") var output: Output = Output()           // 응답상세
)
data class Output(
    @JsonProperty("KRX_FWDG_ORD_ORGNO") var exchangeOrderOrgNo: String = "" , // 한국거래소전송주문조직번호
    @JsonProperty("ODNO") var orderNo: String = "" , // 주문번호
    @JsonProperty("ORD_TMD") var orderTime: String = "" // 주문시각
)