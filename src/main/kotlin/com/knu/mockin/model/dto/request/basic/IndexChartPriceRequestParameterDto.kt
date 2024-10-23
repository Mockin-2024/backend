package com.knu.mockin.model.dto.request.basic

data class IndexChartPriceRequestParameterDto (
        val fidCondMrktDivCode: String = "", // 조건 시장 분류 코드
        val fidInputIscd: String = "",       // 입력 종목코드
        val fidHourClsCode: String = "",     // 시간 구분 코드
        val fidPwDataIncuYn: String = "",    // 과거 데이터 포함 여부
)
