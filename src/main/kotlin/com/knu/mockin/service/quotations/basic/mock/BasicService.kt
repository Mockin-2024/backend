package com.knu.mockin.service.quotations.basic.mock

import com.knu.mockin.exeption.ErrorCode
import com.knu.mockin.kisclient.KISBasicClient
import com.knu.mockin.model.dto.kisheader.request.KISOverSeaRequestHeaderDto
import com.knu.mockin.model.dto.kisrequest.quotations.basic.mock.*
import com.knu.mockin.model.dto.kisresponse.quotations.basic.mock.*
import com.knu.mockin.model.dto.request.quotations.basic.mock.*
import com.knu.mockin.model.enum.Constant
import com.knu.mockin.model.enum.Constant.MOCK
import com.knu.mockin.model.enum.TradeId
import com.knu.mockin.repository.UserRepository
import com.knu.mockin.util.ExtensionUtil.orThrow
import com.knu.mockin.util.RedisUtil
import com.knu.mockin.util.tag
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.stereotype.Service

@Service
class BasicService(
    private val kisBasicClient: KISBasicClient,
    private val userRepository: UserRepository,
) {

    private suspend fun getUserWithKey(email: String) = userRepository.findByEmailWithMockKey(email)
        .orThrow(ErrorCode.USER_NOT_FOUND)
        .awaitFirst()

    private suspend fun createHeader(email: String, tradeId: TradeId): KISOverSeaRequestHeaderDto {
        val userWithKey = getUserWithKey(email)
        return KISOverSeaRequestHeaderDto(
            authorization = "Bearer ${RedisUtil.getToken(userWithKey.email tag MOCK)}",
            appKey = userWithKey.appKey,
            appSecret = userWithKey.appSecret,
            transactionId = TradeId.getTradeIdByEnum(tradeId)
        )
    }

    suspend fun getCurrentPrice(
        currentPriceRequestParameterDto: CurrentPriceRequestParameterDto,
        email: String
    ): KISCurrentPriceResponseDto {
        val header = createHeader(email, TradeId.CURRENT_PRICE)

        val requestParameter = currentPriceRequestParameterDto.asDomain()

        return kisBasicClient.getCurrentPrice(header, requestParameter).awaitSingle()
    }

    suspend fun getTermPrice(
        termPriceRequestParameterDto: TermPriceRequestParameterDto,
        email: String
    ): KISTermPriceResponseDto {
        val header = createHeader(email, TradeId.TERM_PRICE)
        val requestParameter = termPriceRequestParameterDto.asDomain()
        return kisBasicClient.getTermPrice(header, requestParameter).awaitSingle()
    }

    suspend fun getDailyChartPrice(
        dailyChartPriceRequestParameterDto: DailyChartPriceRequestParameterDto,
        email: String
    ): KISDailyChartPriceResponseDto {
        val header = createHeader(email, TradeId.DAILY_CHART_PRICE)
        val requestParameter = dailyChartPriceRequestParameterDto.asDomain()
        return kisBasicClient.getDailyChartPrice(header, requestParameter).awaitSingle()
    }

    suspend fun getSearch(
        searchRequestParameterDto: SearchRequestParameterDto,
        email: String
    ): KISSearchResponseDto {
        val header = createHeader(email, TradeId.SEARCH)
        val requestParameter = searchRequestParameterDto.asDomain()
        return kisBasicClient.getSearch(header, requestParameter).awaitSingle()
    }
}
