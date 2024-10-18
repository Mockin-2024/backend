package com.knu.mockin.model.dto.request.trading

data class PresentBalanceRequestParameterDto(
    val currencyDivisionCode: String = "",   // 원화외화구분코드 (01: 원화, 02: 외화)
    val countryCode: String = "",            // 국가코드 (3자리)
    val marketCode: String = "",             // 거래시장코드 (2자리)
    val inquiryDivisionCode: String = "",    // 조회구분코드 (2자리)
    val email: String =""                    // 이메일, JWT 추가 후 삭제 예정
)
