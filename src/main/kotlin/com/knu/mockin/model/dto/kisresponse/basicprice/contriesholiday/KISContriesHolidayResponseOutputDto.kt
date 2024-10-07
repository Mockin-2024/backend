package com.knu.mockin.model.dto.kisresponse.basicprice.contriesholiday

import com.fasterxml.jackson.annotation.JsonProperty

data class KISContriesHolidayResponseOutputDto (
        @JsonProperty("prdt_type_cd") val prdtTypeCd: String,      // 상품유형코드
        @JsonProperty("tr_natn_cd") val trNatnCd: String,          // 거래국가코드
        @JsonProperty("tr_natn_name") val trNatnName: String,      // 거래국가명
        @JsonProperty("natn_eng_abrv_cd") val natnEngAbrvCd: String, // 국가영문약어코드
        @JsonProperty("tr_mket_cd") val trMketCd: String,          // 거래시장코드
        @JsonProperty("tr_mket_name") val trMketName: String,      // 거래시장명
        @JsonProperty("acpl_sttl_dt") val acplSttlDt: String,      // 현지결제일자
        @JsonProperty("dmst_sttl_dt") val dmstSttlDt: String        // 국내결제일자
)