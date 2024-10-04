package com.knu.mockin.kisclient

import com.knu.mockin.model.dto.kisheader.request.KISOverSeaRequestHeaderDto
import com.knu.mockin.model.dto.kisresponse.order.KISOverSeaResponseDto
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

class KISCurrentClosingPrice(
        private val webClientMock: WebClient
) {

    // 해외주식 현재 체결가 조회
    fun getStockPrice(
            kisOverSeaRequestHeaderDto: KISOverSeaRequestHeaderDto,
            exchangeCode: String,
            stockSymbol: String
    ): Mono<KISOverSeaResponseDto> {
        return webClientMock.get()
                .uri { builder ->
                    builder.path("/uapi/overseas-price/v1/quotations/price")
                            .queryParam("AUTH", "")
                            .queryParam("EXCD", exchangeCode)
                            .queryParam("SYMB", stockSymbol)
                            .build()
                }
                .headers { addHeaders(it, kisOverSeaRequestHeaderDto) }
                .retrieve()
                .bodyToMono(KISOverSeaResponseDto::class.java)
    }
}