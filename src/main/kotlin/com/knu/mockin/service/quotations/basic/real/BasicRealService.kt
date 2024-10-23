package com.knu.mockin.service.quotations.basic.real

import com.knu.mockin.exeption.ErrorCode
import com.knu.mockin.kisclient.KISBasicRealClient
import com.knu.mockin.model.dto.kisheader.request.KISOverSeaRequestHeaderDto
import com.knu.mockin.model.dto.kisrequest.quotations.analysis.KISNewsTitleRequestParameterDto
import com.knu.mockin.model.dto.kisrequest.quotations.basic.real.*
import com.knu.mockin.model.dto.kisresponse.quotations.analysis.KISNewsTitleResponseDto
import com.knu.mockin.model.dto.kisresponse.quotations.basic.real.*
import com.knu.mockin.model.dto.request.quotations.analysis.real.NewsTitleRequestParameterDto
import com.knu.mockin.model.dto.request.quotations.basic.real.*
import com.knu.mockin.model.enum.Constant.REAL
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
class BasicRealService (
        private val kisBasicRealClient: KISBasicRealClient,
        private val userRepository: UserRepository
) {

    suspend fun getCountriesHoliday(
            countriesHolidayRequestParameterDto: CountriesHolidayRequestParameterDto,
            email: String
    ) : KISCountriesHolidayResponseDto {
        val userWithKey =  userRepository.findByEmailWithRealKey(email)
            .orThrow(ErrorCode.USER_NOT_FOUND)
            .awaitFirst()
        val kisOverSeaRequestHeaderDto = KISOverSeaRequestHeaderDto(
            authorization = "Bearer ${RedisUtil.getToken(userWithKey.email tag REAL)}",
            appKey = userWithKey.appKey,
            appSecret = userWithKey.appSecret,
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
            priceDetailRequestParameterDto: PriceDetailRequestParameterDto,
            email: String
    ): KISPriceDetailResponseDto {
        val userWithKey =  userRepository.findByEmailWithRealKey(email)
            .orThrow(ErrorCode.USER_NOT_FOUND)
            .awaitFirst()
        val kisOverSeaRequestHeaderDto = KISOverSeaRequestHeaderDto(
            authorization = "Bearer ${RedisUtil.getToken(userWithKey.email tag REAL)}",
            appKey = userWithKey.appKey,
            appSecret = userWithKey.appSecret,
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
            itemChartPriceRequestParameterDto: ItemChartPriceRequestParameterDto,
            email: String
    ): KISItemChartPriceResponseDto {
        val userWithKey =  userRepository.findByEmailWithRealKey(email)
            .orThrow(ErrorCode.USER_NOT_FOUND)
            .awaitFirst()
        val kisOverSeaRequestHeaderDto = KISOverSeaRequestHeaderDto(
            authorization = "Bearer ${RedisUtil.getToken(userWithKey.email tag REAL)}",
            appKey = userWithKey.appKey,
            appSecret = userWithKey.appSecret,
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
            indexChartPriceRequestParameterDto: IndexChartPriceRequestParameterDto,
            email: String
    ): KISIndexChartPriceResponseDto {
        val userWithKey =  userRepository.findByEmailWithRealKey(email)
            .orThrow(ErrorCode.USER_NOT_FOUND)
            .awaitFirst()
        val kisOverSeaRequestHeaderDto = KISOverSeaRequestHeaderDto(
            authorization = "Bearer ${RedisUtil.getToken(userWithKey.email tag REAL)}",
            appKey = userWithKey.appKey,
            appSecret = userWithKey.appSecret,
            transactionId = TradeId.getTradeIdByEnum(TradeId.INDEX_CHART_PRICE)
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
        searchInfoRequestParameterDto: SearchInfoRequestParameterDto,
        email: String
    ): KISSearchInfoResponseDto {
        val userWithKey =  userRepository.findByEmailWithRealKey(email)
            .orThrow(ErrorCode.USER_NOT_FOUND)
            .awaitFirst()
        val kisOverSeaRequestHeaderDto = KISOverSeaRequestHeaderDto(
            authorization = "Bearer ${RedisUtil.getToken(userWithKey.email tag REAL)}",
            appKey = userWithKey.appKey,
            appSecret = userWithKey.appSecret,
            transactionId = TradeId.getTradeIdByEnum(TradeId.SEARCH_INFO)
        )

        val requestParameter = KISSearchInfoRequestParameterDto(
            prdtTypeCd = searchInfoRequestParameterDto.prdtTypeCd,
            pdno = searchInfoRequestParameterDto.pdno
        )

        return kisBasicRealClient.getSearchInfo(kisOverSeaRequestHeaderDto, requestParameter).awaitSingle()
    }

}
