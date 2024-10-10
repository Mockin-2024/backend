package com.knu.mockin.model.dto.kisresponse.basic

import com.fasterxml.jackson.annotation.JsonProperty

data class KISTermPriceResponseDto (
        @JsonProperty("rt_cd") val successFailureStatus: String,    // 성공 실패 여부
        @JsonProperty("msg_cd") val responseCode: String,           // 응답코드
        @JsonProperty("msg1") val responseMessage: String,          // 응답메세지
        @JsonProperty("output1") val output1: TermPriceOutput1?,               // 응답상세
        @JsonProperty("output2") val output2: List<TermPriceOutput2>?,
)

data class TermPriceOutput1 (
        @JsonProperty("rsym") val rsym: String,          // 실시간 조회 종목 코드
        @JsonProperty("zdiv") val zdiv: String,             // 소수점 자리수
        @JsonProperty("nrec") val nrec: String           // 전일 종가
)

data class TermPriceOutput2 (
        @JsonProperty("xymd") val xymd: String,                // 일자 (YYYYMMDD)
        @JsonProperty("clos") val clos: String,                // 해당 일자의 종가
        @JsonProperty("sign") val sign: String,                // 대비 기호
        @JsonProperty("diff") val diff: String,                // 대비
        @JsonProperty("rate") val rate: String,                // 등락율
        @JsonProperty("open") val open: String,                // 해당일 최초 거래가격
        @JsonProperty("high") val high: String,                // 해당일 가장 높은 거래가격
        @JsonProperty("low") val low: String,                  // 해당일 가장 낮은 거래가격
        @JsonProperty("tvol") val tvol: String,                // 해당일 거래량
        @JsonProperty("tamt") val tamt: String,                // 해당일 거래대금
        @JsonProperty("pbid") val pbid: String,                // 마지막 체결 시 매수호가
        @JsonProperty("vbid") val vbid: String,                // 매수호가 잔량
        @JsonProperty("pask") val pask: String,                // 마지막 체결 시 매도호가
        @JsonProperty("vask") val vask: String                 // 매도호가 잔량
)