package com.knu.mockin.model.dto.kisresponse.basicprice.real.timeindexchartprice

import com.fasterxml.jackson.annotation.JsonProperty

data class KISTimeIndexChartPriceResponseDto (
        @JsonProperty("rt_cd") val successFailureStatus: String,    // 성공 실패 여부
        @JsonProperty("msg_cd") val responseCode: String,           // 응답코드
        @JsonProperty("msg1") val responseMessage: String,          // 응답메세지
        @JsonProperty("output1") val output1: Output1?,        // 응답상세
        @JsonProperty("output2") val output2: Output2?,
)

data class Output1 (
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

data class Output2 (
        @JsonProperty("stck_bsop_date") val stckBsopDate: String,    // 주식 영업 일자
        @JsonProperty("stck_cntg_hour") val stckCntgHour: String,    // 주식 체결 시간
        @JsonProperty("optn_prpr") val optnPrpr: String,            // 옵션 현재가
        @JsonProperty("optn_oprc") val optnOprc: String,            // 옵션 시가2
        @JsonProperty("optn_hgpr") val optnHgpr: String,            // 옵션 최고가
        @JsonProperty("optn_lwpr") val optnLwpr: String,            // 옵션 최저가
        @JsonProperty("cntg_vol") val cntgVol: String               // 체결 거래량
)