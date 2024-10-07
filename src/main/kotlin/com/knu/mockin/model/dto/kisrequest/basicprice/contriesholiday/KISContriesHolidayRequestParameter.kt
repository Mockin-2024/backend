package com.knu.mockin.model.dto.kisrequest.basicprice.contriesholiday

import com.fasterxml.jackson.annotation.JsonProperty

data class KISContriesHolidayRequestParameter (
        @JsonProperty("TRAD_DT") val tradDt: String,                       // 기준일자
        @JsonProperty("CTX_AREA_NK") val ctxAreaNk: String,                       // 연속조회키
        @JsonProperty("CTX_AREA_FK") val ctxAreaFk: String,     // 연속조회검색조건
)