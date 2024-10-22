package com.knu.mockin.model.dto.kisresponse.basic

import com.fasterxml.jackson.annotation.JsonProperty

data class KISNewsTitleResponseDto(
    @JsonProperty("rt_cd") val successStatus: String, // 성공 실패 여부
    @JsonProperty("msg_cd") val responseCode: String, // 응답코드
    @JsonProperty("msg1") val responseMessage: String, // 응답메세지
    @JsonProperty("outblock1") val details: List<NewsDetail> // 응답상세
)

data class NewsDetail(
    @JsonProperty("info_gb") val newsCategory: String,      // 뉴스구분
    @JsonProperty("news_key") val newsKey: String,          // 뉴스키
    @JsonProperty("data_dt") val queryDate: String,         // 조회일자
    @JsonProperty("data_tm") val queryTime: String,         // 조회시간
    @JsonProperty("class_cd") val categoryCode: String,     // 중분류
    @JsonProperty("class_name") val categoryName: String,   // 중분류명
    @JsonProperty("source") val source: String,             // 자료원
    @JsonProperty("nation_cd") val countryCode: String,     // 국가코드
    @JsonProperty("exchange_cd") val exchangeCode: String,  // 거래소코드
    @JsonProperty("symb") val stockCode: String,            // 종목코드
    @JsonProperty("symb_name") val stockName: String,       // 종목명
    @JsonProperty("title") val title: String                // 제목
)