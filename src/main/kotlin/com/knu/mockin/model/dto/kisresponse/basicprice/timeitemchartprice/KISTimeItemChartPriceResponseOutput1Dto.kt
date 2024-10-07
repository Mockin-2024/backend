package com.knu.mockin.model.dto.kisresponse.basicprice.timeitemchartprice

import com.fasterxml.jackson.annotation.JsonProperty

data class KISTimeItemChartPriceResponseOutput1Dto (
        @JsonProperty("rsym") val rsym: String,              // 실시간종목코드
        @JsonProperty("zdiv") val zdiv: String,              // 소수점자리수
        @JsonProperty("stim") val stim: String,              // 장시작현지시간
        @JsonProperty("etim") val etim: String,              // 장종료현지시간
        @JsonProperty("sktm") val sktm: String,              // 장시작한국시간
        @JsonProperty("ektm") val ektm: String,              // 장종료한국시간
        @JsonProperty("next") val next: String,              // 다음가능여부
        @JsonProperty("more") val more: String,              // 추가데이타여부
        @JsonProperty("nrec") val nrec: String               // 레코드갯수
)