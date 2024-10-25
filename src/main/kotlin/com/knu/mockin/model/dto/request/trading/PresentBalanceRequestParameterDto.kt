package com.knu.mockin.model.dto.request.trading

import com.knu.mockin.model.dto.kisrequest.trading.KISPresentBalanceRequestParameterDto

data class PresentBalanceRequestParameterDto(
    val currencyDivisionCode: String = "",
    val countryCode: String = "",
    val marketCode: String = "",
    val inquiryDivisionCode: String = ""
)

fun PresentBalanceRequestParameterDto.asDomain(accountNumber:String): KISPresentBalanceRequestParameterDto{
    return KISPresentBalanceRequestParameterDto(
        accountNumber = accountNumber,
        currencyDivisionCode = this.currencyDivisionCode,
        countryCode = this.countryCode,
        marketCode = this.marketCode,
        inquiryDivisionCode = this.inquiryDivisionCode
    )
}