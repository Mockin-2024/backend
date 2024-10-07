package com.knu.mockin.model.dto.kisresponse.basicprice.exchangeprice

import com.fasterxml.jackson.annotation.JsonProperty

data class KISExchangePriceResponseBodyDto (
        @JsonProperty("rt_cd") val successFailureStatus: String,    // 성공 실패 여부
        @JsonProperty("msg_cd") val responseCode: String,           // 응답코드
        @JsonProperty("msg1") val responseMessage: String,          // 응답메세지
        @JsonProperty("output1") val output1: KISExchangePriceResponseOutput1Dto,               // 응답상세
        @JsonProperty("output2") val output2: KISExchangePriceResponseOutput2Dto
)