package com.knu.mockin.model.dto.kisresponse.basicprice.pricedetail

import com.knu.mockin.model.dto.kisheader.response.KISOverSeaResponseHeaderDto
import com.knu.mockin.model.dto.kisresponse.basicprice.contriesholiday.KISContriesHolidayResponseBodyDto

data class KISPriceDetailResponseDto (
        val header: KISOverSeaResponseHeaderDto, // 헤더 (필수)
        val body: KISPriceDetailResponseBodyDto // 본문 (필수)
)