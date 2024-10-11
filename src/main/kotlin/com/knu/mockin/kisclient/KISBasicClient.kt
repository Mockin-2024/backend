package com.knu.mockin.kisclient

import com.knu.mockin.model.dto.kisheader.request.KISOverSeaRequestHeaderDto
import com.knu.mockin.model.dto.kisrequest.basic.KISCurrentPriceRequestParameterDto
import com.knu.mockin.model.dto.kisrequest.basic.KISDailyChartPriceRequestParameterDto
import com.knu.mockin.model.dto.kisrequest.basic.KISSearchRequestParameterDto
import com.knu.mockin.model.dto.kisrequest.basic.KISTermPriceRequestParameterDto
import com.knu.mockin.model.dto.kisresponse.basic.KISCurrentPriceResponseDto
import com.knu.mockin.model.dto.kisresponse.basic.KISDailyChartPriceResponseDto
import com.knu.mockin.model.dto.kisresponse.basic.KISSearchResponseDto
import com.knu.mockin.model.dto.kisresponse.basic.KISTermPriceResponseDto
import com.knu.mockin.util.HttpUtils
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class KISBasicClient (
        private val webClientMock: WebClient
) {
    private val quotationUrl = "/uapi/overseas-price/v1/quotations"

    fun getCurrentPrice(
            header: KISOverSeaRequestHeaderDto,
            requestParameter: KISCurrentPriceRequestParameterDto
    ) : Mono<KISCurrentPriceResponseDto> {
        val targetUri = HttpUtils.buildUri("${quotationUrl}/price", requestParameter)
        return webClientMock.get()
                .uri(targetUri)
                .headers { HttpUtils.addHeaders(it, header) }
                .retrieve()
                .bodyToMono(KISCurrentPriceResponseDto::class.java)
    }

    fun getTermPrice(
            header: KISOverSeaRequestHeaderDto,
            requestParameter: KISTermPriceRequestParameterDto
    ) : Mono<KISTermPriceResponseDto> {
        val targetUri = HttpUtils.buildUri("${quotationUrl}/dailyprice", requestParameter)
        return webClientMock.get()
                .uri(targetUri)
                .headers { HttpUtils.addHeaders(it, header) }
                .retrieve()
                .bodyToMono(KISTermPriceResponseDto::class.java)
    }

    fun getDailyChartPrice(
            header: KISOverSeaRequestHeaderDto,
            requestParameter: KISDailyChartPriceRequestParameterDto
    ) : Mono<KISDailyChartPriceResponseDto> {
        val targetUri = HttpUtils.buildUri("${quotationUrl}/inquire-daily-chartprice", requestParameter)
        return webClientMock.get()
                .uri(targetUri)
                .headers { HttpUtils.addHeaders(it, header) }
                .retrieve()
                .bodyToMono(KISDailyChartPriceResponseDto::class.java)
    }

    fun getSearch(
            header: KISOverSeaRequestHeaderDto,
            requestParameter: KISSearchRequestParameterDto
    ) : Mono<KISSearchResponseDto> {
        val targetUri = HttpUtils.buildUri("${quotationUrl}/inquire-search", requestParameter)
        return webClientMock.get()
                .uri(targetUri)
                .headers { HttpUtils.addHeaders(it, header) }
                .retrieve()
                .bodyToMono(KISSearchResponseDto::class.java)
    }

}
