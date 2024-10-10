package com.knu.mockin.model.dto.request.trading

data class PsAmountRequestParameterDto(
    val overseasExchangeCode: String,   // 해외거래소코드
    val itemCode: String,               // 상품번호
    val overseasOrderUnitPrice: String, // 해외주문단가
    val email: String                   // 이메일, JWT 추가 후 삭제 예정
)
