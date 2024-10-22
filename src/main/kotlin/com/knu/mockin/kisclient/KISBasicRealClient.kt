package com.knu.mockin.kisclient

import com.knu.mockin.model.dto.kisheader.request.KISOverSeaRequestHeaderDto
import com.knu.mockin.model.dto.kisrequest.basic.*
import com.knu.mockin.model.dto.kisresponse.basic.*
import com.knu.mockin.util.HttpUtils
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class KISBasicRealClient (
        private val webClientReal: WebClient
) {
    private val priceQuotationUrl = "/uapi/overseas-price/v1/quotations"
    private val stockQuotationUrl = "/uapi/overseas-stock/v1/quotations"

    fun getCountriesHoliday(
            header: KISOverSeaRequestHeaderDto,
            requestParameter: KISCountriesHolidayRequestParameterDto
    ) :Mono<KISCountriesHolidayResponseDto> {
        val targetUri = HttpUtils.buildUri("${stockQuotationUrl}/countries-holiday", requestParameter)
        return webClientReal.get()
                .uri(targetUri)
                .headers { HttpUtils.addHeaders(it, header) }
                .retrieve()
                .bodyToMono(KISCountriesHolidayResponseDto::class.java)
    }

    fun getPriceDetail(
            header: KISOverSeaRequestHeaderDto,
            requestParameter: KISPriceDetailRequestParameterDto
    ): Mono<KISPriceDetailResponseDto> {
        val targetUri = HttpUtils.buildUri("${priceQuotationUrl}/price-detail", requestParameter)
        return webClientReal.get()
                .uri(targetUri)
                .headers { HttpUtils.addHeaders(it, header) }
                .retrieve()
                .bodyToMono(KISPriceDetailResponseDto::class.java)
    }

    fun getItemChartPrice(
            header: KISOverSeaRequestHeaderDto,
            requestParameter: KISItemChartPriceRequestParameterDto
    ): Mono<KISItemChartPriceResponseDto> {
        val targetUri = HttpUtils.buildUri("${priceQuotationUrl}/inquire-time-itemchartprice", requestParameter)
        return webClientReal.get()
                .uri(targetUri)
                .headers { HttpUtils.addHeaders(it, header) }
                .retrieve()
                .bodyToMono(KISItemChartPriceResponseDto::class.java)
    }

    fun getIndexChartPrice(
            header: KISOverSeaRequestHeaderDto,
            requestParameter: KISIndexChartPriceRequestParameterDto
    ): Mono<KISIndexChartPriceResponseDto> {
        val targetUri = HttpUtils.buildUri("${priceQuotationUrl}/inquire-time-indexchartprice", requestParameter)
        return webClientReal.get()
                .uri(targetUri)
                .headers { HttpUtils.addHeaders(it, header) }
                .retrieve()
                .bodyToMono(KISIndexChartPriceResponseDto::class.java)
    }

    fun getNewsTitle(
        header: KISOverSeaRequestHeaderDto,
        requestParameter: KISNewsTitleRequestParameterDto
    ): Mono<KISNewsTitleResponseDto> {
        val targetUri = HttpUtils.buildUri("${priceQuotationUrl}/news-title", requestParameter)
        return webClientReal.get()
            .uri(targetUri)
            .headers { HttpUtils.addHeaders(it, header) }
            .retrieve()
            .bodyToMono(KISNewsTitleResponseDto::class.java)
    }
}