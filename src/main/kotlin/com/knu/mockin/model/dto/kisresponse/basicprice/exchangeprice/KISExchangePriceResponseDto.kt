package com.knu.mockin.model.dto.kisresponse.basicprice.exchangeprice

import com.knu.mockin.model.dto.kisheader.response.KISOverSeaResponseHeaderDto

data class KISExchangePriceResponseDto (
        val header: KISOverSeaResponseHeaderDto, // 헤더 (필수)
        val body: KISExchangePriceResponseBodyDto// 본문 (필수)
)