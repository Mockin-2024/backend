package com.knu.mockin.kisclient.quotations.basic

import com.knu.mockin.kisclient.getWithParams
import com.knu.mockin.model.dto.kisheader.request.KISOverSeaRequestHeaderDto
import com.knu.mockin.model.dto.kisrequest.quotations.basic.real.*
import com.knu.mockin.model.dto.kisresponse.quotations.basic.real.*
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

    fun getInquireTimeItemChartPrice(
        headerDto: KISOverSeaRequestHeaderDto,
        parameterDto: KISInquireTimeItemChartPriceRequestParameterDto
    ): Mono<KISInquireTimeItemChartPriceResponseDto> {
        val targetUri = HttpUtils.buildUri("${priceQuotationUrl}/inquire-time-itemchartprice", parameterDto)

        return webClientReal.getWithParams(
            uri = targetUri,
            headerDto = headerDto,
            responseType = KISInquireTimeItemChartPriceResponseDto::class.java
        )
    }

    fun getInquireTimeIndexChartPrice(
        headerDto: KISOverSeaRequestHeaderDto,
        parameterDto: KISInquireTimeIndexChartPriceRequestParameterDto
    ): Mono<KISInquireTimeIndexChartPriceResponseDto> {
        val targetUri = HttpUtils.buildUri("${priceQuotationUrl}/inquire-time-indexchartprice", parameterDto)

        return webClientReal.getWithParams(
            uri = targetUri,
            headerDto = headerDto,
            responseType = KISInquireTimeIndexChartPriceResponseDto::class.java
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

}