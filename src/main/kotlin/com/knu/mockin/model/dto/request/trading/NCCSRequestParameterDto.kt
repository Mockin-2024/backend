package com.knu.mockin.model.dto.request.trading

import com.knu.mockin.model.dto.kisrequest.trading.KISNCCSRequestParameterDto

data class NCCSRequestParameterDto(
    val overseasExchangeCode: String = "",          // 해외거래소코드
    val sortOrder: String = "",                     // 정렬순서
    val continuousSearchCondition200: String = "",  // 연속조회검색조건200
    val continuousSearchKey200: String = "",        // 연속조회키200
)

fun NCCSRequestParameterDto.asDomain(accountNumber: String): KISNCCSRequestParameterDto{
    return KISNCCSRequestParameterDto(
        accountNumber = accountNumber,
        overseasExchangeCode = this.overseasExchangeCode,
        sortOrder = this.sortOrder,
        continuousSearchCondition200 = this.continuousSearchCondition200,
        continuousSearchKey200 = this.continuousSearchKey200
    )
}