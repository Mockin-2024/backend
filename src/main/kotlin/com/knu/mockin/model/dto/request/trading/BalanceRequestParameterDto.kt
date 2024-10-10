package com.knu.mockin.model.dto.request.trading

data class BalanceRequestParameterDto (
    val overseasExchangeCode: String,
    val transactionCurrencyCode: String,
    val continuousSearchCondition200: String = "",
    val continuousSearchKey200: String = ""
)