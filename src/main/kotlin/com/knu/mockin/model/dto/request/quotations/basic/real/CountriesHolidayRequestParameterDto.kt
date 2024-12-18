package com.knu.mockin.model.dto.request.quotations.basic.real

import com.knu.mockin.model.dto.kisrequest.quotations.basic.real.KISCountriesHolidayRequestParameterDto

data class CountriesHolidayRequestParameterDto (
        val tradDt: String = "",                       // 기준일자
        val ctxAreaNk: String = "",                       // 연속조회키
        val ctxAreaFk: String = "",     // 연속조회검색조건
)

fun CountriesHolidayRequestParameterDto.asDomain(): KISCountriesHolidayRequestParameterDto {
        return KISCountriesHolidayRequestParameterDto(
                tradDt = this.tradDt,
                ctxAreaNk = this.ctxAreaNk,
                ctxAreaFk = this.ctxAreaFk
        )
}