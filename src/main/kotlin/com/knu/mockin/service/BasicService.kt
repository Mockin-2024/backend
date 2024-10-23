package com.knu.mockin.service

import com.knu.mockin.kisclient.KISBasicClient
import com.knu.mockin.model.dto.kisheader.request.KISOverSeaRequestHeaderDto
import com.knu.mockin.model.dto.kisrequest.basic.KISCurrentPriceRequestParameterDto
import com.knu.mockin.model.dto.kisrequest.basic.KISDailyChartPriceRequestParameterDto
import com.knu.mockin.model.dto.kisrequest.basic.KISSearchRequestParameterDto
import com.knu.mockin.model.dto.kisrequest.basic.KISTermPriceRequestParameterDto
import com.knu.mockin.model.dto.kisresponse.basic.KISCurrentPriceResponseDto
import com.knu.mockin.model.dto.kisresponse.basic.KISDailyChartPriceResponseDto
import com.knu.mockin.model.dto.kisresponse.basic.KISSearchResponseDto
import com.knu.mockin.model.dto.kisresponse.basic.KISTermPriceResponseDto
import com.knu.mockin.model.dto.request.basic.CurrentPriceRequestParameterDto
import com.knu.mockin.model.dto.request.basic.DailyChartPriceRequestParameterDto
import com.knu.mockin.model.dto.request.basic.SearchRequestParameterDto
import com.knu.mockin.model.dto.request.basic.TermPriceRequestParameterDto
import com.knu.mockin.model.enum.Constant.MOCK
import com.knu.mockin.model.enum.TradeId
import com.knu.mockin.repository.MockKeyRepository
import com.knu.mockin.repository.UserRepository
import com.knu.mockin.util.RedisUtil
import com.knu.mockin.util.tag
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.stereotype.Service

