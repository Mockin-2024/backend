package com.knu.mockin.kisclient

import com.knu.mockin.logging.utils.LogUtil
import com.knu.mockin.model.dto.kisheader.request.KISOverSeaRequestHeaderDto
import com.knu.mockin.model.dto.kisrequest.basicprice.mock.conditionsearch.KISConditionSearchRequestParameterDto
import com.knu.mockin.model.dto.kisrequest.basicprice.mock.currentprice.KISCurrentPriceRequestParameterDto
import com.knu.mockin.model.dto.kisrequest.basicprice.mock.exchangeprice.KISExchangePriceRequestParameterDto
import com.knu.mockin.model.dto.kisrequest.basicprice.mock.termprice.KISTermPriceRequestParameterDto
import com.knu.mockin.model.dto.kisresponse.basicprice.mock.conditionsearch.KISConditionSearchResponseDto
import com.knu.mockin.model.dto.kisresponse.basicprice.mock.currentprice.KISCurrentPriceResponseDto
import com.knu.mockin.model.dto.kisresponse.basicprice.mock.exchangeprice.KISExchangePriceResponseDto
import com.knu.mockin.model.dto.kisresponse.basicprice.mock.termprice.KISTermPriceResponseDto
import com.knu.mockin.util.httpUtils
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Mono

@Component
class KISBasicMockClient (
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
                .headers { httpUtils.addHeaders(it, header) }
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
                .headers { httpUtils.addHeaders(it, header) }
                .retrieve()
                .bodyToMono(KISTermPriceResponseDto::class.java)
    }

    fun getExchangePrice(
            header: KISOverSeaRequestHeaderDto,
            requestParameter: KISExchangePriceRequestParameterDto
    ) : Mono<KISExchangePriceResponseDto> {
        return webClientMock.get()
                .uri{ UriBuilder ->
                    UriBuilder.path("${quotationUrl}/inquire-daily-chartprice")
                            .queryParam("FID_COND_MRKT_DIV_CODE", requestParameter.fidCondMrktDivCode)
                            .queryParam("FID_INPUT_ISCD", requestParameter.fidInputIscd)
                            .queryParam("FID_INPUT_DATE_1", requestParameter.fidInputDate1)
                            .queryParam("FID_INPUT_DATE_2", requestParameter.fidInputDate2)
                            .queryParam("FID_PERIOD_DIV_CODE", requestParameter.fidPeriodDivCode)
                            .build();
                }
                .headers { httpUtils.addHeaders(it, header) }
                .retrieve()
                .bodyToMono(KISExchangePriceResponseDto::class.java)
    }

    fun getConditionSearch(
            header: KISOverSeaRequestHeaderDto,
            requestParameter: KISConditionSearchRequestParameterDto
    ) : Mono<KISConditionSearchResponseDto> {
        return webClientMock.get()
                .uri{ UriBuilder ->
                    UriBuilder.path("${quotationUrl}/inquire-search")
                            .queryParam("AUTH", requestParameter.AUTH)
                            .queryParam("EXCD", requestParameter.EXCD)
                            .queryParam("CO_YN_PRICECUR", requestParameter.coYnPricecur)
                            .queryParam("CO_ST_PRICECUR", requestParameter.coStPricecur)
                            .queryParam("CO_EN_PRICECUR", requestParameter.coEnPricecur)
                            .queryParam("CO_YN_RATE", requestParameter.coYnRate)
                            .queryParam("CO_ST_RATE", requestParameter.coStRate)
                            .queryParam("CO_EN_RATE", requestParameter.coEnRate)
                            .queryParam("CO_YN_VALX", requestParameter.coYnValx)
                            .queryParam("CO_ST_VALX", requestParameter.coStValx)
                            .queryParam("CO_EN_VALX", requestParameter.coEnValx)
                            .queryParam("CO_YN_SHAR", requestParameter.coYnShar)
                            .queryParam("CO_ST_SHAR", requestParameter.coStShar)
                            .queryParam("CO_EN_SHAR", requestParameter.coEnShar)
                            .queryParam("CO_YN_VOLUME", requestParameter.coYnVolume)
                            .queryParam("CO_ST_VOLUME", requestParameter.coStVolume)
                            .queryParam("CO_EN_VOLUME", requestParameter.coEnVolume)
                            .queryParam("CO_YN_AMT", requestParameter.coYnAmt)
                            .queryParam("CO_ST_AMT", requestParameter.coStAmt)
                            .queryParam("CO_EN_AMT", requestParameter.coEnAmt)
                            .queryParam("CO_YN_EPS", requestParameter.coYnEps)
                            .queryParam("CO_ST_EPS", requestParameter.coStEps)
                            .queryParam("CO_EN_EPS", requestParameter.coEnEps)
                            .queryParam("CO_YN_PER", requestParameter.coYnPer)
                            .queryParam("CO_ST_PER", requestParameter.coStPer)
                            .queryParam("CO_EN_PER", requestParameter.coEnPer)
                            .queryParam("KEYB", requestParameter.KEYB)
                            .build()
                }
                .headers { httpUtils.addHeaders(it, header) }
                .retrieve()
                .bodyToMono(KISConditionSearchResponseDto::class.java)
    }

}