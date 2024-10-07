package com.knu.mockin.model.dto.kisrequest.basicprice.timeindexchartprice

data class KISTimeIndexChartPriceRequestParameter (
        @JsonProperty("FID_COND_MRKT_DIV_CODE") val fidCondMrktDivCode: String, // 조건 시장 분류 코드
        @JsonProperty("FID_INPUT_ISCD") val fidInputIscd: String,                // 입력 종목코드
        @JsonProperty("FID_HOUR_CLS_CODE") val fidHourClsCode: String,          // 시간 구분 코드
        @JsonProperty("FID_PW_DATA_INCU_YN") val fidPwDataIncuYn: String        // 과거 데이터 포함 여부
)