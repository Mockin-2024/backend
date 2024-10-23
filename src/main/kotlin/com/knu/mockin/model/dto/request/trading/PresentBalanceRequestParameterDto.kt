package com.knu.mockin.model.dto.request.trading

data class PresentBalanceRequestParameterDto(
    val currencyDivisionCode: String = "",
    val countryCode: String = "",
    val marketCode: String = "",
    val inquiryDivisionCode: String = ""
)
