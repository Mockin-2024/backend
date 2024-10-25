package com.knu.mockin.model.dto.request.trading

import com.knu.mockin.model.dto.kisrequest.trading.KISBalanceRequestParameterDto

data class BalanceRequestParameterDto (
    val overseasExchangeCode: String = "",
    val transactionCurrencyCode: String = "",
    val continuousSearchCondition200: String = "",
    val continuousSearchKey200: String = ""
)

fun BalanceRequestParameterDto.asDomain(accountNumber: String): KISBalanceRequestParameterDto{
    return KISBalanceRequestParameterDto(
        accountNumber = accountNumber,
        overseasExchangeCode = this.overseasExchangeCode,
        transactionCurrencyCode = this.transactionCurrencyCode,
        continuousSearchKey200 = this.continuousSearchKey200,
        continuousSearchCondition200 = this.continuousSearchCondition200
    )
}