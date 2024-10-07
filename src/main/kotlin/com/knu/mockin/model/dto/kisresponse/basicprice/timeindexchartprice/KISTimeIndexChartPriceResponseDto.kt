package com.knu.mockin.model.dto.kisresponse.basicprice.timeindexchartprice

import com.knu.mockin.model.dto.kisheader.response.KISOverSeaResponseHeaderDto

data class KISTimeIndexChartPriceResponseDto (
        val header: KISOverSeaResponseHeaderDto, // 헤더 (필수)
        val body: KISTimeIndexChartPriceResponseBodyDto // 본문 (필수)
)