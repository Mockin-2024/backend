package com.knu.mockin.model.dto.kisresponse.basicprice.termprice

import com.fasterxml.jackson.annotation.JsonProperty

data class KISTermPriceResponseOutput1Dto (
        @JsonProperty("rsym") val rsym: String,          // 실시간 조회 종목 코드
        @JsonProperty("zdiv") val zdiv: String,             // 소수점 자리수
        @JsonProperty("nrec") val nrec: String           // 전일 종가
)