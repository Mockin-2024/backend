package com.knu.mockin.model.dto.kisresponse.quotations.basic.mock

import com.fasterxml.jackson.annotation.JsonProperty

data class KISInquireSearchResponseDto (
        @JsonProperty("rt_cd") val successFailureStatus: String,    // 성공 실패 여부
        @JsonProperty("msg_cd") val responseCode: String,           // 응답코드
        @JsonProperty("msg1") val responseMessage: String,          // 응답메세지
        @JsonProperty("output1") val output1: InquireSearchOutput1?,        // 응답상세
        @JsonProperty("output2") val output2: List<InquireSearchOutput2>?,
)

data class InquireSearchOutput1 (
        @JsonProperty("zdiv") val zdiv: String,                    // 소수점 자리수
        @JsonProperty("stat") val stat: String,                     // 거래상태정보
        @JsonProperty("crec") val crec: String,                     // 현재조회종목수
        @JsonProperty("trec") val trec: String,                     // 전체조회종목수
        @JsonProperty("nrec") val nrec: String                      // Record Count`
)

data class InquireSearchOutput2 (
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
        @JsonProperty("e_ordyn") val eordyn: String                 // 매매가능 (가능: O)
)