package com.knu.mockin.model.dto.kisresponse.basicprice.timeindexchartprice

import com.fasterxml.jackson.annotation.JsonProperty

data class KISTimeIndexChartPriceResponseOutput2Dto (
        @JsonProperty("stck_bsop_date") val stckBsopDate: String,    // 주식 영업 일자
        @JsonProperty("stck_cntg_hour") val stckCntgHour: String,    // 주식 체결 시간
        @JsonProperty("optn_prpr") val optnPrpr: String,            // 옵션 현재가
        @JsonProperty("optn_oprc") val optnOprc: String,            // 옵션 시가2
        @JsonProperty("optn_hgpr") val optnHgpr: String,            // 옵션 최고가
        @JsonProperty("optn_lwpr") val optnLwpr: String,            // 옵션 최저가
        @JsonProperty("cntg_vol") val cntgVol: String               // 체결 거래량
)