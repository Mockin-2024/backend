package com.knu.mockin.model.dto.request.trading

import com.knu.mockin.model.dto.kisrequest.trading.KISOrderReverseRequestBodyDto

data class OrderReverseRequestBodyDto(
    val transactionId: String,
    val overseasExchangeCode: String,
    val productNumber: String,
    val originalOrderNumber: String,
    val cancelOrReviseCode: String,
    var orderQuantity: String,
    var overseasOrderPrice: String,
)

fun OrderReverseRequestBodyDto.asDomain(accountNumber: String):KISOrderReverseRequestBodyDto{
    return KISOrderReverseRequestBodyDto(
        accountNumber = accountNumber,
        overseasExchangeCode = this.overseasExchangeCode,
        productNumber = this.productNumber,
        originalOrderNumber = this.originalOrderNumber,
        cancelOrReviseCode = this.cancelOrReviseCode,
        orderQuantity = this.orderQuantity,
        overseasOrderPrice = this.overseasOrderPrice
    )
}