package com.knu.mockin.model.dto.kisresponse.basicprice.searchinfo

import com.knu.mockin.model.dto.kisheader.response.KISOverSeaResponseHeaderDto
import com.knu.mockin.model.dto.kisresponse.basicprice.conditionsearch.KISConditionSearchResponseBodyDto

data class KISSearchInfoResponseDto (
        val header: KISOverSeaResponseHeaderDto, // 헤더 (필수)
        val body: KISSearchInfoResponseBodyDto // 본문 (필수)
)