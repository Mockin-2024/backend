package com.knu.mockin.model.dto.request.trading

import com.knu.mockin.model.dto.kisrequest.trading.KISPsAmountRequestParameterDto

data class PsAmountRequestParameterDto(
    val overseasExchangeCode: String = "",
    val itemCode: String = "",
    val overseasOrderUnitPrice: String = ""
)

fun PsAmountRequestParameterDto.asDomain(accountNumber:String):KISPsAmountRequestParameterDto{
    return KISPsAmountRequestParameterDto(
        accountNumber = accountNumber,
        overseasExchangeCode = this.overseasExchangeCode,
        itemCode = this.itemCode,
        overseasOrderUnitPrice = this.overseasOrderUnitPrice
    )
}