package com.knu.mockin.model.dto.kisresponse.basicprice.timeindexchartprice

import com.fasterxml.jackson.annotation.JsonProperty

data class KISTimeIndexChartPriceResponseOutput1Dto (
        @JsonProperty("ovrs_nmix_prdy_vrss") val ovrsNmixPrdyVrss: String,       // 해외 지수 전일 대비
        @JsonProperty("prdy_vrss_sign") val prdyVrssSign: String,                // 전일 대비 부호
        @JsonProperty("hts_kor_isnm") val htsKorIsnm: String,                    // HTS 한글 종목명
        @JsonProperty("prdy_ctrt") val prdyCtrt: String,                        // 전일 대비율
        @JsonProperty("ovrs_nmix_prdy_clpr") val ovrsNmixPrdyClpr: String,      // 해외 지수 전일 종가
        @JsonProperty("acml_vol") val acmlVol: String,                          // 누적 거래량
        @JsonProperty("ovrs_nmix_prpr") val ovrsNmixPrpr: String,                // 해외 지수 현재가
        @JsonProperty("stck_shrn_iscd") val stckShrnIscd: String,                // 주식 단축 종목코드
        @JsonProperty("ovrs_prod_oprc") val ovrsProdOprc: String,                // 해외 상품 시가2
        @JsonProperty("ovrs_prod_hgpr") val ovrsProdHgpr: String,                // 해외 상품 최고가
        @JsonProperty("ovrs_prod_lwpr") val ovrsProdLwpr: String                 // 해외 상품 최저가
)