package com.knu.mockin.model.dto.request.quotations.basic.real

import com.fasterxml.jackson.annotation.JsonProperty
import com.knu.mockin.model.dto.kisrequest.quotations.basic.real.KISInquireAskingPriceRequestParameterDto

data class InquireAskingPriceRequestParameterDto (
    @JsonProperty("AUTH") val AUTH: String = "",
    @JsonProperty("EXCD") val EXCD: String,  // 거래소 코드
    @JsonProperty("SYMB") val SYMB: String,  // 종목 코드
)

fun InquireAskingPriceRequestParameterDto.asDomain() : KISInquireAskingPriceRequestParameterDto {
    return KISInquireAskingPriceRequestParameterDto(
        AUTH = this.AUTH,
        EXCD = this.EXCD,
        SYMB = this.SYMB
    )
}