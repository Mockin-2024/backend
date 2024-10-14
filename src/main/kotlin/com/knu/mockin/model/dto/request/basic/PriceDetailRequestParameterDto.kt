package com.knu.mockin.model.dto.request.basic

import com.fasterxml.jackson.annotation.JsonProperty

data class PriceDetailRequestParameterDto (
        @JsonProperty("AUTH") val AUTH: String = "",
        @JsonProperty("EXCD") val EXCD: String,  // 거래소 코드
        @JsonProperty("SYMB") val SYMB: String,   // 종목 코드
        val email : String
)