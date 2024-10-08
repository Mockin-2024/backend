package com.knu.mockin.service

import com.knu.mockin.kisclient.KISBasicRealClient
import com.knu.mockin.model.dto.kisheader.request.KISOverSeaRequestHeaderDto
import com.knu.mockin.model.dto.kisrequest.basicprice.real.countriesholiday.KISCountriesHolidayRequestParameterDto
import com.knu.mockin.model.dto.kisresponse.basicprice.real.countriesholiday.KISCountriesHolidayResponseDto
import com.knu.mockin.model.enum.TradeId
import com.knu.mockin.repository.UserRepository
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.stereotype.Service

@Service
class CountriesHolidayService (
        private val kisBasicRealClient: KISBasicRealClient,
        private val userRepository: UserRepository
) {

    suspend fun getCountriesHoliday() : KISCountriesHolidayResponseDto {
        val user = userRepository.findById(2).awaitFirst()
        val kisOverSeaRequestHeaderDto = KISOverSeaRequestHeaderDto(
                authorization = "Bearer ${user.token}",
                appKey = user.appKey,
                appSecret = user.appSecret,
                transactionId = TradeId.getTradeIdByEnum(TradeId.COUNTRIES_HOLIDAY) // 적절한 거래 ID로 수정
        )

        val requestParameter = KISCountriesHolidayRequestParameterDto(
                tradDt = "20221227",
                ctxAreaNk = "",
                ctxAreaFk = ""
        )
        return kisBasicRealClient.getCountriesHoliday(kisOverSeaRequestHeaderDto, requestParameter).awaitSingle()
    }

}