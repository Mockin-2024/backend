package com.knu.mockin.model.dto.kisresponse.KISCurrentPrice

import com.fasterxml.jackson.annotation.JsonProperty
import com.knu.mockin.model.dto.kisresponse.order.OutputDto

data class KISCurrentPriceResponseBodyDto(

    @JsonProperty("rt_cd") val successFailureStatus: String,    // 성공 실패 여부
    @JsonProperty("msg_cd") val responseCode: String,           // 응답코드
    @JsonProperty("msg1") val responseMessage: String,          // 응답메세지
    @JsonProperty("output") val output: KISCurrentPriceResponseOutputDto               // 응답상세
    // KISOverSeaResponseBodyDto와 output 내용이 다름
)