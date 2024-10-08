package com.knu.mockin.service

import com.knu.mockin.kisclient.KISBasicMockClient
import com.knu.mockin.model.dto.kisheader.request.KISOverSeaRequestHeaderDto
import com.knu.mockin.model.dto.kisrequest.basicprice.mock.conditionsearch.KISConditionSearchRequestParameterDto
import com.knu.mockin.model.dto.kisresponse.basicprice.mock.conditionsearch.KISConditionSearchResponseDto
import com.knu.mockin.model.enum.TradeId
import com.knu.mockin.repository.UserRepository
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service

@Service
class ConditionSearchService (
        private val kisBasicMockClient: KISBasicMockClient,
        private val userRepository: UserRepository
) {
    suspend fun getConditionSearch(): KISConditionSearchResponseDto {
        val user = userRepository.findById(1).awaitFirst()
            // Request header 생성
            val kisOverSeaRequestHeaderDto = KISOverSeaRequestHeaderDto(
                    authorization = "Bearer ${user.token}",
                    appKey = user.appKey,
                    appSecret = user.appSecret,
                    transactionId = TradeId.getTradeIdByEnum(TradeId.CONDITION_PRICE) // 적절한 거래 ID로 수정
            )

            // 요청 파라미터 생성
            val requestParameter = KISConditionSearchRequestParameterDto(
                    AUTH = "",
                    EXCD = "NAS",
                    coYnPricecur = "1",
                    coStPricecur = "160",
                    coEnPricecur = "161",
                    coYnRate = "",
                    coStRate = "",
                    coEnRate = "",
                    coYnValx = "",
                    coStValx = "",
                    coEnValx = "",
                    coYnShar = "",
                    coStShar = "",
                    coEnShar = "",
                    coYnVolume = "",
                    coStVolume = "",
                    coEnVolume = "",
                    coYnAmt = "",
                    coStAmt = "",
                    coEnAmt = "",
                    coYnEps = "",
                    coStEps = "",
                    coEnEps = "",
                    coYnPer = "",
                    coStPer = "",
                    coEnPer = "",
                    KEYB = ""
            )

            return kisBasicMockClient.getConditionSearch(kisOverSeaRequestHeaderDto, requestParameter).awaitSingle()

    }
}