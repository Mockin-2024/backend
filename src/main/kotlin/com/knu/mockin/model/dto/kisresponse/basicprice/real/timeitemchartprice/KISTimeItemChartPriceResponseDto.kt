package com.knu.mockin.model.dto.kisresponse.basicprice.real.timeitemchartprice

import com.fasterxml.jackson.annotation.JsonProperty

data class KISTimeItemChartPriceResponseDto (
        @JsonProperty("rt_cd") val successFailureStatus: String,    // 성공 실패 여부
        @JsonProperty("msg_cd") val responseCode: String,           // 응답코드
        @JsonProperty("msg1") val responseMessage: String,          // 응답메세지
        @JsonProperty("output1") val output1: Output1?,        // 응답상세
        @JsonProperty("output2") val output2: Output2?,
)

data class Output1 (
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

data class Output2 (
        @JsonProperty("tymd") val tymd: String,              // 현지영업일자
        @JsonProperty("xymd") val xymd: String,              // 현지기준일자
        @JsonProperty("xhms") val xhms: String,              // 현지기준시간
        @JsonProperty("kymd") val kymd: String,              // 한국기준일자
        @JsonProperty("khms") val khms: String,              // 한국기준시간
        @JsonProperty("open") val open: String,              // 시가
        @JsonProperty("high") val high: String,              // 고가
        @JsonProperty("low") val low: String,                // 저가
        @JsonProperty("last") val last: String,              // 종가
        @JsonProperty("evol") val evol: String,              // 체결량
        @JsonProperty("eamt") val eamt: String               // 체결대금
)