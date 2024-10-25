package com.knu.mockin.model.dto.request.quotations.analysis.real

import com.knu.mockin.model.dto.kisrequest.quotations.analysis.KISNewsTitleRequestParameterDto

data class NewsTitleRequestParameterDto(
    val queryDate: String = "",            // 조회일자
    val queryTime: String = "",            // 조회시간
)


fun NewsTitleRequestParameterDto.asDomain(): KISNewsTitleRequestParameterDto {
    return KISNewsTitleRequestParameterDto(
        queryDate = this.queryDate,
        queryTime = this.queryTime
    )
}