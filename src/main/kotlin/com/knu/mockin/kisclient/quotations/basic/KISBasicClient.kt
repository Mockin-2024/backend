package com.knu.mockin.kisclient.quotations.basic

import com.knu.mockin.kisclient.getWithParams
import com.knu.mockin.model.dto.kisheader.request.KISOverSeaRequestHeaderDto
import com.knu.mockin.model.dto.kisrequest.quotations.basic.mock.KISPriceRequestParameterDto
import com.knu.mockin.model.dto.kisrequest.quotations.basic.mock.KISInquireSearchRequestParameterDto
import com.knu.mockin.model.dto.kisrequest.quotations.basic.mock.KISDailyPriceRequestParameterDto
import com.knu.mockin.model.dto.kisrequest.quotations.basic.mock.KISInquireDailyChartPriceRequestParameterDto
import com.knu.mockin.model.dto.kisresponse.quotations.basic.mock.KISInquireDailyChartPriceResponseDto
import com.knu.mockin.model.dto.kisresponse.quotations.basic.mock.KISDailyPriceResponseDto
import com.knu.mockin.model.dto.kisresponse.quotations.basic.mock.KISPriceResponseDto
import com.knu.mockin.model.dto.kisresponse.quotations.basic.mock.KISInquireSearchResponseDto
import com.knu.mockin.util.HttpUtils
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class KISBasicClient (
        private val webClientMock: WebClient
) {
    private val quotationUrl = "/uapi/overseas-price/v1/quotations"

    fun getPrice(
        headerDto: KISOverSeaRequestHeaderDto,
        parameterDto: KISPriceRequestParameterDto
    ) : Mono<KISPriceResponseDto> {
        val targetUri = HttpUtils.buildUri("${quotationUrl}/price", parameterDto)
        return webClientMock.getWithParams(
            uri = targetUri,
            headerDto = headerDto,
            responseType = KISPriceResponseDto::class.java
        )
    }

    fun getDailyPrice(
        headerDto: KISOverSeaRequestHeaderDto,
        parameterDto: KISDailyPriceRequestParameterDto
    ) : Mono<KISDailyPriceResponseDto> {
        val targetUri = HttpUtils.buildUri("${quotationUrl}/dailyprice", parameterDto)
        return webClientMock.getWithParams(
            uri = targetUri,
            headerDto = headerDto,
            responseType = KISDailyPriceResponseDto::class.java
        )
    }

    fun getInquireDailyChartPrice(
        headerDto: KISOverSeaRequestHeaderDto,
        parameterDto: KISInquireDailyChartPriceRequestParameterDto
    ) : Mono<KISInquireDailyChartPriceResponseDto> {
        val targetUri = HttpUtils.buildUri("${quotationUrl}/inquire-daily-chartprice", parameterDto)
        return webClientMock.getWithParams(
            uri = targetUri,
            headerDto = headerDto,
            responseType = KISInquireDailyChartPriceResponseDto::class.java
        )
    }

    fun getInquireSearch(
        headerDto: KISOverSeaRequestHeaderDto,
        parameterDto: KISInquireSearchRequestParameterDto
    ) : Mono<KISInquireSearchResponseDto> {
        val targetUri = HttpUtils.buildUri("${quotationUrl}/inquire-search", parameterDto)
        return webClientMock.getWithParams(
            uri = targetUri,
            headerDto = headerDto,
            responseType = KISInquireSearchResponseDto::class.java
        )
    }

}
