package com.knu.mockin.kisclient

import com.knu.mockin.model.dto.kisheader.request.KISOverSeaRequestHeaderDto
import com.knu.mockin.model.dto.kisrequest.basicprice.conditionsearch.KISConditionSearchRequestParameter
import com.knu.mockin.model.dto.kisrequest.basicprice.currentprice.KISCurrentPriceRequestParameter
import com.knu.mockin.model.dto.kisrequest.basicprice.exchangeprice.KISExchangePriceRequestParameter
import com.knu.mockin.model.dto.kisrequest.basicprice.termprice.KISTermPriceRequestParameter
import com.knu.mockin.model.dto.kisrequest.trading.KISBalanceRequestDto
import com.knu.mockin.model.dto.kisrequest.trading.KISOrderRequestDto
import com.knu.mockin.model.dto.kisresponse.basicprice.conditionsearch.KISConditionSearchResponseDto
import com.knu.mockin.model.dto.kisresponse.basicprice.currentprice.KISCurrentPriceResponseBodyDto
import com.knu.mockin.model.dto.kisresponse.basicprice.exchangeprice.KISExchangePriceResponseDto
import com.knu.mockin.model.dto.kisresponse.basicprice.termprice.KISTermPriceResponseDto
import com.knu.mockin.model.dto.kisresponse.trading.KISBalanceResponseDto
import com.knu.mockin.model.dto.kisresponse.trading.KISOrderResponseDto
import com.knu.mockin.util.httpUtils.addHeaders
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriBuilder
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Mono

@Component
class KISTradingClient(
    private val webClientMock: WebClient
) {
    private val tradingUrl = "/uapi/overseas-stock/v1/trading"
    private val tradingUrl1 = "/uapi/overseas-price/v1/quotations"

    fun postOrder(
        kisOverSeaRequestHeaderDto: KISOverSeaRequestHeaderDto,
        kisOrderRequestDto: KISOrderRequestDto
    ): Mono<KISOrderResponseDto> {
        return webClientMock.post()
            .uri("${tradingUrl}/order")
            .headers { addHeaders(it, kisOverSeaRequestHeaderDto) }
            .bodyValue(kisOrderRequestDto)
            .retrieve()
            .bodyToMono(KISOrderResponseDto::class.java)
    }

    fun getCurrentPrice(
            header: KISOverSeaRequestHeaderDto,
            requestParameter: KISCurrentPriceRequestParameter
    ) : Mono<KISCurrentPriceResponseBodyDto> {
        return webClientMock.get()
                .uri { UriBuilder ->
                    UriBuilder.path("${tradingUrl1}/price")
                            .queryParam("AUTH", requestParameter.AUTH)
                            .queryParam("SYMB", requestParameter.SYMB)
                            .queryParam("EXCD", requestParameter.EXCD)
                            .build()
                }
                .headers { addHeaders(it, header)}
                .retrieve()
                .bodyToMono(KISCurrentPriceResponseBodyDto::class.java)
    }

    fun getTermPrice(
            header: KISOverSeaRequestHeaderDto,
            requestParameter: KISTermPriceRequestParameter
    ) : Mono<KISTermPriceResponseDto> {
        return webClientMock.get()
                .uri{ UriBuilder ->
                    UriBuilder.path("${tradingUrl1}/dailyprice")
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
                    UriBuilder.path("${tradingUrl}/inquire-daily-chartprice")
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
                    UriBuilder.path("${tradingUrl}/inquire-search")
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

    fun getBalance(
        kisOverSeaRequestHeaderDto: KISOverSeaRequestHeaderDto,
        kisBalanceRequestDto: KISBalanceRequestDto
    ): Mono<KISBalanceResponseDto>{
        val uriBuilder = UriComponentsBuilder.fromUriString("${tradingUrl}/inquire-balance")
            .queryParam("CANO", kisBalanceRequestDto.accountNumber)
            .queryParam("ACNT_PRDT_CD", kisBalanceRequestDto.accountProductCode)
            .queryParam("OVRS_EXCG_CD", kisBalanceRequestDto.overseasExchangeCode)
            .queryParam("TR_CRCY_CD", kisBalanceRequestDto.transactionCurrencyCode)
            .queryParam("CTX_AREA_FK200", kisBalanceRequestDto.continuousSearchCondition200)
            .queryParam("CTX_AREA_NK200", kisBalanceRequestDto.continuousSearchKey200)

        return webClientMock.get()
            .uri(uriBuilder.build().toUriString())
            .headers { addHeaders(it, kisOverSeaRequestHeaderDto) }
            .retrieve()
            .bodyToMono(KISBalanceResponseDto::class.java)
    }
}