package com.knu.mockin.model.dto.kisresponse.order

import com.fasterxml.jackson.annotation.JsonProperty

data class KISOverSeaResponseBodyDto(
    @JsonProperty("rt_cd") val successFailureStatus: String,    // 성공 실패 여부
    @JsonProperty("msg_cd") val responseCode: String,           // 응답코드
    @JsonProperty("msg1") val responseMessage: String,          // 응답메세지
    @JsonProperty("output") val output: OutputDto               // 응답상세
)
