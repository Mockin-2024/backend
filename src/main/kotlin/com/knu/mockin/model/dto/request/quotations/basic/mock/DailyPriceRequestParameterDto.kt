package com.knu.mockin.model.dto.request.quotations.basic.mock

import com.fasterxml.jackson.annotation.JsonProperty
import com.knu.mockin.model.dto.kisrequest.quotations.basic.mock.KISDailyPriceRequestParameterDto

data class DailyPriceRequestParameterDto (
        @JsonProperty("AUTH") val AUTH: String = "",
        @JsonProperty("EXCD") val EXCD: String = "",  // 거래소 코드
        @JsonProperty("SYMB") val SYMB: String = "",  // 종목 코드
        @JsonProperty("GUBN") val GUBN: String = "",  // 일(0)/주(1)/월(2) 구분
        @JsonProperty("BYMD") val BYMD: String = "",  // 조회기준일자(YYYYMMDD), 공란 시 현재날짜
        @JsonProperty("MODP") val MODP: String = "",  // 수정주가반영여부(0:미반영, 1:반영)
        @JsonProperty("KEYB") val KEYB: String = "",   // NEXT KEY BUFF
)

fun DailyPriceRequestParameterDto.asDomain(): KISDailyPriceRequestParameterDto {
        return KISDailyPriceRequestParameterDto(
                AUTH = this.AUTH,
                EXCD = this.EXCD,
                SYMB = this.SYMB,
                GUBN = this.GUBN,
                BYMD = this.BYMD,
                MODP = this.MODP,
                KEYB = this.KEYB
        )
}