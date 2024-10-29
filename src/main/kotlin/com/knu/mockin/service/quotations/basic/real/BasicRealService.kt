package com.knu.mockin.service.quotations.basic.real

import com.knu.mockin.exception.ErrorCode
import com.knu.mockin.kisclient.quotations.basic.KISBasicRealClient
import com.knu.mockin.model.dto.kisheader.request.KISOverSeaRequestHeaderDto
import com.knu.mockin.model.dto.kisresponse.quotations.basic.real.*
import com.knu.mockin.model.dto.request.quotations.basic.real.*
import com.knu.mockin.model.enum.Constant.REAL
import com.knu.mockin.model.enum.TradeId
import com.knu.mockin.repository.UserRepository
import com.knu.mockin.util.ExtensionUtil.orThrow
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

    private suspend fun getUserWithKey(email: String) = userRepository.findByEmailWithRealKey(email)
        .orThrow(ErrorCode.USER_NOT_FOUND)
        .awaitFirst()

    private suspend fun createHeader(email: String, tradeId: TradeId): KISOverSeaRequestHeaderDto {
        val userWithKey = getUserWithKey(email)
        return KISOverSeaRequestHeaderDto(
            authorization = "Bearer ${RedisUtil.getToken(userWithKey.email tag REAL)}",
            appKey = userWithKey.appKey,
            appSecret = userWithKey.appSecret,
            transactionId = TradeId.getTradeIdByEnum(tradeId)
        )
    }

    suspend fun getCountriesHoliday(
        countriesHolidayRequestParameterDto: CountriesHolidayRequestParameterDto,
        email: String
    ): KISCountriesHolidayResponseDto {
        val header = createHeader(email, TradeId.COUNTRIES_HOLIDAY)
        val requestParameter = countriesHolidayRequestParameterDto.asDomain()
        return kisBasicRealClient.getCountriesHoliday(header, requestParameter).awaitSingle()
    }

    suspend fun getPriceDetail(
        priceDetailRequestParameterDto: PriceDetailRequestParameterDto,
        email: String
    ): KISPriceDetailResponseDto {
        val header = createHeader(email, TradeId.PRICE_DETAIL)
        val requestParameter = priceDetailRequestParameterDto.asDomain()
        return kisBasicRealClient.getPriceDetail(header, requestParameter).awaitSingle()
    }

    suspend fun getInquireTimeItemChartPrice(
        inquireTimeItemChartPriceRequestParameterDto: InquireTimeItemChartPriceRequestParameterDto,
        email: String
    ): KISInquireTimeItemChartPriceResponseDto {
        val header = createHeader(email, TradeId.INQUIRE_TIME_ITEM_CHART_PRICE)
        val requestParameter = inquireTimeItemChartPriceRequestParameterDto.asDomain()
        return kisBasicRealClient.getInquireTimeItemChartPrice(header, requestParameter).awaitSingle()
    }

    suspend fun getInquireTimeIndexChartPrice(
        inquireTimeIndexChartPriceRequestParameterDto: InquireTimeIndexChartPriceRequestParameterDto,
        email: String
    ): KISInquireTimeIndexChartPriceResponseDto {
        val header = createHeader(email, TradeId.INQUIRE_TIME_INDEX_CHART_PRICE)
        val requestParameter = inquireTimeIndexChartPriceRequestParameterDto.asDomain()
        return kisBasicRealClient.getInquireTimeIndexChartPrice(header, requestParameter).awaitSingle()
    }

    suspend fun getSearchInfo(
        searchInfoRequestParameterDto: SearchInfoRequestParameterDto,
        email: String
    ): KISSearchInfoResponseDto {
        val header = createHeader(email, TradeId.SEARCH_INFO)
        val requestParameter = searchInfoRequestParameterDto.asDomain()
        return kisBasicRealClient.getSearchInfo(header, requestParameter).awaitSingle()
    }


    suspend fun getInquireAskingPrice(
        inquireAskingPriceRequestParameterDto: InquireAskingPriceRequestParameterDto,
        email: String
    ): KISInquireAskingPriceResponseDto {
        val header = createHeader(email, TradeId.INQUIRE_ASKING_PRICE)
        val requestParameter = inquireAskingPriceRequestParameterDto.asDomain()
        return kisBasicRealClient.getInquireAskingPrice(header, requestParameter).awaitSingle()
    }

}
