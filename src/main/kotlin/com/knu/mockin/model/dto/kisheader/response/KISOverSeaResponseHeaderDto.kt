package com.knu.mockin.model.dto.kisheader.response

import com.fasterxml.jackson.annotation.JsonProperty

data class KISOverSeaResponseHeaderDto(
    @JsonProperty("content-type") val contentType: String,          // 컨텐츠타입
    @JsonProperty("tr_id") val transactionId: String,               // 거래ID
    @JsonProperty("tr_cont") val transactionContinuation: String,   // 연속 거래 여부
    @JsonProperty("gt_uid") val globalUid: String                   // Global UID
)
