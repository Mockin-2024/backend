package com.knu.mockin.model.dto.request.quotations.basic.mock

import com.fasterxml.jackson.annotation.JsonProperty
import com.knu.mockin.model.dto.kisrequest.quotations.basic.mock.KISPriceRequestParameterDto

data class PriceRequestParameterDto (
        @JsonProperty("AUTH") val AUTH: String = "",
        @JsonProperty("EXCD") val EXCD: String = "",  // 거래소 코드
        @JsonProperty("SYMB") val SYMB: String = "",   // 종목 코드
)

fun PriceRequestParameterDto.asDomain(): KISPriceRequestParameterDto {
        return KISPriceRequestParameterDto(
                AUTH = this.AUTH,
                EXCD = this.EXCD,
                SYMB = this.SYMB
        )
}