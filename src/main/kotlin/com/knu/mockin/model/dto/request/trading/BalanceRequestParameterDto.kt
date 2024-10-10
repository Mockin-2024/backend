package com.knu.mockin.model.dto.request.trading

data class BalanceRequestParameterDto (
    val overseasExchangeCode: String,
    val transactionCurrencyCode: String,
    val continuousSearchCondition200: String = "",
    val continuousSearchKey200: String = "",
    val email: String    // 이메일, JWT 추가 후 삭제 예정
)