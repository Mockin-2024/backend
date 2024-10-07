package com.knu.mockin.model.dto.kisresponse.basicprice.termprice

import com.fasterxml.jackson.annotation.JsonProperty

data class KISTermPriceResponseOutput2Dto (
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