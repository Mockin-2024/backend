package com.knu.mockin.model.dto.kisrequest.basicprice.searchinfo

import com.fasterxml.jackson.annotation.JsonProperty

data class KISSearchInfoRequestParameter (
        @JsonProperty("PRDT_TYPE_CD") val prdtTypeCd: String, // 상품유형코드
        @JsonProperty("PDNO") val pdno: String                 // 상품번호
)