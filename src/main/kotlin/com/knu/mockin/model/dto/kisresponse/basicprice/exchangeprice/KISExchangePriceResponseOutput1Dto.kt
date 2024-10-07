package com.knu.mockin.model.dto.kisresponse.basicprice.exchangeprice

import com.fasterxml.jackson.annotation.JsonProperty

data class KISExchangePriceResponseOutput1Dto (
        @JsonProperty("ovrs_nmix_prdy_vrss") val ovrsNmixPrdyVrss: String,     // 전일 대비
        @JsonProperty("prdy_vrss_sign") val prdyVrssSign: String,              // 전일 대비 부호
        @JsonProperty("prdy_ctrt") val prdyCtrt: String,                      // 전일 대비율
        @JsonProperty("ovrs_nmix_prdy_clpr") val ovrsNmixPrdyClpr: String,    // 전일 종가
        @JsonProperty("acml_vol") val acmlVol: String,                        // 누적 거래량
        @JsonProperty("hts_kor_isnm") val htsKorIsnm: String,                 // HTS 한글 종목명
        @JsonProperty("ovrs_nmix_prpr") val ovrsNmixPrpr: String,             // 현재가
        @JsonProperty("stck_shrn_iscd") val stckShrnIscd: String,             // 단축 종목코드
        @JsonProperty("prdy_vol") val prdyVol: String,                        // 전일 거래량
        @JsonProperty("ovrs_prod_oprc") val ovrsProdOprc: String,             // 시가
        @JsonProperty("ovrs_prod_hgpr") val ovrsProdHgpr: String,             // 최고가
        @JsonProperty("ovrs_prod_lwpr") val ovrsProdLwpr: String              // 최저가
)