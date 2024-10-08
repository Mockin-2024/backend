package com.knu.mockin.model.dto.kisresponse

import com.fasterxml.jackson.annotation.JsonProperty

data class TestResponseBodyDto (
        @JsonProperty("output") val output: TestResponseOutputDto,               // 응답상세
        @JsonProperty("rt_cd") val successFailureStatus: String,    // 성공 실패 여부
        @JsonProperty("msg_cd") val responseCode: String,           // 응답코드
        @JsonProperty("msg1") val responseMessage: String          // 응답메세지
)