package com.knu.mockin.model.dto.kisrequest.basic.mock

import com.fasterxml.jackson.annotation.JsonProperty

data class KISDailyChartPriceRequestParameterDto (
        @JsonProperty("FID_COND_MRKT_DIV_CODE") val fidCondMrktDivCode: String, // 조건 시장 분류 코드
        @JsonProperty("FID_INPUT_ISCD") val fidInputIscd: String,               // 입력 종목코드
        @JsonProperty("FID_INPUT_DATE_1") val fidInputDate1: String,           // 시작일자 (YYYYMMDD)
        @JsonProperty("FID_INPUT_DATE_2") val fidInputDate2: String,           // 종료일자 (YYYYMMDD)
        @JsonProperty("FID_PERIOD_DIV_CODE") val fidPeriodDivCode: String       // 기간 분류 코드
)