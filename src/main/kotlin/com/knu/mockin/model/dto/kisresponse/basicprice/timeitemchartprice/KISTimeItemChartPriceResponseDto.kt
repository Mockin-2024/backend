package com.knu.mockin.model.dto.kisresponse.basicprice.timeitemchartprice

import com.knu.mockin.model.dto.kisheader.response.KISOverSeaResponseHeaderDto

data class KISTimeItemChartPriceResponseDto (
        val header: KISOverSeaResponseHeaderDto, // 헤더 (필수)
        val body: KISTimeItemChartPriceResponseBodyDto // 본문 (필수)
)