package com.knu.mockin.model.dto.kisheader.request

import com.fasterxml.jackson.annotation.JsonProperty

data class KISOverSeaRequestHeaderDto(
    @JsonProperty("content-type") val contentType: String = "application/json; charset=utf-8",         // 컨텐츠타입
    @JsonProperty("authorization") val authorization: String,               // 접근토큰
    @JsonProperty("appkey") val appKey: String,                             // 앱키
    @JsonProperty("appsecret") val appSecret: String,                       // 앱시크릿키
    @JsonProperty("personalseckey") val personalSecKey: String? = null,     // 고객식별키
    @JsonProperty("tr_id") val transactionId: String,                       // 거래ID
    @JsonProperty("tr_cont") var transactionContinuation: String? = null,   // 연속 거래 여부
    @JsonProperty("custtype") val customerType: String? = null,             // 고객타입
    @JsonProperty("seq_no") val sequenceNumber: String? = null,             // 일련번호
    @JsonProperty("mac_address") val macAddress: String? = null,            // 맥주소
    @JsonProperty("phone_number") val phoneNumber: String? = null,          // 핸드폰번호
    @JsonProperty("ip_addr") val ipAddress: String? = null,                 // 접속 단말 공인 IP
    @JsonProperty("hashkey") val hashKey: String? = null,                   // 해쉬키
    @JsonProperty("gt_uid") val globalUid: String? = null                   // Global UID
)