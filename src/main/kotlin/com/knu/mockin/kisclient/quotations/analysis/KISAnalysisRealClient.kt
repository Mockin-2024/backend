package com.knu.mockin.kisclient.quotations.analysis

import com.knu.mockin.kisclient.getWithParams
import com.knu.mockin.model.dto.kisheader.request.KISOverSeaRequestHeaderDto
import com.knu.mockin.model.dto.kisrequest.quotations.analysis.KISNewsTitleRequestParameterDto
import com.knu.mockin.model.dto.kisresponse.quotations.analysis.KISNewsTitleResponseDto
import com.knu.mockin.util.HttpUtils
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class KISAnalysisRealClient (
    private val webClientReal: WebClient
) {
    private val priceQuotationUrl = "/uapi/overseas-price/v1/quotations"
    private val stockQuotationUrl = "/uapi/overseas-stock/v1/quotations"

    fun getNewsTitle(
        headerDto: KISOverSeaRequestHeaderDto,
        parameterDto: KISNewsTitleRequestParameterDto
    ): Mono<KISNewsTitleResponseDto> {
        val targetUri = HttpUtils.buildUri("${priceQuotationUrl}/news-title", parameterDto)

        return webClientReal.getWithParams(
            uri = targetUri,
            headerDto = headerDto,
            responseType = KISNewsTitleResponseDto::class.java
        )
    }
}