package com.knu.mockin.model.dto.request.quotations.basic.mock

import com.fasterxml.jackson.annotation.JsonProperty
import com.knu.mockin.model.dto.kisrequest.quotations.basic.mock.KISSearchRequestParameterDto

data class SearchRequestParameterDto(
        @JsonProperty("AUTH") val AUTH: String = "",                       // 사용자권한정보
        @JsonProperty("EXCD") val EXCD: String = "",                       // 거래소코드
        @JsonProperty("coYnPricecur") val coYnPricecur: String = "",     // 현재가선택조건
        @JsonProperty("coStPricecur") val coStPricecur: String = "",     // 현재가시작범위가
        @JsonProperty("coEnPricecur") val coEnPricecur: String = "",     // 현재가끝범위가
        @JsonProperty("coYnRate") val coYnRate: String = "",             // 등락율선택조건
        @JsonProperty("coStRate") val coStRate: String = "",             // 등락율시작율
        @JsonProperty("coEnRate") val coEnRate: String = "",             // 등락율끝율
        @JsonProperty("coYnValx") val coYnValx: String = "",             // 시가총액선택조건
        @JsonProperty("coStValx") val coStValx: String = "",             // 시가총액시작액
        @JsonProperty("coEnValx") val coEnValx: String = "",             // 시가총액끝액
        @JsonProperty("coYnShar") val coYnShar: String = "",             // 발행주식수선택조건
        @JsonProperty("coStShar") val coStShar: String = "",             // 발행주식시작수
        @JsonProperty("coEnShar") val coEnShar: String = "",             // 발행주식끝수
        @JsonProperty("coYnVolume") val coYnVolume: String = "",         // 거래량선택조건
        @JsonProperty("coStVolume") val coStVolume: String = "",         // 거래량시작량
        @JsonProperty("coEnVolume") val coEnVolume: String = "",         // 거래량끝량
        @JsonProperty("coYnAmt") val coYnAmt: String = "",               // 거래대금선택조건
        @JsonProperty("coStAmt") val coStAmt: String = "",               // 거래대금시작금
        @JsonProperty("coEnAmt") val coEnAmt: String = "",               // 거래대금끝금
        @JsonProperty("coYnEps") val coYnEps: String = "",               // EPS선택조건
        @JsonProperty("coStEps") val coStEps: String = "",               // EPS시작
        @JsonProperty("coEnEps") val coEnEps: String = "",               // EPS끝
        @JsonProperty("coYnPer") val coYnPer: String = "",               // PER선택조건
        @JsonProperty("coStPer") val coStPer: String = "",               // PER시작
        @JsonProperty("coEnPer") val coEnPer: String = "",               // PER끝
        @JsonProperty("KEYB") val KEYB: String = "",                     // NEXT KEY BUFF
)

fun SearchRequestParameterDto.asDomain(): KISSearchRequestParameterDto {
        return KISSearchRequestParameterDto(
                AUTH = this.AUTH,
                EXCD = this.EXCD,
                coYnPricecur = this.coYnPricecur,
                coStPricecur = this.coStPricecur,
                coEnPricecur = this.coEnPricecur,
                coYnRate = this.coYnRate,
                coStRate = this.coStRate,
                coEnRate = this.coEnRate,
                coYnValx = this.coYnValx,
                coStValx = this.coStValx,
                coEnValx = this.coEnValx,
                coYnShar = this.coYnShar,
                coStShar = this.coStShar,
                coEnShar = this.coEnShar,
                coYnVolume = this.coYnVolume,
                coStVolume = this.coStVolume,
                coEnVolume = this.coEnVolume,
                coYnAmt = this.coYnAmt,
                coStAmt = this.coStAmt,
                coEnAmt = this.coEnAmt,
                coYnEps = this.coYnEps,
                coStEps = this.coStEps,
                coEnEps = this.coEnEps,
                coYnPer = this.coYnPer,
                coStPer = this.coStPer,
                coEnPer = this.coEnPer,
                KEYB = this.KEYB
        )
}