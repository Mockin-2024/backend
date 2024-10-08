package com.knu.mockin.model.dto.kisresponse

import com.knu.mockin.model.dto.kisheader.response.KISOverSeaResponseHeaderDto

data class TestResponseDto (
        val header: KISOverSeaResponseHeaderDto, // 헤더 (필수)
        val body: TestResponseBodyDto // 본문 (필수)
)