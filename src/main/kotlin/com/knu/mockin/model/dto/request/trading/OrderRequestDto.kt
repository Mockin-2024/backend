package com.knu.mockin.model.dto.request.trading

data class OrderRequestDto(
    val transactionId: String,          // 요청 종류
    val overseasExchangeCode: String,   // 해외거래소코드
    val productNumber: String,          // 상품번호
    val orderQuantity: String,          // 주문수량
    val overseasOrderUnitPrice: String, // 해외주문단가
)
