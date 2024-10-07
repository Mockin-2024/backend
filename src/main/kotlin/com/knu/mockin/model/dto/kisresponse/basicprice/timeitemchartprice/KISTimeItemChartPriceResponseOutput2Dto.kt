package com.knu.mockin.model.dto.kisresponse.basicprice.timeitemchartprice

import com.fasterxml.jackson.annotation.JsonProperty

data class KISTimeItemChartPriceResponseOutput2Dto (
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