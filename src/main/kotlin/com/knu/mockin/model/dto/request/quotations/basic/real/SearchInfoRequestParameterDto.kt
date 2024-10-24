package com.knu.mockin.model.dto.request.quotations.basic.real

import com.knu.mockin.model.dto.kisrequest.quotations.basic.real.KISSearchInfoRequestParameterDto

data class SearchInfoRequestParameterDto (
    val prdtTypeCd: String = "", // 상품유형코드
    val pdno: String = "",        // 상품번호
)

fun SearchInfoRequestParameterDto.asDomain(): KISSearchInfoRequestParameterDto {
    return KISSearchInfoRequestParameterDto(
        prdtTypeCd = this.prdtTypeCd,
        pdno = this.pdno
    )
}