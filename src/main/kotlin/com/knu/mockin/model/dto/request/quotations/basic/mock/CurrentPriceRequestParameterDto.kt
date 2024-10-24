package com.knu.mockin.model.dto.request.quotations.basic.mock

import com.fasterxml.jackson.annotation.JsonProperty
import com.knu.mockin.model.dto.kisrequest.quotations.basic.mock.KISCurrentPriceRequestParameterDto

data class CurrentPriceRequestParameterDto (
        @JsonProperty("AUTH") val AUTH: String = "",
        @JsonProperty("EXCD") val EXCD: String = "",  // 거래소 코드
        @JsonProperty("SYMB") val SYMB: String = "",   // 종목 코드
)

fun CurrentPriceRequestParameterDto.asDomain(): KISCurrentPriceRequestParameterDto {
        return KISCurrentPriceRequestParameterDto(
                AUTH = this.AUTH,
                EXCD = this.EXCD,
                SYMB = this.SYMB
        )
}