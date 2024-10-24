package com.knu.mockin.model.dto.request.quotations.analysis.real

data class NewsTitleRequestParameterDto(
    val queryDate: String = "",            // 조회일자
    val queryTime: String = "",            // 조회시간
)
