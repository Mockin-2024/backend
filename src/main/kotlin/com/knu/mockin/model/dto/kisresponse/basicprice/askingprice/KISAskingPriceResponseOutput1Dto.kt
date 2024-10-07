package com.knu.mockin.model.dto.kisresponse.basicprice.askingprice

import com.fasterxml.jackson.annotation.JsonProperty

data class KISAskingPriceResponseOutput1Dto (
        @JsonProperty("rsym") val rsym: String,              // 실시간조회종목코드
        @JsonProperty("zdiv") val zdiv: String,              // 소수점자리수
        @JsonProperty("curr") val curr: String,              // 통화
        @JsonProperty("base") val base: String,              // 전일종가
        @JsonProperty("open") val open: String,              // 시가
        @JsonProperty("high") val high: String,              // 고가
        @JsonProperty("low") val low: String,                // 저가
        @JsonProperty("last") val last: String,              // 현재가
        @JsonProperty("dymd") val dymd: String,              // 호가일자
        @JsonProperty("dhms") val dhms: String,              // 호가시간
        @JsonProperty("bvol") val bvol: String,              // 매수호가총잔량
        @JsonProperty("avol") val avol: String,              // 매도호가총잔량
        @JsonProperty("bdvl") val bdvl: String,              // 매수호가총잔량대비
        @JsonProperty("advl") val advl: String,              // 매도호가총잔량대비
        @JsonProperty("code") val code: String,              // 종목코드
        @JsonProperty("ropen") val ropen: String,            // 시가율
        @JsonProperty("rhigh") val rhigh: String,            // 고가율
        @JsonProperty("rlow") val rlow: String,              // 저가율
        @JsonProperty("rclose") val rclose: String           // 현재가율
)