package com.knu.mockin.service.quotations.analysis

import com.knu.mockin.exeption.ErrorCode
import com.knu.mockin.kisclient.quotations.analysis.KISAnalysisRealClient
import com.knu.mockin.kisclient.quotations.basic.KISBasicRealClient
import com.knu.mockin.model.dto.kisheader.request.KISOverSeaRequestHeaderDto
import com.knu.mockin.model.dto.kisresponse.quotations.analysis.KISNewsTitleResponseDto
import com.knu.mockin.model.dto.request.quotations.analysis.real.NewsTitleRequestParameterDto
import com.knu.mockin.model.dto.request.quotations.analysis.real.asDomain
import com.knu.mockin.model.enum.Constant
import com.knu.mockin.model.enum.TradeId
import com.knu.mockin.repository.UserRepository
import com.knu.mockin.util.ExtensionUtil.orThrow
import com.knu.mockin.util.RedisUtil
import com.knu.mockin.util.tag
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.stereotype.Service

@Service
class AnalysisRealService (
    private val kisAnalysisRealClient: KISAnalysisRealClient,
    private val userRepository: UserRepository
) {

    private suspend fun getUserWithKey(email: String) = userRepository.findByEmailWithRealKey(email)
        .orThrow(ErrorCode.USER_NOT_FOUND)
        .awaitFirst()

    private suspend fun createHeader(email: String, tradeId: TradeId): KISOverSeaRequestHeaderDto {
        val userWithKey = getUserWithKey(email)
        return KISOverSeaRequestHeaderDto(
            authorization = "Bearer ${RedisUtil.getToken(userWithKey.email tag Constant.REAL)}",
            appKey = userWithKey.appKey,
            appSecret = userWithKey.appSecret,
            transactionId = TradeId.getTradeIdByEnum(tradeId)
        )
    }

    suspend fun getNewsTitle(
        newsTitleRequestParameterDto: NewsTitleRequestParameterDto,
        email: String
    ): KISNewsTitleResponseDto {
        val header = createHeader(email, TradeId.NEWS_TITLE)
        val requestParameter = newsTitleRequestParameterDto.asDomain()
        return kisAnalysisRealClient.getNewsTitle(header, requestParameter).awaitSingle()
    }
}