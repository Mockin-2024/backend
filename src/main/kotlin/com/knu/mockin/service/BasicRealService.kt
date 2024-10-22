package com.knu.mockin.service

import com.knu.mockin.kisclient.KISBasicRealClient
import com.knu.mockin.model.dto.kisheader.request.KISOverSeaRequestHeaderDto
import com.knu.mockin.model.dto.kisrequest.basic.*
import com.knu.mockin.model.dto.kisresponse.basic.*
import com.knu.mockin.model.dto.request.basic.*
import com.knu.mockin.model.enum.TradeId
import com.knu.mockin.repository.RealKeyRepository
import com.knu.mockin.repository.UserRepository
import com.knu.mockin.util.RedisUtil
import com.knu.mockin.util.StringUtil
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.stereotype.Service

@Service
class BasicRealService (
        private val kisBasicRealClient: KISBasicRealClient,
        private val realKeyRepository: RealKeyRepository,
        private val userRepository: UserRepository
) {

    suspend fun getCountriesHoliday(
            countriesHolidayRequestParameterDto: CountriesHolidayRequestParameterDto
    ) : KISCountriesHolidayResponseDto {
        val mockKey = realKeyRepository.findByEmail(
                countriesHolidayRequestParameterDto.email).awaitFirst()
        val user = userRepository.findByEmail(
                countriesHolidayRequestParameterDto.email).awaitFirst()
        val kisOverSeaRequestHeaderDto = KISOverSeaRequestHeaderDto(
                authorization = "Bearer ${RedisUtil.getToken(StringUtil.appendRealSuffix(user.email))}",
                appKey = mockKey.appKey,
                appSecret = mockKey.appSecret,
                transactionId = TradeId.getTradeIdByEnum(TradeId.COUNTRIES_HOLIDAY)
        )

        val requestParameter = KISCountriesHolidayRequestParameterDto(
                tradDt = countriesHolidayRequestParameterDto.tradDt,
                ctxAreaNk = countriesHolidayRequestParameterDto.ctxAreaNk,
                ctxAreaFk = countriesHolidayRequestParameterDto.ctxAreaFk
        )

        return kisBasicRealClient.getCountriesHoliday(kisOverSeaRequestHeaderDto, requestParameter).awaitSingle()

    }

    suspend fun getPriceDetail(
            priceDetailRequestParameterDto: PriceDetailRequestParameterDto
    ): KISPriceDetailResponseDto {
        val mockKey = realKeyRepository.findByEmail(priceDetailRequestParameterDto.email).awaitFirst()
        val user = userRepository.findByEmail(priceDetailRequestParameterDto.email).awaitFirst()
        val kisOverSeaRequestHeaderDto = KISOverSeaRequestHeaderDto(
                authorization = "Bearer ${RedisUtil.getToken(StringUtil.appendRealSuffix(user.email))}",
                appKey = mockKey.appKey,
                appSecret = mockKey.appSecret,
                transactionId = TradeId.getTradeIdByEnum(TradeId.PRICE_DETAIL)
        )

        val requestParameter = KISPriceDetailRequestParameterDto(
                AUTH = priceDetailRequestParameterDto.AUTH,
                EXCD = priceDetailRequestParameterDto.EXCD,
                SYMB = priceDetailRequestParameterDto.SYMB
        )

        return kisBasicRealClient.getPriceDetail(kisOverSeaRequestHeaderDto, requestParameter).awaitSingle()
    }

    suspend fun getItemChartPrice(
            itemChartPriceRequestParameterDto: ItemChartPriceRequestParameterDto
    ): KISItemChartPriceResponseDto {
        val mockKey = realKeyRepository.findByEmail(itemChartPriceRequestParameterDto.email).awaitFirst()
        val user = userRepository.findByEmail(itemChartPriceRequestParameterDto.email).awaitFirst()
        val kisOverSeaRequestHeaderDto = KISOverSeaRequestHeaderDto(
                authorization = "Bearer ${RedisUtil.getToken(StringUtil.appendRealSuffix(user.email))}",
                appKey = mockKey.appKey,
                appSecret = mockKey.appSecret,
                transactionId = TradeId.getTradeIdByEnum(TradeId.ITEM_CHART_PRICE)
        )

        val requestParameter = KISItemChartPriceRequestParameterDto(
                AUTH = itemChartPriceRequestParameterDto.AUTH,
                EXCD = itemChartPriceRequestParameterDto.EXCD,
                SYMB = itemChartPriceRequestParameterDto.SYMB,
                NMIN = itemChartPriceRequestParameterDto.NMIN,
                PINC = itemChartPriceRequestParameterDto.PINC,
                NEXT = itemChartPriceRequestParameterDto.NEXT,
                NREC = itemChartPriceRequestParameterDto.NREC,
                FILL = itemChartPriceRequestParameterDto.FILL,
                KEYB = itemChartPriceRequestParameterDto.KEYB
        )

        return kisBasicRealClient.getItemChartPrice(kisOverSeaRequestHeaderDto, requestParameter).awaitSingle()
    }

    suspend fun getIndexChartPrice(
            indexChartPriceRequestParameterDto: IndexChartPriceRequestParameterDto
    ): KISIndexChartPriceResponseDto {
        val mockKey = realKeyRepository.findByEmail(indexChartPriceRequestParameterDto.email).awaitFirst()
        val user = userRepository.findByEmail(indexChartPriceRequestParameterDto.email).awaitFirst()
        val kisOverSeaRequestHeaderDto = KISOverSeaRequestHeaderDto(
                authorization = "Bearer ${RedisUtil.getToken(StringUtil.appendRealSuffix(user.email))}",
                appKey = mockKey.appKey,
                appSecret = mockKey.appSecret,
                transactionId = TradeId.getTradeIdByEnum(TradeId.INDEX_CHART_PRICE) // 거래 ID를 INDEX_CHART_PRICE로 변경
        )

        val requestParameter = KISIndexChartPriceRequestParameterDto(
                fidCondMrktDivCode = indexChartPriceRequestParameterDto.fidCondMrktDivCode,
                fidInputIscd = indexChartPriceRequestParameterDto.fidInputIscd,
                fidHourClsCode = indexChartPriceRequestParameterDto.fidHourClsCode,
                fidPwDataIncuYn = indexChartPriceRequestParameterDto.fidPwDataIncuYn
        )

        return kisBasicRealClient.getIndexChartPrice(kisOverSeaRequestHeaderDto, requestParameter).awaitSingle()
    }

    suspend fun getSearchInfo(
        searchInfoRequestParameterDto: SearchInfoRequestParameterDto
    ): KISSearchInfoResponseDto {
        val mockKey = realKeyRepository.findByEmail(searchInfoRequestParameterDto.email).awaitFirst()
        val user = userRepository.findByEmail(searchInfoRequestParameterDto.email).awaitFirst()
        val kisOverSeaRequestHeaderDto = KISOverSeaRequestHeaderDto(
            authorization = "Bearer ${RedisUtil.getToken(StringUtil.appendRealSuffix(user.email))}",
            appKey = mockKey.appKey,
            appSecret = mockKey.appSecret,
            transactionId = TradeId.getTradeIdByEnum(TradeId.SEARCH_INFO)
        )

        val requestParameter = KISSearchInfoRequestParameterDto(
            prdtTypeCd = searchInfoRequestParameterDto.prdtTypeCd,
            pdno = searchInfoRequestParameterDto.pdno
        )

        return kisBasicRealClient.getSearchInfo(kisOverSeaRequestHeaderDto, requestParameter).awaitSingle()
    }

}
