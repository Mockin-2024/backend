package com.knu.mockin.service.quotations.basic.real

import com.knu.mockin.exeption.ErrorCode
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

    suspend fun getItemChartPrice(
        itemChartPriceRequestParameterDto: ItemChartPriceRequestParameterDto,
        email: String
    ): KISItemChartPriceResponseDto {
        val header = createHeader(email, TradeId.ITEM_CHART_PRICE)
        val requestParameter = itemChartPriceRequestParameterDto.asDomain()
        return kisBasicRealClient.getItemChartPrice(header, requestParameter).awaitSingle()
    }

    suspend fun getIndexChartPrice(
        indexChartPriceRequestParameterDto: IndexChartPriceRequestParameterDto,
        email: String
    ): KISIndexChartPriceResponseDto {
        val header = createHeader(email, TradeId.INDEX_CHART_PRICE)
        val requestParameter = indexChartPriceRequestParameterDto.asDomain()
        return kisBasicRealClient.getIndexChartPrice(header, requestParameter).awaitSingle()
    }

    suspend fun getSearchInfo(
        searchInfoRequestParameterDto: SearchInfoRequestParameterDto,
        email: String
    ): KISSearchInfoResponseDto {
        val header = createHeader(email, TradeId.SEARCH_INFO)
        val requestParameter = searchInfoRequestParameterDto.asDomain()
        return kisBasicRealClient.getSearchInfo(header, requestParameter).awaitSingle()
    }

}
