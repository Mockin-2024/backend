package com.knu.mockin.model.dto.kisresponse.basicprice.conditionsearch

import com.fasterxml.jackson.annotation.JsonProperty

data class KISConditionSearchResponseBodyDto  (
        @JsonProperty("rt_cd") val successFailureStatus: String,    // 성공 실패 여부
        @JsonProperty("msg_cd") val responseCode: String,           // 응답코드
        @JsonProperty("msg1") val responseMessage: String,          // 응답메세지
        @JsonProperty("output") val output: KISConditionSearchResponseOutputDto,        // 응답상세
        @JsonProperty("output1") val output1: KISConditionSearchResponseOutput1Dto
)