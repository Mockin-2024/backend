package com.knu.mockin.model.dto.kisresponse.KISCurrentPrice

import com.fasterxml.jackson.annotation.JsonProperty

data class KISCurrentPriceResponseOutputDto(
        @JsonProperty("rsym") val rsym: String,          // 실시간 조회 종목 코드
        @JsonProperty("zdiv") val zdiv: Int,             // 소수점 자리수
        @JsonProperty("base") val base: String,          // 전일 종가
        @JsonProperty("pvol") val pvol: String,          // 전일 거래량
        @JsonProperty("last") val last: String,          // 현재가
        @JsonProperty("sign") val sign: Int,             // 대비 기호
        @JsonProperty("diff") val diff: String,          // 대비
        @JsonProperty("rate") val rate: String,          // 등락율
        @JsonProperty("tvol") val tvol: String,          // 거래량
        @JsonProperty("tamt") val tamt: String,          // 거래대금
        @JsonProperty("ordy") val ordy: String           // 매수 가능 여부
)