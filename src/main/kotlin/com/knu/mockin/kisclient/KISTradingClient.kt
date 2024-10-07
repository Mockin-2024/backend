package com.knu.mockin.kisclient

import com.knu.mockin.model.dto.kisheader.request.KISOverSeaRequestHeaderDto
import com.knu.mockin.model.dto.kisheader.response.KISOverSeaResponseHeaderDto
import com.knu.mockin.model.dto.kisrequest.trading.KISBalanceRequestDto
import com.knu.mockin.model.dto.kisrequest.trading.KISOrderRequestDto
import com.knu.mockin.model.dto.kisresponse.trading.KISBalanceResponseDto
import com.knu.mockin.model.dto.kisresponse.trading.KISOrderResponseDto
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Mono

@Component
class KISTradingClient(
    private val webClientMock: WebClient
) {

    private val log = LoggerFactory.getLogger(KISTradingClient::class.java)
    private val tradingUrl = "/uapi/overseas-stock/v1/trading"
    fun postOrder(
        kisOverSeaRequestHeaderDto: KISOverSeaRequestHeaderDto,
        kisOrderRequestDto: KISOrderRequestDto
    ): Mono<Pair<KISOverSeaResponseHeaderDto, KISOrderResponseDto>> {
        return webClientMock.post()
            .uri("${tradingUrl}/order")
            .headers { addHeaders(it, kisOverSeaRequestHeaderDto)}
            .bodyValue(kisOrderRequestDto)
            .exchangeToMono { response ->
                val headerResponse = response.headers().asHttpHeaders()
                val bodyResponse = response.bodyToMono(KISOrderResponseDto::class.java)

                // 헤더에서 필요한 정보를 추출하여 KISOverSeaResponseHeaderDto로 변환
                val kisOverSeaResponseHeaderDto = extractHeaderDto(headerResponse)

                // 바디를 Mono에서 추출하고 두 개를 Pair로 묶음
                bodyResponse.map { body ->
                    Pair(kisOverSeaResponseHeaderDto, body) }
            }
    }

    fun getBalance(
        kisOverSeaRequestHeaderDto: KISOverSeaRequestHeaderDto,
        kisBalanceRequestDto: KISBalanceRequestDto
    ): Mono<Pair<KISOverSeaResponseHeaderDto, KISBalanceResponseDto>>{
        val uriBuilder = UriComponentsBuilder.fromUriString("${tradingUrl}/balance")
            .queryParam("CANO", kisBalanceRequestDto.accountNumber)
            .queryParam("ACNT_PRDT_CD", kisBalanceRequestDto.accountProductCode)
            .queryParam("OVRS_EXCG_CD", kisBalanceRequestDto.overseasExchangeCode)
            .queryParam("TR_CRCY_CD", kisBalanceRequestDto.transactionCurrencyCode)
            .queryParam("CTX_AREA_FK200", kisBalanceRequestDto.continuousSearchCondition200)
            .queryParam("CTX_AREA_NK200", kisBalanceRequestDto.continuousSearchKey200)

        return webClientMock.get()
            .uri(uriBuilder.build().toUriString())
            .exchangeToMono { response ->
                val headerResponse = response.headers().asHttpHeaders()
                val bodyResponse = response.bodyToMono(KISBalanceResponseDto::class.java)

                // 헤더에서 필요한 정보를 추출하여 KISOverSeaResponseHeaderDto로 변환
                val kisOverSeaResponseHeaderDto = extractHeaderDto(headerResponse)

                // 바디를 Mono에서 추출하고 두 개를 Pair로 묶음
                bodyResponse.map { body ->
                    Pair(kisOverSeaResponseHeaderDto, body) }
            }

    }

    private fun addHeaders(headers: HttpHeaders, headerDto: KISOverSeaRequestHeaderDto) {
        headers["Content-Type"] = headerDto.contentType
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