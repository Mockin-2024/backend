package com.knu.mockin.model.dto.kisresponse.order

import com.knu.mockin.model.dto.kisheader.response.KISOverSeaResponseHeaderDto

data class KISOverSeaResponseDto(
    val header: KISOverSeaResponseHeaderDto, // 헤더 (필수)
    val body: KISOverSeaResponseBodyDto // 본문 (필수)
)