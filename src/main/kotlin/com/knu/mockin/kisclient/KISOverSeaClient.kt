package com.knu.mockin.kisclient

import com.knu.mockin.model.dto.kisheader.request.KISOverSeaRequestHeaderDto
import com.knu.mockin.model.dto.kisrequest.basicprice.currentprice.KISCurrentPriceRequestParameter
import com.knu.mockin.model.dto.kisrequest.order.KISOrderRequestDto
import com.knu.mockin.model.dto.kisresponse.KISBasicPrice.currentPrice.KISCurrentPriceResponseDto
import com.knu.mockin.model.dto.kisresponse.order.KISOverSeaResponseDto
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class KISOverSeaClient(
    private val webClientMock: WebClient
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
        return webClientMock.post()
                .uri("/uapi/overseas-price/v1/quotations/price")
                .headers { addHeaders(it, header)}
                .bodyValue(requestParameter)
                .retrieve()
                .bodyToMono(KISCurrentPriceResponseDto::class.java)
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