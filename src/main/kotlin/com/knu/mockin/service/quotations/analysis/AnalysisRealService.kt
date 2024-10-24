package com.knu.mockin.service.quotations.analysis

import com.knu.mockin.exeption.ErrorCode
import com.knu.mockin.kisclient.KISBasicRealClient
import com.knu.mockin.model.dto.kisheader.request.KISOverSeaRequestHeaderDto
import com.knu.mockin.model.dto.kisrequest.quotations.analysis.KISNewsTitleRequestParameterDto
import com.knu.mockin.model.dto.kisresponse.quotations.analysis.KISNewsTitleResponseDto
import com.knu.mockin.model.dto.request.quotations.analysis.real.NewsTitleRequestParameterDto
import com.knu.mockin.model.enum.Constant
import com.knu.mockin.model.enum.TradeId
import com.knu.mockin.repository.RealKeyRepository
import com.knu.mockin.repository.UserRepository
import com.knu.mockin.util.ExtensionUtil.orThrow
import com.knu.mockin.util.RedisUtil
import com.knu.mockin.util.tag
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.stereotype.Service

@Service
class AnalysisRealService (
    private val kisBasicRealClient: KISBasicRealClient,
    private val userRepository: UserRepository
) {
    suspend fun getNewsTitle(
        newsTitleRequestParameterDto: NewsTitleRequestParameterDto,
        email: String
    ): KISNewsTitleResponseDto {
        val userWithKey =  userRepository.findByEmailWithRealKey(email)
            .orThrow(ErrorCode.USER_NOT_FOUND)
            .awaitFirst()
        val kisOverSeaRequestHeaderDto = KISOverSeaRequestHeaderDto(
            authorization = "Bearer ${RedisUtil.getToken(userWithKey.email tag Constant.REAL)}",
            appKey = userWithKey.appKey,
            appSecret = userWithKey.appSecret,
            transactionId = TradeId.getTradeIdByEnum(TradeId.NEWS_TITLE)
        )

        val requestParameter = KISNewsTitleRequestParameterDto(
            queryDate = newsTitleRequestParameterDto.queryDate,
            queryTime = newsTitleRequestParameterDto.queryTime
        )

        return kisBasicRealClient.getNewsTitle(kisOverSeaRequestHeaderDto, requestParameter).awaitSingle()
    }
}