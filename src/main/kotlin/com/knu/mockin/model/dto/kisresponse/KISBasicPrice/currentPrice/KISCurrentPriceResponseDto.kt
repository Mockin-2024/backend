package com.knu.mockin.model.dto.kisresponse.KISBasicPrice.currentPrice

import com.knu.mockin.model.dto.kisheader.response.KISOverSeaResponseHeaderDto

data class KISCurrentPriceResponseDto(
    val header: KISOverSeaResponseHeaderDto, // 헤더 (필수)
    val body: KISCurrentPriceResponseBodyDto// 본문 (필수)
)