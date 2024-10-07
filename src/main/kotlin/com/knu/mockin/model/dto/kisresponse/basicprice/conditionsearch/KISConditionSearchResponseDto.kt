package com.knu.mockin.model.dto.kisresponse.basicprice.conditionsearch

import com.knu.mockin.model.dto.kisheader.response.KISOverSeaResponseHeaderDto

data class KISConditionSearchResponseDto (
        val header: KISOverSeaResponseHeaderDto, // 헤더 (필수)
        val body: KISConditionSearchResponseBodyDto // 본문 (필수)
)