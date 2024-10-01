package com.knu.mockin.model.dto.kisheader.request

import com.fasterxml.jackson.annotation.JsonProperty

data class KISOverSeaRequestHeaderDto(
    @JsonProperty("content-type") val contentType: String?,         // 컨텐츠타입
    @JsonProperty("authorization") val authorization: String,       // 접근토큰
    @JsonProperty("appkey") val appKey: String,                     // 앱키
    @JsonProperty("appsecret") val appSecret: String,               // 앱시크릿키
    @JsonProperty("personalseckey") val personalSecKey: String?,    // 고객식별키
    @JsonProperty("tr_id") val transactionId: String,               // 거래ID
    @JsonProperty("tr_cont") val transactionContinuation: String?,  // 연속 거래 여부
    @JsonProperty("custtype") val customerType: String?,            // 고객타입
    @JsonProperty("seq_no") val sequenceNumber: String?,            // 일련번호
    @JsonProperty("mac_address") val macAddress: String?,           // 맥주소
    @JsonProperty("phone_number") val phoneNumber: String?,         // 핸드폰번호
    @JsonProperty("ip_addr") val ipAddress: String?,                // 접속 단말 공인 IP
    @JsonProperty("hashkey") val hashKey: String?,                  // 해쉬키
    @JsonProperty("gt_uid") val globalUid: String?                  // Global UID
)