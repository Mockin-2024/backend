package com.knu.mockin.model.dto.kisresponse.basicprice.contriesholiday

import com.knu.mockin.model.dto.kisheader.response.KISOverSeaResponseHeaderDto
import com.knu.mockin.model.dto.kisresponse.basicprice.conditionsearch.KISConditionSearchResponseBodyDto

data class KISContriesHolidayResponseDto (
        val header: KISOverSeaResponseHeaderDto, // 헤더 (필수)
        val body: KISContriesHolidayResponseBodyDto // 본문 (필수)
)