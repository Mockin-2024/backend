package com.knu.mockin.kisclient

import com.knu.mockin.model.dto.kisheader.request.KISOverSeaRequestHeaderDto
import com.knu.mockin.model.dto.kisrequest.basicprice.conditionsearch.KISConditionSearchRequestParameter
import com.knu.mockin.model.dto.kisrequest.basicprice.currentprice.KISCurrentPriceRequestParameter
import com.knu.mockin.model.dto.kisrequest.basicprice.exchangeprice.KISExchangePriceRequestParameter
import com.knu.mockin.model.dto.kisrequest.basicprice.termprice.KISTermPriceRequestParameter
import com.knu.mockin.model.dto.kisrequest.order.KISOrderRequestDto
import com.knu.mockin.model.dto.kisresponse.basicprice.conditionsearch.KISConditionSearchResponseDto
import com.knu.mockin.model.dto.kisresponse.basicprice.currentprice.KISCurrentPriceResponseDto
import com.knu.mockin.model.dto.kisresponse.basicprice.exchangeprice.KISExchangePriceResponseDto
import com.knu.mockin.model.dto.kisresponse.basicprice.termprice.KISTermPriceResponseDto
import com.knu.mockin.model.dto.kisresponse.order.KISOverSeaResponseDto
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriBuilder
import reactor.core.publisher.Mono

@Component
class KISOverSeaClient(
    private val webClientMock: WebClient,
) {
    fun postOrder(
        kisOverSeaRequestHeaderDto: KISOverSeaRequestHeaderDto,
        kisOrderRequestDto: KISOrderRequestDto
    ): Mono<KISOverSeaResponseDto> {
        return webClientMock.post()
            .uri("/uapi/overseas-stock/v1/trading/order")
            .headers { addHeaders(it, kisOverSeaRequestHeaderDto)}
            .bodyValue(kisOrderRequestDto)
            .retrieve()
            .bodyToMono(KISOverSeaResponseDto::class.java)
    }

    fun getCurrentPrice(
            header: KISOverSeaRequestHeaderDto,
            requestParameter: KISCurrentPriceRequestParameter
    ) : Mono<KISCurrentPriceResponseDto> {
        return webClientMock.get()
                .uri{UriBuilder ->
                    UriBuilder.path("/uapi/overseas-price/v1/quotations/price")
                            .queryParam("AUTH", requestParameter.AUTH)
                            .queryParam("EXCD", requestParameter.EXCD)
                            .queryParam("SYMB", requestParameter.SYMB)
                            .build()
                }
                .headers { addHeaders(it, header)}
                .retrieve()
                .bodyToMono(KISCurrentPriceResponseDto::class.java)
    }

    fun getTermPrice(
            header: KISOverSeaRequestHeaderDto,
            requestParameter: KISTermPriceRequestParameter
    ) : Mono<KISTermPriceResponseDto> {
        return webClientMock.get()
                .uri{ UriBuilder ->
                    UriBuilder.path("/uapi/overseas-price/v1/quotations/dailyprice")
                            .queryParam("AUTH", requestParameter.AUTH)
                            .queryParam("EXCD", requestParameter.EXCD)
                            .queryParam("SYMB", requestParameter.SYMB)
                            .queryParam("GUBN", requestParameter.GUBN)
                            .queryParam("BYMD", requestParameter.BYMD)
                            .queryParam("MODP", requestParameter.MODP)
                            .build()
                }
                .headers { addHeaders(it, header)}
                .retrieve()
                .bodyToMono(KISTermPriceResponseDto::class.java)
    }

    fun getExchangePrice(
            header: KISOverSeaRequestHeaderDto,
            requestParameter: KISExchangePriceRequestParameter
    ) : Mono<KISExchangePriceResponseDto> {
        return webClientMock.get()
                .uri{ UriBuilder ->
                    UriBuilder.path("/uapi/overseas-price/v1/quotations/inquire-daily-chartprice")
                            .queryParam("FID_COND_MRKT_DIV_CODE", requestParameter.fidCondMrktDivCode)
                            .queryParam("FID_INPUT_ISCD", requestParameter.fidInputIscd)
                            .queryParam("FID_INPUT_DATE_1", requestParameter.fidInputDate1)
                            .queryParam("FID_INPUT_DATE_2", requestParameter.fidInputDate2)
                            .queryParam("FID_PERIOD_DIV_CODE", requestParameter.fidPeriodDivCode)
                            .build();
                }
                .headers { addHeaders(it, header)}
                .retrieve()
                .bodyToMono(KISExchangePriceResponseDto::class.java)
    }

    fun getConditionSearch(
            header: KISOverSeaRequestHeaderDto,
            requestParameter: KISConditionSearchRequestParameter
    ) : Mono<KISConditionSearchResponseDto> {
        return webClientMock.get()
                .uri{ UriBuilder ->
                    UriBuilder.path("/uapi/overseas-price/v1/quotations/inquire-search")
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
                .headers { addHeaders(it, header)}
                .retrieve()
                .bodyToMono(KISConditionSearchResponseDto::class.java)
    }

    private fun addHeaders(headers: HttpHeaders, headerDto: KISOverSeaRequestHeaderDto) {
        headers["Content-Type"] = headerDto.contentType ?: "application/json"
        headers["Authorization"] = headerDto.authorization
        headers["AppKey"] = headerDto.appKey
        headers["AppSecret"] = headerDto.appSecret
        headerDto.personalSecKey?.let { headers["PersonalSecKey"] = it }
        headers["TrId"] = headerDto.transactionId
        headerDto.transactionContinuation?.let { headers["TrCont"] = it }
        headerDto.customerType?.let { headers["CustType"] = it }
        headerDto.sequenceNumber?.let { headers["SeqNo"] = it }
        headerDto.macAddress?.let { headers["MacAddress"] = it }
        headerDto.phoneNumber?.let { headers["PhoneNumber"] = it }
        headerDto.ipAddress?.let { headers["IpAddr"] = it }
        headerDto.hashKey?.let { headers["HashKey"] = it }
        headerDto.globalUid?.let { headers["GtUid"] = it }
    }
}