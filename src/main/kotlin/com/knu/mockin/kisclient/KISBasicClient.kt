package com.knu.mockin.kisclient

import com.knu.mockin.model.dto.kisheader.request.KISOverSeaRequestHeaderDto
import com.knu.mockin.model.dto.kisrequest.quotations.basic.mock.KISCurrentPriceRequestParameterDto
import com.knu.mockin.model.dto.kisrequest.quotations.basic.mock.KISDailyChartPriceRequestParameterDto
import com.knu.mockin.model.dto.kisrequest.quotations.basic.mock.KISSearchRequestParameterDto
import com.knu.mockin.model.dto.kisrequest.quotations.basic.mock.KISTermPriceRequestParameterDto
import com.knu.mockin.model.dto.kisresponse.quotations.basic.mock.KISCurrentPriceResponseDto
import com.knu.mockin.model.dto.kisresponse.quotations.basic.mock.KISDailyChartPriceResponseDto
import com.knu.mockin.model.dto.kisresponse.quotations.basic.mock.KISSearchResponseDto
import com.knu.mockin.model.dto.kisresponse.quotations.basic.mock.KISTermPriceResponseDto
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
        headerDto: KISOverSeaRequestHeaderDto,
        parameterDto: KISCurrentPriceRequestParameterDto
    ) : Mono<KISCurrentPriceResponseDto> {
        val targetUri = HttpUtils.buildUri("${quotationUrl}/price", parameterDto)
        return webClientMock.getWithParams(
            uri = targetUri,
            headerDto = headerDto,
            responseType = KISCurrentPriceResponseDto::class.java
        )
    }

    fun getTermPrice(
        headerDto: KISOverSeaRequestHeaderDto,
        parameterDto: KISTermPriceRequestParameterDto
    ) : Mono<KISTermPriceResponseDto> {
        val targetUri = HttpUtils.buildUri("${quotationUrl}/dailyprice", parameterDto)
        return webClientMock.getWithParams(
            uri = targetUri,
            headerDto = headerDto,
            responseType = KISTermPriceResponseDto::class.java
        )
    }

    fun getDailyChartPrice(
        headerDto: KISOverSeaRequestHeaderDto,
        parameterDto: KISDailyChartPriceRequestParameterDto
    ) : Mono<KISDailyChartPriceResponseDto> {
        val targetUri = HttpUtils.buildUri("${quotationUrl}/inquire-daily-chartprice", parameterDto)
        return webClientMock.getWithParams(
            uri = targetUri,
            headerDto = headerDto,
            responseType = KISDailyChartPriceResponseDto::class.java
        )
    }

    fun getSearch(
        headerDto: KISOverSeaRequestHeaderDto,
        parameterDto: KISSearchRequestParameterDto
    ) : Mono<KISSearchResponseDto> {
        val targetUri = HttpUtils.buildUri("${quotationUrl}/inquire-search", parameterDto)
        return webClientMock.getWithParams(
            uri = targetUri,
            headerDto = headerDto,
            responseType = KISSearchResponseDto::class.java
        )
    }

}
