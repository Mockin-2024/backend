package com.knu.mockin.model.dto.request.trading

import com.fasterxml.jackson.annotation.JsonProperty

data class OrderReverseRequestBodyDto(
    val email: String,
    val transactionId: String,
    val overseasExchangeCode: String,
    val productNumber: String,
    val originalOrderNumber: String,
    val cancelOrReviseCode: String,
    var orderQuantity: String,
    var overseasOrderPrice: String,
)
