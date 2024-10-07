package com.knu.mockin.model.dto.kisresponse.basicprice.exchangeprice

import com.fasterxml.jackson.annotation.JsonProperty

data class KISExchangePriceResponseOutput2Dto (
        @JsonProperty("stck_bsop_date") val stckBsopDate: String,      // 영업 일자
        @JsonProperty("ovrs_nmix_prpr") val ovrsNmixPrpr: String,      // 현재가
        @JsonProperty("ovrs_nmix_oprc") val ovrsNmixOprc: String,      // 시가
        @JsonProperty("ovrs_nmix_hgpr") val ovrsNmixHgpr: String,      // 최고가
        @JsonProperty("ovrs_nmix_lwpr") val ovrsNmixLwpr: String,      // 최저가
        @JsonProperty("acml_vol") val acmlVol: String,                // 누적 거래량
        @JsonProperty("mod_yn") val modYn: String                      // 변경 여부
)