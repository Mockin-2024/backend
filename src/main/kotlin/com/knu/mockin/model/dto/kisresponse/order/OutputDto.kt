package com.knu.mockin.model.dto.kisresponse.order

import com.fasterxml.jackson.annotation.JsonProperty

data class OutputDto(
    @JsonProperty("KRX_FWDG_ORD_ORGNO") val krxForwardOrderOrgNo: String, // 한국거래소전송주문조직번호
    @JsonProperty("ODNO") val ODNO: String, // 주문번호
    @JsonProperty("ORD_TMD") val ordTmd: String // 주문시각
)