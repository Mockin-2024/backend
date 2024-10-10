package com.knu.mockin.kisclient

import com.knu.mockin.model.dto.kisheader.request.KISOverSeaRequestHeaderDto
import com.knu.mockin.model.dto.kisrequest.trading.*
import com.knu.mockin.model.dto.kisresponse.trading.*
import com.knu.mockin.util.HttpUtils.addHeaders
import com.knu.mockin.util.HttpUtils.buildUri
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Mono

@Component
class KISTradingClient(
    private val webClientMock: WebClient
) {
    private val tradingUrl = "/uapi/overseas-stock/v1/trading"

    fun postOrder(
        kisOverSeaRequestHeaderDto: KISOverSeaRequestHeaderDto,
        kisOrderRequestBodyDto: KISOrderRequestBodyDto
    ): Mono<KISOrderResponseDto> {
        return webClientMock.post()
            .uri("${tradingUrl}/order")
            .headers { addHeaders(it, kisOverSeaRequestHeaderDto) }
            .bodyValue(kisOrderRequestBodyDto)
            .retrieve()
            .bodyToMono(KISOrderResponseDto::class.java)
    }

    fun getNCCS(
        kisOverSeaRequestHeaderDto: KISOverSeaRequestHeaderDto,
        kisnccsRequestParameterDto: KISNCCSRequestParameterDto
    ): Mono<KISNCCSResponseDto>{
        val targetUri = buildUri("${tradingUrl}/inquire-nccs", kisnccsRequestParameterDto)

        return webClientMock.get()
            .uri(targetUri)
            .headers{ addHeaders(it, kisOverSeaRequestHeaderDto) }
            .retrieve()
            .bodyToMono(KISNCCSResponseDto::class.java)
    }

    fun getBalance(
        kisOverSeaRequestHeaderDto: KISOverSeaRequestHeaderDto,
        kisBalanceRequestParameterDto: KISBalanceRequestParameterDto
    ): Mono<KISBalanceResponseDto>{
        val targetUri = buildUri("${tradingUrl}/inquire-balance", kisBalanceRequestParameterDto)

        return webClientMock.get()
            .uri(targetUri)
            .headers { addHeaders(it, kisOverSeaRequestHeaderDto) }
            .retrieve()
            .bodyToMono(KISBalanceResponseDto::class.java)
    }

    fun getPsAmount(
        kisOverSeaRequestHeaderDto: KISOverSeaRequestHeaderDto,
        kisPsAmountRequestParameterDto: KISPsAmountRequestParameterDto
    ): Mono<KISPsAmountResponseDto>{
        val targetUri = buildUri("${tradingUrl}/inquire-psamount", kisPsAmountRequestParameterDto)

        return webClientMock.get()
            .uri(targetUri)
            .headers { addHeaders(it, kisOverSeaRequestHeaderDto) }
            .retrieve()
            .bodyToMono(KISPsAmountResponseDto::class.java)
    }

    fun getPresentBalance(
        kisOverSeaRequestHeaderDto: KISOverSeaRequestHeaderDto,
        kisPresentBalanceRequestParameterDto: KISPresentBalanceRequestParameterDto
    ): Mono<KISPresentBalanceResponseDto>{
        val targetUri = buildUri("${tradingUrl}/inquire-psamount", kisPresentBalanceRequestParameterDto)

        return webClientMock.get()
            .uri(targetUri)
            .headers { addHeaders(it, kisOverSeaRequestHeaderDto) }
            .retrieve()
            .bodyToMono(KISPresentBalanceResponseDto::class.java)
    }
}