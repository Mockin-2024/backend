package com.knu.mockin.model.dto.kisresponse.basicprice.askingprice

import com.knu.mockin.model.dto.kisheader.response.KISOverSeaResponseHeaderDto
import com.knu.mockin.model.dto.kisresponse.basicprice.conditionsearch.KISConditionSearchResponseBodyDto

data class KISAskingPriceResponseDto (
        val header: KISOverSeaResponseHeaderDto, // 헤더 (필수)
        val body: KISAskingPriceResponseBodyDto // 본문 (필수)
)