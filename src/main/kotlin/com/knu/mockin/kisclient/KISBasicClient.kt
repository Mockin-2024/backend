package com.knu.mockin.kisclient

import com.knu.mockin.logging.utils.LogUtil
import com.knu.mockin.model.dto.kisheader.request.KISOverSeaRequestHeaderDto
import com.knu.mockin.model.dto.kisrequest.basic.KISCurrentPriceRequestParameterDto
import com.knu.mockin.model.dto.kisrequest.basic.KISTermPriceRequestParameterDto
import com.knu.mockin.model.dto.kisresponse.basic.KISCurrentPriceResponseDto
import com.knu.mockin.model.dto.kisresponse.basic.KISTermPriceResponseDto
import com.knu.mockin.util.HttpUtils
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder
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
        val uriBuilder = UriComponentsBuilder.fromUriString("${quotationUrl}/price")
                .queryParam("AUTH", requestParameter.AUTH)
                .queryParam("SYMB", requestParameter.SYMB)
                .queryParam("EXCD", requestParameter.EXCD)
        println(uriBuilder.toUriString())
        println(LogUtil.toJson(requestParameter))
        return webClientMock.get()
                .uri(uriBuilder.toUriString())
                .headers { HttpUtils.addHeaders(it, header) }
                .retrieve()
                .bodyToMono(KISCurrentPriceResponseDto::class.java)
    }

    fun getTermPrice(
            header: KISOverSeaRequestHeaderDto,
            requestParameter: KISTermPriceRequestParameterDto
    ) : Mono<KISTermPriceResponseDto> {
        return webClientMock.get()
                .uri{ UriBuilder ->
                    UriBuilder.path("${quotationUrl}/dailyprice")
                            .queryParam("AUTH", requestParameter.AUTH)
                            .queryParam("EXCD", requestParameter.EXCD)
                            .queryParam("SYMB", requestParameter.SYMB)
                            .queryParam("GUBN", requestParameter.GUBN)
                            .queryParam("BYMD", requestParameter.BYMD)
                            .queryParam("MODP", requestParameter.MODP)
                            .build()
                }
                .headers { HttpUtils.addHeaders(it, header) }
                .retrieve()
                .bodyToMono(KISTermPriceResponseDto::class.java)
    }


}