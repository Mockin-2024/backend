package com.knu.mockin.model.dto.request.trading
data class CCNLRequestParameterDto(
    val orderStartDate: String = "", // 주문시작일자
    val orderEndDate: String = "",   // 주문종료일자
    val email: String = ""           // 이메일, JWT 추가 후 삭제 예정
)