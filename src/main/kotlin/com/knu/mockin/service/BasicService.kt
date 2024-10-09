package com.knu.mockin.service

import com.knu.mockin.kisclient.KISBasicClient
import com.knu.mockin.model.dto.kisheader.request.KISOverSeaRequestHeaderDto
import com.knu.mockin.model.dto.kisrequest.basic.KISCurrentPriceRequestParameterDto
import com.knu.mockin.model.dto.kisrequest.basic.KISTermPriceRequestParameterDto
import com.knu.mockin.model.dto.kisresponse.basic.KISCurrentPriceResponseDto
import com.knu.mockin.model.dto.kisresponse.basic.KISTermPriceResponseDto
import com.knu.mockin.model.enum.TradeId
import com.knu.mockin.repository.UserRepository
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.stereotype.Service

@Service
class BasicService (
        private val kisBasicClient: KISBasicClient,
        private val userRepository: UserRepository
) {
    suspend fun getCurrentPrice(): KISCurrentPriceResponseDto {
        val user = userRepository.findById(1).awaitFirst()
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
        return kisBasicClient.getCurrentPrice(kisOverSeaRequestHeaderDto, requestParameter).awaitSingle()
    }

    suspend fun getTermPrice(): KISTermPriceResponseDto {
        val user = userRepository.findById(1).awaitFirst()
        // Request header 생성
        val kisOverSeaRequestHeaderDto = KISOverSeaRequestHeaderDto(
                authorization = "Bearer ${user.token}",
                appKey = user.appKey,
                appSecret = user.appSecret,
                transactionId = TradeId.getTradeIdByEnum(TradeId.TERM_PRICE) // 적절한 거래 ID로 수정
        )

        // 요청 파라미터 생성
        val requestParameter = KISTermPriceRequestParameterDto(
                AUTH = "",  // 필요한 경우 AUTH 값을 설정
                EXCD = "NAS",
                SYMB = "TSLA",
                GUBN = "0",    // 0:일, 1:주, 2:월
                BYMD = "",     // 공란:현재날짜
                MODP = "0",    // 0:수정주가미반영, 1:반영
                KEYB = ""
        )

        // KIS API 호출
        return kisBasicClient.getTermPrice(kisOverSeaRequestHeaderDto, requestParameter).awaitSingle()

    }



}