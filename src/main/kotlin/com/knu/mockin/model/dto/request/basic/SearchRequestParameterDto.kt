package com.knu.mockin.model.dto.request.basic

import com.fasterxml.jackson.annotation.JsonProperty

data class SearchRequestParameterDto (
        @JsonProperty("AUTH") val AUTH: String = "",                       // 사용자권한정보
        @JsonProperty("EXCD") val EXCD: String = "",                       // 거래소코드
        @JsonProperty("CO_YN_PRICECUR") val coYnPricecur: String = "",     // 현재가선택조건
        @JsonProperty("CO_ST_PRICECUR") val coStPricecur: String = "",     // 현재가시작범위가
        @JsonProperty("CO_EN_PRICECUR") val coEnPricecur: String = "",     // 현재가끝범위가
        @JsonProperty("CO_YN_RATE") val coYnRate: String = "",             // 등락율선택조건
        @JsonProperty("CO_ST_RATE") val coStRate: String = "",             // 등락율시작율
        @JsonProperty("CO_EN_RATE") val coEnRate: String = "",             // 등락율끝율
        @JsonProperty("CO_YN_VALX") val coYnValx: String = "",             // 시가총액선택조건
        @JsonProperty("CO_ST_VALX") val coStValx: String = "",             // 시가총액시작액
        @JsonProperty("CO_EN_VALX") val coEnValx: String = "",             // 시가총액끝액
        @JsonProperty("CO_YN_SHAR") val coYnShar: String = "",             // 발행주식수선택조건
        @JsonProperty("CO_ST_SHAR") val coStShar: String = "",             // 발행주식시작수
        @JsonProperty("CO_EN_SHAR") val coEnShar: String = "",             // 발행주식끝수
        @JsonProperty("CO_YN_VOLUME") val coYnVolume: String = "",         // 거래량선택조건
        @JsonProperty("CO_ST_VOLUME") val coStVolume: String = "",         // 거래량시작량
        @JsonProperty("CO_EN_VOLUME") val coEnVolume: String = "",         // 거래량끝량
        @JsonProperty("CO_YN_AMT") val coYnAmt: String = "",               // 거래대금선택조건
        @JsonProperty("CO_ST_AMT") val coStAmt: String = "",               // 거래대금시작금
        @JsonProperty("CO_EN_AMT") val coEnAmt: String = "",               // 거래대금끝금
        @JsonProperty("CO_YN_EPS") val coYnEps: String = "",               // EPS선택조건
        @JsonProperty("CO_ST_EPS") val coStEps: String = "",               // EPS시작
        @JsonProperty("CO_EN_EPS") val coEnEps: String = "",               // EPS끝
        @JsonProperty("CO_YN_PER") val coYnPer: String = "",               // PER선택조건
        @JsonProperty("CO_ST_PER") val coStPer: String = "",               // PER시작
        @JsonProperty("CO_EN_PER") val coEnPer: String = "",               // PER끝
        @JsonProperty("KEYB") val KEYB: String = "",                        // NEXT KEY BUFF
        @JsonProperty("email") val email : String = ""
)