package com.knu.mockin.service

import com.knu.mockin.kisclient.KISTradingClient
import com.knu.mockin.model.dto.kisheader.request.KISOverSeaRequestHeaderDto
import com.knu.mockin.model.dto.kisrequest.basicprice.conditionsearch.KISConditionSearchRequestParameter
import com.knu.mockin.model.dto.kisresponse.basicprice.conditionsearch.KISConditionSearchResponseDto
import com.knu.mockin.model.enum.TradeId
import com.knu.mockin.repository.UserRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class ConditionSearchService (
        private val kisTradingClient: KISTradingClient,
        private val userRepository: UserRepository
) {
    fun getConditionSearch(): Mono<KISConditionSearchResponseDto> {
        return userRepository.findById(1).flatMap { user ->
            // Request header 생성
            val kisOverSeaRequestHeaderDto = KISOverSeaRequestHeaderDto(
                    authorization = "Bearer ${user.token}",
                    appKey = user.appKey,
                    appSecret = user.appSecret,
                    transactionId = TradeId.CURRENT_PRICE.name // 적절한 거래 ID로 수정
            )

            // 요청 파라미터 생성
            val requestParameter = KISConditionSearchRequestParameter(
                    AUTH = "",
                    EXCD = "NAS",
                    coYnPricecur = "",
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

            kisTradingClient.getConditionSearch(kisOverSeaRequestHeaderDto, requestParameter)
        }
    }
}