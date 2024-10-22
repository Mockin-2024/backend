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
        headerDto: KISOverSeaRequestHeaderDto,
        parameterDto: KISCountriesHolidayRequestParameterDto
    ) :Mono<KISCountriesHolidayResponseDto> {
        val targetUri = HttpUtils.buildUri("${stockQuotationUrl}/countries-holiday", parameterDto)

        return webClientReal.getWithParams(
            uri = targetUri,
            headerDto = headerDto,
            responseType = KISCountriesHolidayResponseDto::class.java
        )
    }

    fun getPriceDetail(
        headerDto: KISOverSeaRequestHeaderDto,
        parameterDto: KISPriceDetailRequestParameterDto
    ): Mono<KISPriceDetailResponseDto> {
        val targetUri = HttpUtils.buildUri("${priceQuotationUrl}/price-detail", parameterDto)

        return webClientReal.getWithParams(
            uri = targetUri,
            headerDto = headerDto,
            responseType = KISPriceDetailResponseDto::class.java
        )
    }

    fun getItemChartPrice(
        headerDto: KISOverSeaRequestHeaderDto,
        parameterDto: KISItemChartPriceRequestParameterDto
    ): Mono<KISItemChartPriceResponseDto> {
        val targetUri = HttpUtils.buildUri("${priceQuotationUrl}/inquire-time-itemchartprice", parameterDto)

        return webClientReal.getWithParams(
            uri = targetUri,
            headerDto = headerDto,
            responseType = KISItemChartPriceResponseDto::class.java
        )
    }

    fun getIndexChartPrice(
        headerDto: KISOverSeaRequestHeaderDto,
        parameterDto: KISIndexChartPriceRequestParameterDto
    ): Mono<KISIndexChartPriceResponseDto> {
        val targetUri = HttpUtils.buildUri("${priceQuotationUrl}/inquire-time-indexchartprice", parameterDto)

        return webClientReal.getWithParams(
            uri = targetUri,
            headerDto = headerDto,
            responseType = KISIndexChartPriceResponseDto::class.java
        )
    }

    fun getSearchInfo(
        headerDto: KISOverSeaRequestHeaderDto,
        parameterDto: KISSearchInfoRequestParameterDto
    ): Mono<KISSearchInfoResponseDto> {
        val targetUri = HttpUtils.buildUri("${priceQuotationUrl}/search-info", parameterDto)

        return webClientReal.getWithParams(
            uri = targetUri,
            headerDto = headerDto,
            responseType = KISSearchInfoResponseDto::class.java
        )
    }
    
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