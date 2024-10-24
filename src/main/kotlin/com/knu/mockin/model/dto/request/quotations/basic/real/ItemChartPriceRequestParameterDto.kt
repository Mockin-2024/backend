package com.knu.mockin.model.dto.request.quotations.basic.real

import com.fasterxml.jackson.annotation.JsonProperty
import com.knu.mockin.model.dto.kisrequest.quotations.basic.real.KISItemChartPriceRequestParameterDto

data class ItemChartPriceRequestParameterDto (
        @JsonProperty("AUTH") val AUTH: String = "",              // 사용자권한정보
        @JsonProperty("EXCD") val EXCD: String = "",              // 거래소코드
        @JsonProperty("SYMB") val SYMB: String = "",              // 종목코드
        @JsonProperty("NMIN") val NMIN: String = "",              // 분갭
        @JsonProperty("PINC") val PINC: String = "",              // 전일포함여부
        @JsonProperty("NEXT") val NEXT: String = "",              // 다음여부
        @JsonProperty("NREC") val NREC: String = "",              // 요청갯수
        @JsonProperty("FILL") val FILL: String = "",              // 미체결채움구분
        @JsonProperty("KEYB") val KEYB: String = "",              // NEXT KEY BUFF
)

fun ItemChartPriceRequestParameterDto.asDomain(): KISItemChartPriceRequestParameterDto {
        return KISItemChartPriceRequestParameterDto(
                AUTH = this.AUTH,
                EXCD = this.EXCD,
                SYMB = this.SYMB,
                NMIN = this.NMIN,
                PINC = this.PINC,
                NEXT = this.NEXT,
                NREC = this.NREC,
                FILL = this.FILL,
                KEYB = this.KEYB
        )
}