package com.knu.mockin.kisclient

import com.knu.mockin.model.dto.kisheader.request.KISOverSeaRequestHeaderDto
import com.knu.mockin.model.dto.kisrequest.trading.KISBalanceRequestDto
import com.knu.mockin.model.dto.kisrequest.trading.KISOrderRequestDto
import com.knu.mockin.model.dto.kisresponse.trading.KISBalanceResponseDto
import com.knu.mockin.model.dto.kisresponse.trading.KISOrderResponseDto
import com.knu.mockin.util.httpUtils.addHeaders
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Mono

@Component
class KISTradingClient(
    private val webClientMock: WebClient
) {
    private val tradingUrl = "/uapi/overseas-stock/v1/trading"

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