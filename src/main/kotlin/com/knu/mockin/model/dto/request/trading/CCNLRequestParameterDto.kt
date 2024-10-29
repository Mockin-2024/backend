package com.knu.mockin.model.dto.request.trading

import com.knu.mockin.model.dto.kisrequest.trading.KISCCNLRequestParameterDto

data class CCNLRequestParameterDto(
    val orderStartDate: String = "",
    val orderEndDate: String = "",
    val continuousSearchKey200: String = "",
    val continuousSearchCondition200: String = "",
    val transactionContinuation: String = ""
)

fun CCNLRequestParameterDto.asDomain(accountNumber: String): KISCCNLRequestParameterDto{
    return KISCCNLRequestParameterDto(
        accountNumber = accountNumber,
        orderStartDate = this.orderStartDate,
        orderEndDate = this.orderEndDate,
        continuousSearchKey200 = this.continuousSearchKey200,
        continuousSearchCondition200 = this.continuousSearchCondition200
    )
}