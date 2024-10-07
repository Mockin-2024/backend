package com.knu.mockin.model.dto.kisresponse.basicprice.conditionsearch

import com.fasterxml.jackson.annotation.JsonProperty

data class KISConditionSearchResponseOutput1Dto (
        @JsonProperty("rsym") val rsym: String,                     // 실시간조회심볼
        @JsonProperty("excd") val excd: String,                     // 거래소코드
        @JsonProperty("name") val name: String,                     // 종목명
        @JsonProperty("symb") val symb: String,                     // 종목코드
        @JsonProperty("last") val last: String,                     // 현재가
        @JsonProperty("shar") val shar: String,                     // 발행주식수 (단위: 천)
        @JsonProperty("valx") val valx: String,                     // 시가총액 (단위: 천)
        @JsonProperty("plow") val plow: String,                     // 저가
        @JsonProperty("phigh") val phigh: String,                   // 고가
        @JsonProperty("popen") val popen: String,                   // 시가
        @JsonProperty("tvol") val tvol: String,                     // 거래량 (단위: 주)
        @JsonProperty("rate") val rate: String,                     // 등락율 (%)
        @JsonProperty("diff") val diff: String,                     // 대비
        @JsonProperty("sign") val sign: String,                     // 기호
        @JsonProperty("avol") val avol: String,                     // 거래대금 (단위: 천)
        @JsonProperty("eps") val eps: String,                       // EPS
        @JsonProperty("per") val per: String,                       // PER
        @JsonProperty("rank") val rank: String,                     // 순위
        @JsonProperty("ename") val ename: String,                   // 영문종목명
        @JsonProperty("e_ordyn") val eOrdyn: String                 // 매매가능 (가능: O)
)