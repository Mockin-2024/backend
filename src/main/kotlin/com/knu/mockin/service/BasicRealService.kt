package com.knu.mockin.service

import com.knu.mockin.kisclient.KISBasicRealClient
import com.knu.mockin.model.dto.kisheader.request.KISOverSeaRequestHeaderDto
import com.knu.mockin.model.dto.kisrequest.basic.KISCountriesHolidayRequestParameterDto
import com.knu.mockin.model.dto.kisrequest.basic.KISPriceDetailRequestParameterDto
import com.knu.mockin.model.dto.kisresponse.basic.KISCountriesHolidayResponseDto
import com.knu.mockin.model.dto.kisresponse.basic.KISPriceDetailResponseDto
import com.knu.mockin.model.dto.request.basic.CountriesHolidayRequestParameterDto
import com.knu.mockin.model.dto.request.basic.PriceDetailRequestParameterDto
import com.knu.mockin.model.enum.TradeId
import com.knu.mockin.repository.RealKeyRepository
import com.knu.mockin.repository.UserRepository
import com.knu.mockin.util.RedisUtil
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
                authorization = "Bearer ${RedisUtil.getToken(user.email)}",
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
                authorization = "Bearer ${RedisUtil.getToken(user.email)}",
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

}