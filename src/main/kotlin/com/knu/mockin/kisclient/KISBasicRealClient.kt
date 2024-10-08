package com.knu.mockin.kisclient

import com.knu.mockin.logging.utils.LogUtil
import com.knu.mockin.model.dto.kisheader.request.KISOverSeaRequestHeaderDto
import com.knu.mockin.model.dto.kisrequest.basicprice.real.countriesholiday.KISCountriesHolidayRequestParameterDto
import com.knu.mockin.model.dto.kisresponse.basicprice.mock.currentprice.KISCurrentPriceResponseDto
import com.knu.mockin.model.dto.kisresponse.basicprice.real.countriesholiday.KISCountriesHolidayResponseDto
import com.knu.mockin.util.httpUtils
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Mono

@Component
class KISBasicRealClient (
        private val webClientReal : WebClient
) {
    private val priceQuotationUrl = "/uapi/overseas-price/v1/quotations"
    private val stockQuotationUrl = "/uapi/overseas-stock/v1/quotations"

    fun getCountriesHoliday(
            header: KISOverSeaRequestHeaderDto,
            requestParameter: KISCountriesHolidayRequestParameterDto
    ) : Mono<KISCountriesHolidayResponseDto> {
        val uriBuilder = UriComponentsBuilder.fromUriString("${stockQuotationUrl}/countries-holiday")
                .queryParam("TRAD_DT", requestParameter.tradDt)
                .queryParam("CTX_AREA_NK", requestParameter.ctxAreaNk)
                .queryParam("CTX_AREA_FK", requestParameter.ctxAreaFk)
        println(uriBuilder.toUriString())
        println(LogUtil.toJson(requestParameter))
        return webClientReal.get()
                .uri(uriBuilder.toUriString())
                .headers { httpUtils.addHeaders(it, header) }
                .retrieve()
                .bodyToMono(KISCountriesHolidayResponseDto::class.java)
    }


}