package com.knu.mockin.model.dto.request.basic

data class CountriesHolidayRequestParameterDto (
        val tradDt: String = "",                       // 기준일자
        val ctxAreaNk: String = "",                       // 연속조회키
        val ctxAreaFk: String = "",     // 연속조회검색조건
        val email : String = ""
)