@Service
class BasicService (
        private val kisBasicClient: KISBasicClient,
        private val mockKeyRepository: MockKeyRepository,
        private val userRepository: UserRepository,
) {
    suspend fun getCurrentPrice(
            currentPriceRequestParameterDto: CurrentPriceRequestParameterDto
    ): KISCurrentPriceResponseDto {
        val mockKey = mockKeyRepository.findByEmail(
                currentPriceRequestParameterDto.email).awaitFirst()
        val user = userRepository.findByEmail(
                currentPriceRequestParameterDto.email).awaitFirst()
        val kisOverSeaRequestHeaderDto = KISOverSeaRequestHeaderDto(
                authorization = "Bearer ${RedisUtil.getToken(user.email tag MOCK)}",
                appKey = mockKey.appKey,
                appSecret = mockKey.appSecret,
                transactionId = TradeId.getTradeIdByEnum(TradeId.CURRENT_PRICE) 
        )

        
        val requestParameter = KISCurrentPriceRequestParameterDto(

                AUTH = currentPriceRequestParameterDto.AUTH,
                EXCD = currentPriceRequestParameterDto.EXCD,
                SYMB = currentPriceRequestParameterDto.SYMB
        )

        
        return kisBasicClient.getCurrentPrice(kisOverSeaRequestHeaderDto, requestParameter).awaitSingle()
    }

    suspend fun getTermPrice(
            termPriceRequestParameterDto: TermPriceRequestParameterDto
    ): KISTermPriceResponseDto {
        val mockKey = mockKeyRepository.findByEmail(
                termPriceRequestParameterDto.email).awaitFirst()
        val user = userRepository.findByEmail(
                termPriceRequestParameterDto.email).awaitFirst()
        val kisOverSeaRequestHeaderDto = KISOverSeaRequestHeaderDto(
                authorization = "Bearer ${RedisUtil.getToken(user.email tag MOCK)}",
                appKey = mockKey.appKey,
                appSecret = mockKey.appSecret,
                transactionId = TradeId.getTradeIdByEnum(TradeId.TERM_PRICE) 
        )

        
        val requestParameter = KISTermPriceRequestParameterDto(
                AUTH = termPriceRequestParameterDto.AUTH,
                EXCD = termPriceRequestParameterDto.EXCD,
                SYMB = termPriceRequestParameterDto.SYMB,
                GUBN = termPriceRequestParameterDto.GUBN,
                BYMD = termPriceRequestParameterDto.BYMD,
                MODP = termPriceRequestParameterDto.MODP,
                KEYB = termPriceRequestParameterDto.KEYB
        )

        
        return kisBasicClient.getTermPrice(kisOverSeaRequestHeaderDto, requestParameter).awaitSingle()

    }

    suspend fun getDailyChartPrice(
            dailyChartPriceRequestParameterDto: DailyChartPriceRequestParameterDto
    ): KISDailyChartPriceResponseDto {
        val mockKey = mockKeyRepository.findByEmail(
                dailyChartPriceRequestParameterDto.email).awaitFirst()
        val user = userRepository.findByEmail(
                dailyChartPriceRequestParameterDto.email).awaitFirst()
        val kisOverSeaRequestHeaderDto = KISOverSeaRequestHeaderDto(
                authorization = "Bearer ${RedisUtil.getToken(user.email tag MOCK)}",
                appKey = mockKey.appKey,
                appSecret = mockKey.appSecret,
                transactionId = TradeId.getTradeIdByEnum(TradeId.DAILY_CHART_PRICE)
        )

        val requestParameter = KISDailyChartPriceRequestParameterDto(
                fidCondMrktDivCode = dailyChartPriceRequestParameterDto.fidCondMrktDivCode,
                fidInputDate1 = dailyChartPriceRequestParameterDto.fidInputDate1,
                fidInputDate2 = dailyChartPriceRequestParameterDto.fidInputDate2,
                fidInputIscd = dailyChartPriceRequestParameterDto.fidInputIscd,
                fidPeriodDivCode = dailyChartPriceRequestParameterDto.fidPeriodDivCode
        )


        return kisBasicClient.getDailyChartPrice(kisOverSeaRequestHeaderDto, requestParameter).awaitSingle()

    }

    suspend fun getSearch(
            searchRequestParameterDto: SearchRequestParameterDto
    ): KISSearchResponseDto {
        val mockKey = mockKeyRepository.findByEmail(
                searchRequestParameterDto.email).awaitFirst()
        val user = userRepository.findByEmail(
                searchRequestParameterDto.email).awaitFirst()
        val kisOverSeaRequestHeaderDto = KISOverSeaRequestHeaderDto(
                authorization = "Bearer ${RedisUtil.getToken(user.email tag MOCK)}",
                appKey = mockKey.appKey,
                appSecret = mockKey.appSecret,
                transactionId = TradeId.getTradeIdByEnum(TradeId.SEARCH)
        )


        val requestParameter = KISSearchRequestParameterDto(
                AUTH = searchRequestParameterDto.AUTH,
                EXCD = searchRequestParameterDto.EXCD,
                coYnPricecur = searchRequestParameterDto.coYnPricecur,
                coStPricecur = searchRequestParameterDto.coStPricecur,
                coEnPricecur = searchRequestParameterDto.coEnPricecur,
                coYnRate = searchRequestParameterDto.coYnRate,
                coStRate = searchRequestParameterDto.coStRate,
                coEnRate = searchRequestParameterDto.coEnRate,
                coYnValx = searchRequestParameterDto.coYnValx,
                coStValx = searchRequestParameterDto.coStValx,
                coEnValx = searchRequestParameterDto.coEnValx,
                coYnShar = searchRequestParameterDto.coYnShar,
                coStShar = searchRequestParameterDto.coStShar,
                coEnShar = searchRequestParameterDto.coEnShar,
                coYnVolume = searchRequestParameterDto.coYnVolume,
                coStVolume = searchRequestParameterDto.coStVolume,
                coEnVolume = searchRequestParameterDto.coEnVolume,
                coYnAmt = searchRequestParameterDto.coYnAmt,
                coStAmt = searchRequestParameterDto.coStAmt,
                coEnAmt = searchRequestParameterDto.coEnAmt,
                coYnEps = searchRequestParameterDto.coYnEps,
                coStEps = searchRequestParameterDto.coStEps,
                coEnEps = searchRequestParameterDto.coEnEps,
                coYnPer = searchRequestParameterDto.coYnPer,
                coStPer = searchRequestParameterDto.coStPer,
                coEnPer = searchRequestParameterDto.coEnPer,
                KEYB = searchRequestParameterDto.KEYB
        )


        return kisBasicClient.getSearch(kisOverSeaRequestHeaderDto, requestParameter).awaitSingle()

    }

}
