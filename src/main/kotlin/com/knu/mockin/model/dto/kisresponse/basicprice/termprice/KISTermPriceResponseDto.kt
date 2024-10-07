package com.knu.mockin.model.dto.kisresponse.basicprice.termprice

import com.knu.mockin.model.dto.kisheader.response.KISOverSeaResponseHeaderDto

data class KISTermPriceResponseDto (
        val header: KISOverSeaResponseHeaderDto, // 헤더 (필수)
        val body: KISTermPriceResponseBodyDto// 본문 (필수)
)