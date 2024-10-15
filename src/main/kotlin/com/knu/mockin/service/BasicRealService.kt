package com.knu.mockin.service

import com.knu.mockin.kisclient.KISBasicRealClient
import com.knu.mockin.model.dto.kisheader.request.KISOverSeaRequestHeaderDto
import com.knu.mockin.model.dto.kisrequest.basic.KISCountriesHolidayRequestParameterDto
import com.knu.mockin.model.dto.kisrequest.basic.KISIndexChartPriceRequestParameterDto
import com.knu.mockin.model.dto.kisrequest.basic.KISItemChartPriceRequestParameterDto
import com.knu.mockin.model.dto.kisrequest.basic.KISPriceDetailRequestParameterDto
import com.knu.mockin.model.dto.kisresponse.basic.KISCountriesHolidayResponseDto
import com.knu.mockin.model.dto.kisresponse.basic.KISIndexChartPriceResponseDto
import com.knu.mockin.model.dto.kisresponse.basic.KISItemChartPriceResponseDto
import com.knu.mockin.model.dto.kisresponse.basic.KISPriceDetailResponseDto
import com.knu.mockin.model.dto.request.basic.CountriesHolidayRequestParameterDto
import com.knu.mockin.model.dto.request.basic.IndexChartPriceRequestParameterDto
import com.knu.mockin.model.dto.request.basic.ItemChartPriceRequestParameterDto
import com.knu.mockin.model.dto.request.basic.PriceDetailRequestParameterDto
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

    fun appendRealSuffix(email: String): String {
        return "${email}-real"
    }

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

}