package com.knu.mockin.model.dto.request.basic

data class SearchInfoRequestParameterDto (
    val prdtTypeCd: String = "", // 상품유형코드
    val pdno: String = "",        // 상품번호
    val email: String = ""        // 이메일
)
