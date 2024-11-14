package com.knu.mockin.service.quotations.basic.mock

import com.knu.mockin.exception.ErrorCode
import com.knu.mockin.kisclient.quotations.basic.KISBasicClient
import com.knu.mockin.model.dto.kisheader.request.KISOverSeaRequestHeaderDto
import com.knu.mockin.model.dto.kisresponse.quotations.basic.mock.*
import com.knu.mockin.model.dto.request.quotations.basic.mock.*
import com.knu.mockin.model.enum.Constant.MOCK
import com.knu.mockin.model.enum.TimeConstant.FIFTEEN_MINUTES
import com.knu.mockin.model.enum.TimeConstant.ONE_MINUTE
import com.knu.mockin.model.enum.TimeConstant.THIRTY_MINUTES
import com.knu.mockin.model.enum.TradeId
import com.knu.mockin.repository.UserRepository
import com.knu.mockin.util.ExtensionUtil.orThrow
import com.knu.mockin.util.ExtensionUtil.toDto
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

        val redisCacheKey = "getPrice" tag priceRequestParameterDto.SYMB

        val cachedValue = RedisUtil.getData(redisCacheKey)

        if (cachedValue != null) {
            return cachedValue toDto KISPriceResponseDto::class.java
        }

        val header = createHeader(userWithMockKey, TradeId.getTradeIdByEnum(TradeId.PRICE))
        val requestParameter = priceRequestParameterDto.asDomain()

        val response = kisBasicClient
            .getPrice(header, requestParameter)
            .awaitSingle()
        RedisUtil.setData(redisCacheKey, response, ONE_MINUTE)

        return response
    }

    suspend fun getDailyPrice(
        dailyPriceRequestParameterDto: DailyPriceRequestParameterDto,
        email: String
    ): KISDailyPriceResponseDto {
        val userWithMockKey = getUser(email)

        val redisCacheKey = "getDailyPrice" tag
                dailyPriceRequestParameterDto.SYMB tag
                dailyPriceRequestParameterDto.GUBN tag
                dailyPriceRequestParameterDto.MODP tag
                dailyPriceRequestParameterDto.BYMD


        val cachedValue = RedisUtil.getData(redisCacheKey)

        if (cachedValue != null) {
            return cachedValue toDto KISDailyPriceResponseDto::class.java
        }

        val header = createHeader(userWithMockKey, TradeId.getTradeIdByEnum(TradeId.DAILY_PRICE))
        val requestParameter = dailyPriceRequestParameterDto.asDomain()

        val response = kisBasicClient.getDailyPrice(header, requestParameter).awaitSingle()

        RedisUtil.setData(redisCacheKey, response, FIFTEEN_MINUTES)

        return response
    }

    suspend fun getInquireDailyChartPrice(
        inquireDailyChartPriceRequestParameterDto: InquireDailyChartPriceRequestParameterDto,
        email: String
    ): KISInquireDailyChartPriceResponseDto {
        val userWithMockKey = getUser(email)

        val redisCacheKey = "getInquireDailyChartPrice" tag
                inquireDailyChartPriceRequestParameterDto.fidInputIscd tag
                inquireDailyChartPriceRequestParameterDto.fidCondMrktDivCode tag
                inquireDailyChartPriceRequestParameterDto.fidPeriodDivCode tag
                inquireDailyChartPriceRequestParameterDto.fidInputDate1 tag
                inquireDailyChartPriceRequestParameterDto.fidInputDate2

        val cachedValue = RedisUtil.getData(redisCacheKey)

        if (cachedValue != null) {
            return cachedValue toDto KISInquireDailyChartPriceResponseDto::class.java
        }

        val header = createHeader(userWithMockKey, TradeId.getTradeIdByEnum(TradeId.INQUIRE_DAILY_CHART_PRICE))
        val requestParameter = inquireDailyChartPriceRequestParameterDto.asDomain()

        val response = kisBasicClient.getInquireDailyChartPrice(header, requestParameter).awaitSingle()

        RedisUtil.setData(redisCacheKey, response, FIFTEEN_MINUTES)

        return response
    }

    suspend fun getInquireSearch(
        inquireSearchRequestParameterDto: InquireSearchRequestParameterDto,
        email: String
    ): KISInquireSearchResponseDto {
        val userWithMockKey = getUser(email)

        val header = createHeader(userWithMockKey, TradeId.getTradeIdByEnum(TradeId.INQUIRE_SEARCH))
        val requestParameter = inquireSearchRequestParameterDto.asDomain()

        val response = kisBasicClient.getInquireSearch(header, requestParameter).awaitSingle()

        return response
    }

    private suspend fun getUser(email: String) = userRepository.findByEmailWithMockKey(email)
        .orThrow(ErrorCode.USER_NOT_FOUND)
        .awaitFirst()
}
