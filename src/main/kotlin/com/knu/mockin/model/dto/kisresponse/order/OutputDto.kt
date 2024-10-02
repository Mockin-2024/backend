package com.knu.mockin.model.dto.kisresponse.order

import com.fasterxml.jackson.annotation.JsonProperty

data class OutputDto(
    @JsonProperty("KRX_FWDG_ORD_ORGNO") val krxForwardOrderOrgNo: String, // 한국거래소전송주문조직번호
    @JsonProperty("ODNO") val orderNumber: String, // 주문번호
    @JsonProperty("ORD_TMD") val orderTime: String // 주문시각
)