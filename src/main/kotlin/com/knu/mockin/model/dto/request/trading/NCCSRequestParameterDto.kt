package com.knu.mockin.model.dto.request.trading

data class NCCSRequestParameterDto(
    val overseasExchangeCode: String = "",          // 해외거래소코드
    val sortOrder: String = "",                     // 정렬순서
    val continuousSearchCondition200: String = "",  // 연속조회검색조건200
    val continuousSearchKey200: String = "",        // 연속조회키200
)
