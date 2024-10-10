package com.knu.mockin.kisclient

import com.knu.mockin.model.dto.kisheader.request.KISOverSeaRequestHeaderDto
import com.knu.mockin.model.dto.kisrequest.basic.mock.KISCurrentPriceRequestParameterDto
import com.knu.mockin.model.dto.kisrequest.basic.real.KISCountriesHolidayRequestParameterDto
import com.knu.mockin.model.dto.kisresponse.basic.mock.KISCurrentPriceResponseDto
import com.knu.mockin.model.dto.kisresponse.basic.real.KISCountriesHolidayResponseDto
import com.knu.mockin.util.HttpUtils
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class KISBasicRealClient (
        private val webClientReal: WebClient
) {
    private val quotationUrl = "/uapi/overseas-price/v1/quotations"
    private val stockUrl = "/uapi/overseas-stock/v1/quotations"

    fun getCountriesHoliday(
            header: KISOverSeaRequestHeaderDto,
            requestParameter: KISCountriesHolidayRequestParameterDto
    ): Mono<KISCountriesHolidayResponseDto> {
        val targetUri = HttpUtils.buildUri("${stockUrl}/countries-holiday", requestParameter)
        return webClientReal.get()
                .uri(targetUri)
                .headers { HttpUtils.addHeaders(it, header) }
                .retrieve()
                .bodyToMono(KISCountriesHolidayResponseDto::class.java)
    }
}