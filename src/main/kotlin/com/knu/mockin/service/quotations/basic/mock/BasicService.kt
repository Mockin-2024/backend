package com.knu.mockin.service.quotations.basic.mock

import com.knu.mockin.exeption.ErrorCode
import com.knu.mockin.kisclient.KISBasicClient
import com.knu.mockin.model.dto.kisheader.request.KISOverSeaRequestHeaderDto
import com.knu.mockin.model.dto.kisrequest.quotations.basic.mock.KISCurrentPriceRequestParameterDto
import com.knu.mockin.model.dto.kisrequest.quotations.basic.mock.KISDailyChartPriceRequestParameterDto
import com.knu.mockin.model.dto.kisrequest.quotations.basic.mock.KISSearchRequestParameterDto
import com.knu.mockin.model.dto.kisrequest.quotations.basic.mock.KISTermPriceRequestParameterDto
import com.knu.mockin.model.dto.kisresponse.quotations.basic.mock.KISCurrentPriceResponseDto
import com.knu.mockin.model.dto.kisresponse.quotations.basic.mock.KISDailyChartPriceResponseDto
import com.knu.mockin.model.dto.kisresponse.quotations.basic.mock.KISSearchResponseDto
import com.knu.mockin.model.dto.kisresponse.quotations.basic.mock.KISTermPriceResponseDto
import com.knu.mockin.model.dto.request.quotations.basic.mock.CurrentPriceRequestParameterDto
import com.knu.mockin.model.dto.request.quotations.basic.mock.DailyChartPriceRequestParameterDto
import com.knu.mockin.model.dto.request.quotations.basic.mock.SearchRequestParameterDto
import com.knu.mockin.model.dto.request.quotations.basic.mock.TermPriceRequestParameterDto
import com.knu.mockin.model.enum.Constant
import com.knu.mockin.model.enum.Constant.MOCK
import com.knu.mockin.model.enum.TradeId
import com.knu.mockin.repository.MockKeyRepository
import com.knu.mockin.repository.UserRepository
import com.knu.mockin.util.ExtensionUtil.orThrow
import com.knu.mockin.util.RedisUtil
import com.knu.mockin.util.tag
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.stereotype.Service

@Service
class BasicService (
        private val kisBasicClient: KISBasicClient,
        private val userRepository: UserRepository,
) {
    suspend fun getCurrentPrice(
            currentPriceRequestParameterDto: CurrentPriceRequestParameterDto,
            email: String
    ): KISCurrentPriceResponseDto {
        val userWithKey =  userRepository.findByEmailWithMockKey(email)
            .orThrow(ErrorCode.USER_NOT_FOUND)
            .awaitFirst()
        val kisOverSeaRequestHeaderDto = KISOverSeaRequestHeaderDto(
            authorization = "Bearer ${RedisUtil.getToken(userWithKey.email tag Constant.MOCK)}",
            appKey = userWithKey.appKey,
            appSecret = userWithKey.appSecret,
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
            termPriceRequestParameterDto: TermPriceRequestParameterDto,
            email: String
    ): KISTermPriceResponseDto {
        val userWithKey =  userRepository.findByEmailWithMockKey(email)
            .orThrow(ErrorCode.USER_NOT_FOUND)
            .awaitFirst()
        val kisOverSeaRequestHeaderDto = KISOverSeaRequestHeaderDto(
            authorization = "Bearer ${RedisUtil.getToken(userWithKey.email tag Constant.MOCK)}",
            appKey = userWithKey.appKey,
            appSecret = userWithKey.appSecret,
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
            dailyChartPriceRequestParameterDto: DailyChartPriceRequestParameterDto,
            email: String
    ): KISDailyChartPriceResponseDto {
        val userWithKey =  userRepository.findByEmailWithMockKey(email)
            .orThrow(ErrorCode.USER_NOT_FOUND)
            .awaitFirst()
        val kisOverSeaRequestHeaderDto = KISOverSeaRequestHeaderDto(
            authorization = "Bearer ${RedisUtil.getToken(userWithKey.email tag Constant.MOCK)}",
            appKey = userWithKey.appKey,
            appSecret = userWithKey.appSecret,
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
            searchRequestParameterDto: SearchRequestParameterDto,
            email: String
    ): KISSearchResponseDto {
        val userWithKey =  userRepository.findByEmailWithMockKey(email)
            .orThrow(ErrorCode.USER_NOT_FOUND)
            .awaitFirst()
        val kisOverSeaRequestHeaderDto = KISOverSeaRequestHeaderDto(
            authorization = "Bearer ${RedisUtil.getToken(userWithKey.email tag Constant.MOCK)}",
            appKey = userWithKey.appKey,
            appSecret = userWithKey.appSecret,
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
