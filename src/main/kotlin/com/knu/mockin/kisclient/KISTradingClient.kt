package com.knu.mockin.kisclient

import com.knu.mockin.model.dto.kisheader.request.KISOverSeaRequestHeaderDto
import com.knu.mockin.model.dto.kisrequest.trading.*
import com.knu.mockin.model.dto.kisresponse.trading.*
import com.knu.mockin.util.HttpUtils.buildUri
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class KISTradingClient(
    private val webClientMock: WebClient
) {
    private val tradingUrl = "/uapi/overseas-stock/v1/trading"

    fun postOrder(
        headerDto: KISOverSeaRequestHeaderDto,
        bodyDto: KISOrderRequestBodyDto
    ): Mono<KISOrderResponseDto> {
        val uri = "${tradingUrl}/order"

        return webClientMock.postWithBody(
            uri = uri,
            headerDto = headerDto,
            bodyDto = bodyDto,
            responseType = KISOrderResponseDto::class.java
        )
    }

    fun postOrderReverse(
        headerDto: KISOverSeaRequestHeaderDto,
        bodyDto: KISOrderReverseRequestBodyDto
    ): Mono<KISOrderReverseResponseDto> {
        val uri = "${tradingUrl}/order-rvsecncl"

        return webClientMock.postWithBody(
            uri = uri,
            headerDto = headerDto,
            bodyDto = bodyDto,
            responseType = KISOrderReverseResponseDto::class.java
        )
    }

    fun getNCCS(
        headerDto: KISOverSeaRequestHeaderDto,
        parameterDto: KISNCCSRequestParameterDto
    ): Mono<KISNCCSResponseDto>{
        val targetUri = buildUri("${tradingUrl}/inquire-nccs", parameterDto)

        return webClientMock.getWithParams(
            uri = targetUri,
            headerDto = headerDto,
            responseType = KISNCCSResponseDto::class.java
        )
    }

    fun getBalance(
        headerDto: KISOverSeaRequestHeaderDto,
        parameterDto: KISBalanceRequestParameterDto
    ): Mono<KISBalanceResponseDto>{
        val targetUri = buildUri("${tradingUrl}/inquire-balance", parameterDto)

        return webClientMock.getWithParams(
            uri = targetUri,
            headerDto = headerDto,
            responseType = KISBalanceResponseDto::class.java
        )
    }

    fun getPsAmount(
        headerDto: KISOverSeaRequestHeaderDto,
        parameterDto: KISPsAmountRequestParameterDto
    ): Mono<KISPsAmountResponseDto>{
        val targetUri = buildUri("${tradingUrl}/inquire-psamount", parameterDto)

        return webClientMock.getWithParams(
            uri = targetUri,
            headerDto = headerDto,
            responseType = KISPsAmountResponseDto::class.java
        )
    }

    fun getPresentBalance(
        headerDto: KISOverSeaRequestHeaderDto,
        parameterDto: KISPresentBalanceRequestParameterDto
    ): Mono<KISPresentBalanceResponseDto>{
        val targetUri = buildUri("${tradingUrl}/inquire-psamount", parameterDto)

        return webClientMock.getWithParams(
            uri = targetUri,
            headerDto = headerDto,
            responseType = KISPresentBalanceResponseDto::class.java
        )
    }

    fun getCCNL(
        headerDto: KISOverSeaRequestHeaderDto,
        parameterDto: KISCCNLRequestParameterDto
    ): Mono<KISCCNLResponseDto>{
        val targetUri = buildUri("${tradingUrl}/inquire-ccnl", parameterDto)

        return webClientMock.getWithParams(
            uri = targetUri,
            headerDto = headerDto,
            responseType = KISCCNLResponseDto::class.java
        )
    }
}