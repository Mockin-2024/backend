package com.knu.mockin.model.dto.request.quotations.basic.real

import com.fasterxml.jackson.annotation.JsonProperty
import com.knu.mockin.model.dto.kisrequest.quotations.basic.real.KISPriceDetailRequestParameterDto

data class PriceDetailRequestParameterDto (
        @JsonProperty("AUTH") val AUTH: String = "",
        @JsonProperty("EXCD") val EXCD: String = "",  // 거래소 코드
        @JsonProperty("SYMB") val SYMB: String = "",   // 종목 코드
)

fun PriceDetailRequestParameterDto.asDomain(): KISPriceDetailRequestParameterDto {
        return KISPriceDetailRequestParameterDto(
                AUTH = this.AUTH,
                EXCD = this.EXCD,
                SYMB = this.SYMB
        )
}