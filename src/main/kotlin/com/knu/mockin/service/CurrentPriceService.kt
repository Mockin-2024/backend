package com.knu.mockin.service

import com.knu.mockin.kisclient.KISOverSeaClient
import com.knu.mockin.model.dto.kisheader.request.KISOverSeaRequestHeaderDto
import com.knu.mockin.model.dto.kisrequest.basicprice.currentprice.KISCurrentPriceRequestParameter
import com.knu.mockin.model.dto.kisresponse.KISBasicPrice.currentPrice.KISCurrentPriceResponseDto
import com.knu.mockin.model.enum.TradeId
import com.knu.mockin.repository.UserRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class CurrentPriceService(
        private val kisOverSeaClient: KISOverSeaClient,
        private val userRepository: UserRepository
) {
    fun getCurrentPrice(): Mono<KISCurrentPriceResponseDto> {
        return userRepository.findById(1).flatMap { user ->
            // Request header 생성
            val kisOverSeaRequestHeaderDto = KISOverSeaRequestHeaderDto(
                    authorization = "Bearer ${user.token}",
                    appKey = user.appKey,
                    appSecret = user.appSecret,
                    transactionId = TradeId.CURRENT_PRICE.name // 적절한 거래 ID로 수정
            )

            // 요청 파라미터 생성
            val requestParameter = KISCurrentPriceRequestParameter(
                    AUTH = "",  // 필요한 경우 AUTH 값을 설정
                    EXCD = "NAS",
                    SYMB = "TSLA"
            )

            // KIS API 호출
            kisOverSeaClient.getCurrentPrice(kisOverSeaRequestHeaderDto, requestParameter)
        }
    }
}