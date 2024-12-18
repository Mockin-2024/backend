package com.knu.mockin.service.quotations.basic.real

import com.knu.mockin.exception.ErrorCode
import com.knu.mockin.kisclient.quotations.basic.KISBasicRealClient
import com.knu.mockin.model.dto.kisresponse.quotations.basic.real.*
import com.knu.mockin.model.dto.request.quotations.basic.real.*
import com.knu.mockin.model.enum.TimeConstant.ONE_HOUR
import com.knu.mockin.model.enum.TimeConstant.ONE_MINUTE
import com.knu.mockin.model.enum.TimeConstant.TWELVE_HOURS
import com.knu.mockin.model.enum.TradeId
import com.knu.mockin.repository.UserRepository
import com.knu.mockin.service.util.ServiceUtil.createHeader
import com.knu.mockin.util.ExtensionUtil.orThrow
import com.knu.mockin.util.ExtensionUtil.toDto
import com.knu.mockin.util.RedisUtil
import com.knu.mockin.util.tag
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.stereotype.Service

@Service
class BasicRealService (
        private val kisBasicRealClient: KISBasicRealClient,
        private val userRepository: UserRepository
) {

    suspend fun getCountriesHoliday(
        countriesHolidayRequestParameterDto: CountriesHolidayRequestParameterDto,
        email: String
    ): KISCountriesHolidayResponseDto {
        val userWithRealKey = getUser(email)

        val redisCacheKey = "getCountriesHoliday" tag countriesHolidayRequestParameterDto.tradDt

        val cachedValue = RedisUtil.getData(redisCacheKey)

        if (cachedValue != null) {
            return cachedValue toDto KISCountriesHolidayResponseDto::class.java
        }

        val header = createHeader(userWithRealKey, TradeId.getTradeIdByEnum(TradeId.COUNTRIES_HOLIDAY), true)
        val requestParameter = countriesHolidayRequestParameterDto.asDomain()

        val response = kisBasicRealClient.getCountriesHoliday(header, requestParameter).awaitSingle()

        RedisUtil.setData(redisCacheKey, response, ONE_HOUR)

        return response
    }

    suspend fun getPriceDetail(
        priceDetailRequestParameterDto: PriceDetailRequestParameterDto,
        email: String
    ): KISPriceDetailResponseDto {
        val userWithRealKey = getUser(email)

        val redisCacheKey = "getPriceDetail" tag priceDetailRequestParameterDto.SYMB

        val cachedValue = RedisUtil.getData(redisCacheKey)

        if (cachedValue != null) {
            return cachedValue toDto KISPriceDetailResponseDto::class.java
        }

        val header = createHeader(userWithRealKey, TradeId.getTradeIdByEnum(TradeId.PRICE_DETAIL), true)
        val requestParameter = priceDetailRequestParameterDto.asDomain()

        val response = kisBasicRealClient.getPriceDetail(header, requestParameter).awaitSingle()

        RedisUtil.setData(redisCacheKey, response, ONE_MINUTE)

        return response
    }

    suspend fun getInquireTimeItemChartPrice(
        inquireTimeItemChartPriceRequestParameterDto: InquireTimeItemChartPriceRequestParameterDto,
        email: String
    ): KISInquireTimeItemChartPriceResponseDto {
        val userWithRealKey = getUser(email)

        val redisCacheKey = "getInquireTimeItemChartPrice" tag
                inquireTimeItemChartPriceRequestParameterDto.SYMB tag
                inquireTimeItemChartPriceRequestParameterDto.NEXT tag
                inquireTimeItemChartPriceRequestParameterDto.KEYB

        val cachedValue = RedisUtil.getData(redisCacheKey)

        if (cachedValue != null) {
            return cachedValue toDto KISInquireTimeItemChartPriceResponseDto::class.java
        }

        val header = createHeader(userWithRealKey, TradeId.getTradeIdByEnum(TradeId.INQUIRE_TIME_ITEM_CHART_PRICE), true)
        val requestParameter = inquireTimeItemChartPriceRequestParameterDto.asDomain()

        val response = kisBasicRealClient.getInquireTimeItemChartPrice(header, requestParameter).awaitSingle()

        RedisUtil.setData(redisCacheKey, response, ONE_MINUTE)

        return response
    }

    suspend fun getInquireTimeIndexChartPrice(
        inquireTimeIndexChartPriceRequestParameterDto: InquireTimeIndexChartPriceRequestParameterDto,
        email: String
    ): KISInquireTimeIndexChartPriceResponseDto {
        val userWithRealKey = getUser(email)

        val redisCacheKey = "getInquireTimeIndexChartPrice" tag
                inquireTimeIndexChartPriceRequestParameterDto.fidInputIscd tag
                inquireTimeIndexChartPriceRequestParameterDto.fidCondMrktDivCode tag
                inquireTimeIndexChartPriceRequestParameterDto.fidPwDataIncuYn tag
                inquireTimeIndexChartPriceRequestParameterDto.fidHourClsCode


        val cachedValue = RedisUtil.getData(redisCacheKey)

        if (cachedValue != null) {
            return cachedValue toDto KISInquireTimeIndexChartPriceResponseDto::class.java
        }

        val header = createHeader(userWithRealKey, TradeId.getTradeIdByEnum(TradeId.INQUIRE_TIME_INDEX_CHART_PRICE), true)
        val requestParameter = inquireTimeIndexChartPriceRequestParameterDto.asDomain()

        val response = kisBasicRealClient.getInquireTimeIndexChartPrice(header, requestParameter).awaitSingle()

        RedisUtil.setData(redisCacheKey, response, ONE_MINUTE)

        return response
    }

    suspend fun getSearchInfo(
        searchInfoRequestParameterDto: SearchInfoRequestParameterDto,
        email: String
    ): KISSearchInfoResponseDto {
        val userWithRealKey = getUser(email)

        val redisCacheKey = "getSearchInfo" tag searchInfoRequestParameterDto.pdno

        val cachedValue = RedisUtil.getData(redisCacheKey)

        if (cachedValue != null) {
            return cachedValue toDto KISSearchInfoResponseDto::class.java
        }

        val header = createHeader(userWithRealKey, TradeId.getTradeIdByEnum(TradeId.SEARCH_INFO), true)
        val requestParameter = searchInfoRequestParameterDto.asDomain()

        val response = kisBasicRealClient.getSearchInfo(header, requestParameter).awaitSingle()

        RedisUtil.setData(redisCacheKey, response, TWELVE_HOURS)

        return response
    }


    suspend fun getInquireAskingPrice(
        inquireAskingPriceRequestParameterDto: InquireAskingPriceRequestParameterDto,
        email: String
    ): KISInquireAskingPriceResponseDto {
        val userWithRealKey = getUser(email)

        val redisCacheKey = "getInquireAskingPrice" tag inquireAskingPriceRequestParameterDto.SYMB

        val cachedValue = RedisUtil.getData(redisCacheKey)

        if (cachedValue != null) {
            return cachedValue toDto KISInquireAskingPriceResponseDto::class.java
        }

        val header = createHeader(userWithRealKey, TradeId.getTradeIdByEnum(TradeId.INQUIRE_ASKING_PRICE), true)
        val requestParameter = inquireAskingPriceRequestParameterDto.asDomain()

        val response = kisBasicRealClient.getInquireAskingPrice(header, requestParameter).awaitSingle()

        RedisUtil.setData(redisCacheKey, response, ONE_MINUTE)

        return response
    }

    private suspend fun getUser(email: String) = userRepository.findByEmailWithRealKey(email)
        .orThrow(ErrorCode.USER_NOT_FOUND)
        .awaitFirst()

}
