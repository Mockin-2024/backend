package com.knu.mockin.model.dto.request.trading

data class OrderReverseRequestBodyDto(
    val transactionId: String,
    val overseasExchangeCode: String,
    val productNumber: String,
    val originalOrderNumber: String,
    val cancelOrReviseCode: String,
    var orderQuantity: String,
    var overseasOrderPrice: String,
)
