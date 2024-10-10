package com.knu.mockin.service

import com.knu.mockin.kisclient.KISBasicClient
import com.knu.mockin.model.dto.kisheader.request.KISOverSeaRequestHeaderDto
import com.knu.mockin.model.dto.kisrequest.basic.mock.KISCurrentPriceRequestParameterDto
import com.knu.mockin.model.dto.kisrequest.basic.mock.KISDailyChartPriceRequestParameterDto
import com.knu.mockin.model.dto.kisrequest.basic.mock.KISSearchRequestParameterDto
import com.knu.mockin.model.dto.kisrequest.basic.mock.KISTermPriceRequestParameterDto
import com.knu.mockin.model.dto.kisresponse.basic.mock.KISCurrentPriceResponseDto
import com.knu.mockin.model.dto.kisresponse.basic.mock.KISDailyChartPriceResponseDto
import com.knu.mockin.model.dto.kisresponse.basic.mock.KISSearchResponseDto
import com.knu.mockin.model.dto.kisresponse.basic.mock.KISTermPriceResponseDto
import com.knu.mockin.model.enum.TradeId
import com.knu.mockin.repository.UserRepository
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.stereotype.Service

@Service
class BasicService (
        private val kisBasicClient: KISBasicClient,
        private val userRepository: UserRepository
) {
    suspend fun getCurrentPrice(): KISCurrentPriceResponseDto {
        val user = userRepository.findById(1).awaitFirst()
        
        val kisOverSeaRequestHeaderDto = KISOverSeaRequestHeaderDto(
                authorization = "Bearer ${user.token}",
                appKey = user.appKey,
                appSecret = user.appSecret,
                transactionId = TradeId.getTradeIdByEnum(TradeId.CURRENT_PRICE) 
        )

        
        val requestParameter = KISCurrentPriceRequestParameterDto(
                
                EXCD = "NAS",
                SYMB = "TSLA"
        )

        
        return kisBasicClient.getCurrentPrice(kisOverSeaRequestHeaderDto, requestParameter).awaitSingle()
    }

    suspend fun getTermPrice(): KISTermPriceResponseDto {
        val user = userRepository.findById(1).awaitFirst()
        
        val kisOverSeaRequestHeaderDto = KISOverSeaRequestHeaderDto(
                authorization = "Bearer ${user.token}",
                appKey = user.appKey,
                appSecret = user.appSecret,
                transactionId = TradeId.getTradeIdByEnum(TradeId.TERM_PRICE) 
        )

        
        val requestParameter = KISTermPriceRequestParameterDto(
                AUTH = "",  
                EXCD = "NAS",
                SYMB = "TSLA",
                GUBN = "0",    
                BYMD = "",     
                MODP = "0",    
                KEYB = ""
        )

        
        return kisBasicClient.getTermPrice(kisOverSeaRequestHeaderDto, requestParameter).awaitSingle()

    }

    suspend fun getDailyChartPrice(): KISDailyChartPriceResponseDto {
        val user = userRepository.findById(1).awaitFirst()

        val kisOverSeaRequestHeaderDto = KISOverSeaRequestHeaderDto(
                authorization = "Bearer ${user.token}",
                appKey = user.appKey,
                appSecret = user.appSecret,
                transactionId = TradeId.getTradeIdByEnum(TradeId.DAILY_CHART_PRICE)
        )


        val requestParameter = KISDailyChartPriceRequestParameterDto(
                fidCondMrktDivCode = "N",
                fidInputDate1 = "20220401",
                fidInputDate2 = "20220613",
                fidInputIscd = ".DJI",
                fidPeriodDivCode = "D"
        )


        return kisBasicClient.getDailyChartPrice(kisOverSeaRequestHeaderDto, requestParameter).awaitSingle()

    }

    suspend fun getSearch(): KISSearchResponseDto {
        val user = userRepository.findById(1).awaitFirst()

        val kisOverSeaRequestHeaderDto = KISOverSeaRequestHeaderDto(
                authorization = "Bearer ${user.token}",
                appKey = user.appKey,
                appSecret = user.appSecret,
                transactionId = TradeId.getTradeIdByEnum(TradeId.SEARCH)
        )


        val requestParameter = KISSearchRequestParameterDto(
                AUTH = "",
                EXCD = "NAS",
                coYnPricecur = "1",
                coStPricecur = "160",
                coEnPricecur = "161",
                coYnRate = "",
                coStRate = "",
                coEnRate = "",
                coYnValx = "",
                coStValx = "",
                coEnValx = "",
                coYnShar = "",
                coStShar = "",
                coEnShar = "",
                coYnVolume = "",
                coStVolume = "",
                coEnVolume = "",
                coYnAmt = "",
                coStAmt = "",
                coEnAmt = "",
                coYnEps = "",
                coStEps = "",
                coEnEps = "",
                coYnPer = "",
                coStPer = "",
                coEnPer = "",
                KEYB = ""
        )


        return kisBasicClient.getSearch(kisOverSeaRequestHeaderDto, requestParameter).awaitSingle()

    }

}
