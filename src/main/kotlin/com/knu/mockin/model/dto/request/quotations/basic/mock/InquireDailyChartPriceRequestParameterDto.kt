package com.knu.mockin.model.dto.request.quotations.basic.mock

import com.knu.mockin.model.dto.kisrequest.quotations.basic.mock.KISInquireDailyChartPriceRequestParameterDto

data class InquireDailyChartPriceRequestParameterDto (
        val fidCondMrktDivCode: String = "", // 조건 시장 분류 코드
        val fidInputIscd: String = "", // 입력 종목코드
        val fidInputDate1: String = "", // 시작일자 (YYYYMMDD)
        val fidInputDate2: String = "", // 종료일자 (YYYYMMDD)
        val fidPeriodDivCode: String = "", // 기간 분류 코드
)

fun InquireDailyChartPriceRequestParameterDto.asDomain(): KISInquireDailyChartPriceRequestParameterDto {
        return KISInquireDailyChartPriceRequestParameterDto(
                fidCondMrktDivCode = this.fidCondMrktDivCode,
                fidInputIscd = this.fidInputIscd,
                fidInputDate1 = this.fidInputDate1,
                fidInputDate2 = this.fidInputDate2,
                fidPeriodDivCode = this.fidPeriodDivCode
        )
}