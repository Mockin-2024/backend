package com.knu.mockin.model.dto.request.basic

data class DailyChartPriceRequestParameterDto (
        val fidCondMrktDivCode: String = "", // 조건 시장 분류 코드
        val fidInputIscd: String = "", // 입력 종목코드
        val fidInputDate1: String = "", // 시작일자 (YYYYMMDD)
        val fidInputDate2: String = "", // 종료일자 (YYYYMMDD)
        val fidPeriodDivCode: String = "", // 기간 분류 코드
)
