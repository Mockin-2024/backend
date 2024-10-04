package com.knu.mockin.model.dto.kisresponse.KISCurrentPrice

import com.knu.mockin.model.dto.kisheader.response.KISOverSeaResponseHeaderDto
import com.knu.mockin.model.dto.kisresponse.order.KISOverSeaResponseBodyDto

data class KISCurrentPriceResponseDto(
    val header: KISOverSeaResponseHeaderDto, // 헤더 (필수)
    val body:  KISCurrentPriceResponseBodyDto// 본문 (필수)
)