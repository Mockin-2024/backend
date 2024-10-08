package com.knu.mockin.service

import com.knu.mockin.kisclient.KISBasicMockClient
import com.knu.mockin.model.dto.kisheader.request.KISOverSeaRequestHeaderDto
import com.knu.mockin.model.dto.kisrequest.basicprice.mock.currentprice.KISCurrentPriceRequestParameterDto
import com.knu.mockin.model.dto.kisresponse.basicprice.mock.currentprice.KISCurrentPriceResponseDto
import com.knu.mockin.model.enum.TradeId
import com.knu.mockin.repository.UserRepository
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.stereotype.Service

@Service
class CurrentPriceService(
        private val kisBasicMockClient: KISBasicMockClient,
        private val userRepository: UserRepository
) {
    suspend fun getCurrentPrice(): KISCurrentPriceResponseDto {
            val user = userRepository.findById(1).awaitFirst();
            // Request header 생성
            val kisOverSeaRequestHeaderDto = KISOverSeaRequestHeaderDto(
                    authorization = "Bearer ${user.token}",
                    appKey = user.appKey,
                    appSecret = user.appSecret,
                    transactionId = TradeId.getTradeIdByEnum(TradeId.CURRENT_PRICE) // 적절한 거래 ID로 수정
            )

            // 요청 파라미터 생성
            val requestParameter = KISCurrentPriceRequestParameterDto(
                      // 필요한 경우 AUTH 값을 설정
                    EXCD = "NAS",
                    SYMB = "TSLA"
            )

            // KIS API 호출
            return kisBasicMockClient.getCurrentPrice(kisOverSeaRequestHeaderDto, requestParameter).awaitSingle()
    }
}