package com.knu.mockin.kisclient

import com.knu.mockin.controller.TradingController
import com.knu.mockin.logging.model.LogAPIEntry
import com.knu.mockin.logging.utils.LogUtil
import com.knu.mockin.model.dto.kisheader.request.KISOverSeaRequestHeaderDto
import com.knu.mockin.model.dto.kisheader.response.KISOverSeaResponseHeaderDto
import com.knu.mockin.model.dto.kisrequest.trading.KISOrderRequestDto
import com.knu.mockin.model.dto.kisresponse.trading.KISOrderResponseDto
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class KISTradingClient(
    private val webClientMock: WebClient
) {

    private val log = LoggerFactory.getLogger(KISTradingClient::class.java)

    fun postOrder(
        kisOverSeaRequestHeaderDto: KISOverSeaRequestHeaderDto,
        kisOrderRequestDto: KISOrderRequestDto
    ): Mono<Pair<KISOverSeaResponseHeaderDto, KISOrderResponseDto>> {
        return webClientMock.post()
            .uri("/uapi/overseas-stock/v1/trading/order")
            .headers { addHeaders(it, kisOverSeaRequestHeaderDto)}
            .bodyValue(kisOrderRequestDto)
            .exchangeToMono { response ->

                val headerResponse = response.headers().asHttpHeaders()
                val bodyResponse = response.bodyToMono(KISOrderResponseDto::class.java)

                log.info("{headerResponse: {}}", headerResponse)
                log.info("{bodyResponse: {}}", bodyResponse)
                // 헤더에서 필요한 정보를 추출하여 KISOverSeaResponseHeaderDto로 변환
                val kisOverSeaResponseHeaderDto = extractHeaderDto(headerResponse)

                // 바디를 Mono에서 추출하고 두 개를 Pair로 묶음
                bodyResponse.map { body ->
                    Pair(kisOverSeaResponseHeaderDto, body) }
            }
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

    private fun extractHeaderDto(headers: HttpHeaders): KISOverSeaResponseHeaderDto {
        return KISOverSeaResponseHeaderDto(
            contentType = headers.getFirst("content-type") ?: "",
            transactionId = headers.getFirst("tr_id") ?: "",
            transactionContinuation = headers.getFirst("tr_cont") ?: "",
            globalUid = headers.getFirst("gt_uid") ?: ""
        )
    }
}