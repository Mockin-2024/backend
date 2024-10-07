package com.knu.mockin.model.dto.kisresponse.basicprice.conditionsearch

import com.fasterxml.jackson.annotation.JsonProperty

data class KISConditionSearchResponseOutputDto (
        @JsonProperty("zdiv") val zdiv: String,                    // 소수점 자리수
        @JsonProperty("stat") val stat: String,                     // 거래상태정보
        @JsonProperty("crec") val crec: String,                     // 현재조회종목수
        @JsonProperty("trec") val trec: String,                     // 전체조회종목수
        @JsonProperty("nrec") val nrec: String                      // Record Count`
)