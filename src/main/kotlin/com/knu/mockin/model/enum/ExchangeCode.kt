package com.knu.mockin.model.enum

enum class ExchangeCode(code: String) {
    NASD("NASD"), // 나스닥
    NYSE("NYSE"), // 뉴욕
    AMEX("AMEX"), // 아멕스
    SEHK("SEHK"), // 홍콩
    SHAA("SHAA"), // 중국상해
    SZAA("SZAA"), // 중국심천
    TKSE("TKSE"), // 일본
    HASE("HASE"), // 베트남 하노이
    VNSE("VNSE")  // 베트남 호치민
}