package com.knu.mockin.service

import com.knu.mockin.kisclient.KISTradingClient
import com.knu.mockin.model.dto.kisheader.request.KISOverSeaRequestHeaderDto
import com.knu.mockin.model.dto.kisrequest.basicprice.termprice.KISTermPriceRequestParameter
import com.knu.mockin.model.dto.kisresponse.basicprice.termprice.KISTermPriceResponseDto
import com.knu.mockin.model.enum.TradeId
import com.knu.mockin.repository.UserRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class TermPriceService (
        private val kisTradingClient: KISTradingClient,
        private val userRepository: UserRepository
) {
    fun getTermPrice(): Mono<KISTermPriceResponseDto> {
        return userRepository.findById(1).flatMap { user ->
            // Request header 생성
            val kisOverSeaRequestHeaderDto = KISOverSeaRequestHeaderDto(
                    authorization = "Bearer ${user.token}",
                    appKey = user.appKey,
                    appSecret = user.appSecret,
                    transactionId = TradeId.CURRENT_PRICE.name // 적절한 거래 ID로 수정
            )

            // 요청 파라미터 생성
            val requestParameter = KISTermPriceRequestParameter(
                    AUTH = "",  // 필요한 경우 AUTH 값을 설정
                    EXCD = "NAS",
                    SYMB = "TSLA",
                    GUBN = "0",    // 0:일, 1:주, 2:월
                    BYMD = "",     // 공란:현재날짜
                    MODP = "0",    // 0:수정주가미반영, 1:반영
                    KEYB = ""
            )

            // KIS API 호출
            kisTradingClient.getTermPrice(kisOverSeaRequestHeaderDto, requestParameter)
        }
    }
}