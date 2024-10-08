package com.knu.mockin.service

import com.knu.mockin.kisclient.KISBasicMockClient
import com.knu.mockin.model.dto.kisheader.request.KISOverSeaRequestHeaderDto
import com.knu.mockin.model.dto.kisrequest.basicprice.mock.exchangeprice.KISExchangePriceRequestParameterDto
import com.knu.mockin.model.dto.kisresponse.basicprice.mock.exchangeprice.KISExchangePriceResponseDto
import com.knu.mockin.model.enum.TradeId
import com.knu.mockin.repository.UserRepository
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.stereotype.Service

@Service
class ExchangePriceService (
        private val kisBasicMockClient: KISBasicMockClient,
        private val userRepository: UserRepository
) {
    suspend fun getExchangePrice(): KISExchangePriceResponseDto {
        val user = userRepository.findById(1).awaitFirst()
            // Request header 생성
            val kisOverSeaRequestHeaderDto = KISOverSeaRequestHeaderDto(
                    authorization = "Bearer ${user.token}",
                    appKey = user.appKey,
                    appSecret = user.appSecret,
                    transactionId = TradeId.getTradeIdByEnum(TradeId.EXCHANGE_PRICE) // 적절한 거래 ID로 수정
            )

            // 요청 파라미터 생성
            val requestParameter = KISExchangePriceRequestParameterDto(
                    fidCondMrktDivCode = "N",
                    fidInputDate1 = "20220401",
                    fidInputDate2 = "20220613",
                    fidInputIscd = ".DJI",
                    fidPeriodDivCode = "D"
            )
            return  kisBasicMockClient.getExchangePrice(kisOverSeaRequestHeaderDto, requestParameter).awaitSingle()

    }
}