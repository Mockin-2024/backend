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
import com.knu.mockin.service.util.ServiceUtil.createHeader

@Service
class BasicService(
    private val kisBasicClient: KISBasicClient,
    private val userRepository: UserRepository,
) {

    suspend fun getPrice(
        priceRequestParameterDto: PriceRequestParameterDto,
        email: String
    ): KISPriceResponseDto {
        val userWithMockKey = getUser(email)

        val header = createHeader(userWithMockKey, TradeId.getTradeIdByEnum(TradeId.PRICE))
        val requestParameter = priceRequestParameterDto.asDomain()

        return kisBasicClient.getPrice(header, requestParameter).awaitSingle()
    }

    suspend fun getDailyPrice(
        dailyPriceRequestParameterDto: DailyPriceRequestParameterDto,
        email: String
    ): KISDailyPriceResponseDto {
        val userWithMockKey = getUser(email)

        val header = createHeader(userWithMockKey, TradeId.getTradeIdByEnum(TradeId.DAILY_PRICE))
        val requestParameter = dailyPriceRequestParameterDto.asDomain()

        return kisBasicClient.getDailyPrice(header, requestParameter).awaitSingle()
    }

    suspend fun getInquireDailyChartPrice(
        inquireDailyChartPriceRequestParameterDto: InquireDailyChartPriceRequestParameterDto,
        email: String
    ): KISInquireDailyChartPriceResponseDto {
        val userWithMockKey = getUser(email)

        val header = createHeader(userWithMockKey, TradeId.getTradeIdByEnum(TradeId.INQUIRE_DAILY_CHART_PRICE))
        val requestParameter = inquireDailyChartPriceRequestParameterDto.asDomain()

        return kisBasicClient.getInquireDailyChartPrice(header, requestParameter).awaitSingle()
    }

    suspend fun getInquireSearch(
        inquireSearchRequestParameterDto: InquireSearchRequestParameterDto,
        email: String
    ): KISInquireSearchResponseDto {
        val userWithMockKey = getUser(email)

        val header = createHeader(userWithMockKey, TradeId.getTradeIdByEnum(TradeId.INQUIRE_SEARCH))
        val requestParameter = inquireSearchRequestParameterDto.asDomain()

        return kisBasicClient.getInquireSearch(header, requestParameter).awaitSingle()
    }

    private suspend fun getUser(email: String) = userRepository.findByEmailWithMockKey(email)
        .orThrow(ErrorCode.USER_NOT_FOUND)
        .awaitFirst()
}
