package com.knu.mockin.service.quotations.basic.mock

import com.knu.mockin.exception.ErrorCode
import com.knu.mockin.kisclient.quotations.basic.KISBasicClient
import com.knu.mockin.model.dto.kisheader.request.KISOverSeaRequestHeaderDto
import com.knu.mockin.model.dto.kisresponse.quotations.basic.mock.*
import com.knu.mockin.model.dto.request.quotations.basic.mock.*
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

    suspend fun getPrice(
        priceRequestParameterDto: PriceRequestParameterDto,
        email: String
    ): KISPriceResponseDto {
        val header = createHeader(email, TradeId.PRICE)

        val requestParameter = priceRequestParameterDto.asDomain()

        return kisBasicClient.getPrice(header, requestParameter).awaitSingle()
    }

    suspend fun getDailyPrice(
        dailyPriceRequestParameterDto: DailyPriceRequestParameterDto,
        email: String
    ): KISDailyPriceResponseDto {
        val header = createHeader(email, TradeId.DAILY_PRICE)
        val requestParameter = dailyPriceRequestParameterDto.asDomain()
        return kisBasicClient.getDailyPrice(header, requestParameter).awaitSingle()
    }

    suspend fun getInquireDailyChartPrice(
        inquireDailyChartPriceRequestParameterDto: InquireDailyChartPriceRequestParameterDto,
        email: String
    ): KISInquireDailyChartPriceResponseDto {
        val header = createHeader(email, TradeId.INQUIRE_DAILY_CHART_PRICE)
        val requestParameter = inquireDailyChartPriceRequestParameterDto.asDomain()
        return kisBasicClient.getInquireDailyChartPrice(header, requestParameter).awaitSingle()
    }

    suspend fun getInquireSearch(
        inquireSearchRequestParameterDto: InquireSearchRequestParameterDto,
        email: String
    ): KISInquireSearchResponseDto {
        val header = createHeader(email, TradeId.INQUIRE_SEARCH)
        val requestParameter = inquireSearchRequestParameterDto.asDomain()
        return kisBasicClient.getInquireSearch(header, requestParameter).awaitSingle()
    }
}
