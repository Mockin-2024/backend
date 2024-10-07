package com.knu.mockin.model.dto.kisresponse.basicprice.pricedetail

import com.fasterxml.jackson.annotation.JsonProperty
import com.knu.mockin.model.dto.kisresponse.basicprice.contriesholiday.KISContriesHolidayResponseOutputDto

data class KISPriceDetailResponseBodyDto (
        @JsonProperty("rt_cd") val successFailureStatus: String,    // 성공 실패 여부
        @JsonProperty("msg_cd") val responseCode: String,           // 응답코드
        @JsonProperty("msg1") val responseMessage: String,          // 응답메세지
        @JsonProperty("output") val output: KISPriceDetailResponseOutputDto,        // 응답상세
)