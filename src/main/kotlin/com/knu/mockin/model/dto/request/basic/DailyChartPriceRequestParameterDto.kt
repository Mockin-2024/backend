package com.knu.mockin.model.dto.request.basic

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonProperty

data class DailyChartPriceRequestParameterDto (
        @JsonProperty("FID_COND_MRKT_DIV_CODE")
        @JsonAlias("fid_cond_mrkt_div_code")
        val fidCondMrktDivCode: String, // 조건 시장 분류 코드

        @JsonProperty("FID_INPUT_ISCD")
        @JsonAlias("fid_input_iscd")
        val fidInputIscd: String, // 입력 종목코드

        @JsonProperty("FID_INPUT_DATE_1")
        @JsonAlias("fid_input_date_1")
        val fidInputDate1: String, // 시작일자 (YYYYMMDD)

        @JsonProperty("FID_INPUT_DATE_2")
        @JsonAlias("fid_input_date_2")
        val fidInputDate2: String, // 종료일자 (YYYYMMDD)

        @JsonProperty("FID_PERIOD_DIV_CODE")
        @JsonAlias("fid_period_div_code")
        val fidPeriodDivCode: String, // 기간 분류 코드

        val email: String // 이메일
)