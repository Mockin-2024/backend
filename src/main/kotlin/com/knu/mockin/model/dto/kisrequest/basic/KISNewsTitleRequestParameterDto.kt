package com.knu.mockin.model.dto.kisrequest.basic

import com.fasterxml.jackson.annotation.JsonProperty

data class KISNewsTitleRequestParameterDto(
    @JsonProperty("INFO_GB") val newsCategory: String = "",         // 뉴스구분
    @JsonProperty("CLASS_CD") val categoryCode: String = "",        // 중분류
    @JsonProperty("NATION_CD") val countryCode: String = "",        // 국가코드
    @JsonProperty("EXCHANGE_CD") val exchangeCode: String = "",     // 거래소코드
    @JsonProperty("SYMB") val stockCode: String = "",               // 종목코드
    @JsonProperty("DATA_DT") val queryDate: String = "",            // 조회일자
    @JsonProperty("DATA_TM") val queryTime: String = "",            // 조회시간
    @JsonProperty("CTS") val nextKey: String = ""                   // 다음키
)
