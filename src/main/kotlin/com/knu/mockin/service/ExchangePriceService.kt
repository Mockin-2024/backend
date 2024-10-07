package com.knu.mockin.service

import com.knu.mockin.kisclient.KISOverSeaClient
import com.knu.mockin.model.dto.kisheader.request.KISOverSeaRequestHeaderDto
import com.knu.mockin.model.dto.kisrequest.basicprice.exchangeprice.KISExchangePriceRequestParameter
import com.knu.mockin.model.dto.kisrequest.basicprice.termprice.KISTermPriceRequestParameter
import com.knu.mockin.model.dto.kisresponse.basicprice.exchangeprice.KISExchangePriceResponseDto
import com.knu.mockin.model.enum.TradeId
import com.knu.mockin.repository.UserRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class ExchangePriceService (
        private val kisOverSeaClient: KISOverSeaClient,
        private val userRepository: UserRepository
) {
    fun getExchangePrice(): Mono<KISExchangePriceResponseDto> {
        return userRepository.findById(1).flatMap { user ->
            // Request header 생성
            val kisOverSeaRequestHeaderDto = KISOverSeaRequestHeaderDto(
                    authorization = "Bearer ${user.token}",
                    appKey = user.appKey,
                    appSecret = user.appSecret,
                    transactionId = TradeId.CURRENT_PRICE.name // 적절한 거래 ID로 수정
            )

            // 요청 파라미터 생성
            val requestParameter = KISExchangePriceRequestParameter(
                    fidCondMrktDivCode = "N",
                    fidInputDate1 = "20220401",
                    fidInputDate2 = "20220613",
                    fidInputIscd = ".DJI",
                    fidPeriodDivCode = "D"
            )
            kisOverSeaClient.getExchangePrice(kisOverSeaRequestHeaderDto, requestParameter)
        }
    }
}