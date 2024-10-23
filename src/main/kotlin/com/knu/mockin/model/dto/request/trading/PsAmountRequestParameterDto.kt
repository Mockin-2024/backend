package com.knu.mockin.model.dto.request.trading

data class PsAmountRequestParameterDto(
    val overseasExchangeCode: String = "",
    val itemCode: String = "",
    val overseasOrderUnitPrice: String = ""
)
