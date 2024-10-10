package com.knu.mockin.service

import com.knu.mockin.kisclient.KISBasicRealClient
import com.knu.mockin.model.dto.kisheader.request.KISOverSeaRequestHeaderDto
import com.knu.mockin.model.dto.kisrequest.basic.real.KISCountriesHolidayRequestParameterDto
import com.knu.mockin.model.dto.kisresponse.basic.mock.KISCurrentPriceResponseDto
import com.knu.mockin.model.dto.kisresponse.basic.real.KISCountriesHolidayResponseDto
import com.knu.mockin.model.enum.TradeId
import com.knu.mockin.repository.UserRepository
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.stereotype.Service

@Service
class BasicRealService (
        private val kisBasicRealClient: KISBasicRealClient,
        private val userRepository: UserRepository
) {
    suspend fun getCountriesHoliday(): KISCountriesHolidayResponseDto {
        val user = userRepository.findById(1).awaitFirst()

        val kisOverSeaRequestHeaderDto = KISOverSeaRequestHeaderDto(
                authorization = "Bearer ${user.token}",
                appKey = user.appKey,
                appSecret = user.appSecret,
                transactionId = TradeId.getTradeIdByEnum(TradeId.COUNTRIES_HOLIDAY)
        )


        val requestParameter = KISCountriesHolidayRequestParameterDto(
                tradDt = "20221227",
                ctxAreaNk = "",
                ctxAreaFk = ""
        )


        return kisBasicRealClient.getCountriesHoliday(kisOverSeaRequestHeaderDto, requestParameter).awaitSingle()
    }
}