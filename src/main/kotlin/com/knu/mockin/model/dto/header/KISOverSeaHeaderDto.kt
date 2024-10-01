package com.knu.mockin.model.dto.header

data class KISOverSeaHeaderDto(
    val contentType: String = "application/json; charset=utf-8", // 컨텐츠타입
    val authorization: String,          // 접근토큰 (필수)
    val appkey: String,                 // 앱키 (필수)
    val appsecret: String,              // 앱시크릿키 (필수)
    val personalSecKey: String? = null, // 고객식별키 (법인 필수, 선택적)
    val trId: String,                   // 거래ID (필수)
    val trCont: String? = null,         // 연속 거래 여부 (선택적)
    val custType: String? = null,       // 고객타입 (선택적)
    val seqNo: String? = null,          // 일련번호 (법인 필수, 선택적)
    val macAddress: String? = null,     // 맥주소 (선택적)
    val phoneNumber: String? = null,    // 핸드폰번호 (법인 필수, 선택적)
    val ipAddr: String? = null,         // 접속 단말 공인 IP (법인 필수, 선택적)
    val hashKey: String? = null,        // 해쉬키 (옵션)
    val gtUid: String? = null           // Global UID (법인 필수, 선택적)
)