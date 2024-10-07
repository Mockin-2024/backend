package com.knu.mockin.model.dto.kisresponse.basicprice.searchinfo

import com.fasterxml.jackson.annotation.JsonProperty
import com.knu.mockin.model.dto.kisresponse.basicprice.conditionsearch.KISConditionSearchResponseOutputDto

data class KISSearchInfoResponseBodyDto (
        @JsonProperty("rt_cd") val successFailureStatus: String,    // 성공 실패 여부
        @JsonProperty("msg_cd") val responseCode: String,           // 응답코드
        @JsonProperty("msg1") val responseMessage: String,          // 응답메세지
        @JsonProperty("output1") val output1: KISSearchInfoResponseOutput1Dto,        // 응답상세
        // 본문에는 output1으로 나와있으나, 예시에는 output으로 나와있음
)