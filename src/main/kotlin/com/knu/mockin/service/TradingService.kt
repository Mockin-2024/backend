package com.knu.mockin.service

import com.knu.mockin.exception.ErrorCode
import com.knu.mockin.kisclient.KISTradingClient
import com.knu.mockin.model.dto.kisresponse.trading.*
import com.knu.mockin.model.dto.request.trading.*
import com.knu.mockin.model.enum.TimeConstant.ONE_DAY
import com.knu.mockin.model.enum.TimeConstant.THIRTY_MINUTES
import com.knu.mockin.model.enum.TradeId
import com.knu.mockin.repository.UserRepository
import com.knu.mockin.service.util.ServiceUtil.createHeader
import com.knu.mockin.util.ExtensionUtil.orThrow
import com.knu.mockin.util.ExtensionUtil.toDto
import com.knu.mockin.util.RedisUtil
import com.knu.mockin.util.tag
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service

@Service
class TradingService(
    private val kisTradingClient: KISTradingClient,
    private val userRepository: UserRepository,
) {
    suspend fun postOrder(
        bodyDto: OrderRequestBodyDto,
        email: String
    ):KISOrderResponseDto {
        val userWithMockKey = getUser(email)

        val headerDto = createHeader(userWithMockKey, bodyDto.transactionId)
        val kisOrderRequestBodyDto = bodyDto.asDomain(userWithMockKey.accountNumber)

        RedisUtil.deleteByPattern(email tag "getNCCS" tag "*")
        RedisUtil.deleteByPattern(email tag "getCCNL" tag "*")
        RedisUtil.deleteByPattern(email tag "getPresentBalance" tag "*")

        return kisTradingClient
            .postOrder(headerDto, kisOrderRequestBodyDto)
            .awaitSingle()
    }

    suspend fun postOrderReverse(
        bodyDto: OrderReverseRequestBodyDto,
        email: String
    ): KISOrderReverseResponseDto {
        val userWithMockKey = getUser(email)

        val headerDto = createHeader(userWithMockKey, bodyDto.transactionId)
        val kisOrderReverseRequestBodyDto = bodyDto.asDomain(userWithMockKey.accountNumber)

        RedisUtil.deleteByPattern(email tag "getNCCS" tag "*")
        RedisUtil.deleteByPattern(email tag "getCCNL" tag "*")
        RedisUtil.deleteByPattern(email tag "getPresentBalance" tag "*")

        return kisTradingClient
            .postOrderReverse(headerDto, kisOrderReverseRequestBodyDto)
            .awaitSingle()
    }

    suspend fun postOrderReserve(
        bodyDto: OrderReserveRequestBodyDto,
        email: String
    ): KISOrderReserveResponseDto {
        val userWithMockKey = getUser(email)

        val headerDto = createHeader(userWithMockKey, bodyDto.transactionId)
        val kisOrderReverseRequestBodyDto = bodyDto.asDomain(userWithMockKey.accountNumber)

        return kisTradingClient
            .postOrderReserve(headerDto, kisOrderReverseRequestBodyDto)
            .awaitSingle()
    }

    suspend fun getNCCS(
        parameterDto: NCCSRequestParameterDto,
        email: String
    ): KISNCCSResponseDto{
        val userWithMockKey = getUser(email)

        val redisCacheKey = email tag
                "getNCCS" tag
                parameterDto.overseasExchangeCode tag
                parameterDto.continuousSearchKey200 tag
                parameterDto.continuousSearchCondition200

        val cachedValue = RedisUtil.getData(redisCacheKey)
        if (cachedValue != null){
            return cachedValue toDto KISNCCSResponseDto::class.java
        }

        val headerDto = createHeader(userWithMockKey, TradeId.getTradeIdByEnum(TradeId.INQUIRE_NCCS))
        val kisnccsRequestParameterDto = parameterDto.asDomain(userWithMockKey.accountNumber)

        val response = kisTradingClient
            .getNCCS(headerDto, kisnccsRequestParameterDto)
            .awaitSingle()
        RedisUtil.setData(redisCacheKey, response, ONE_DAY)

        return response
    }

    suspend fun getBalance(
        parameterDto: BalanceRequestParameterDto,
        email: String
    ): KISBalanceResponseDto{
        val userWithMockKey = getUser(email)
        val redisCacheKey = email tag
                "getBalance" tag
                parameterDto.overseasExchangeCode tag
                parameterDto.transactionCurrencyCode tag
                parameterDto.continuousSearchKey200 tag
                parameterDto.continuousSearchCondition200

        val cachedValue = RedisUtil.getData(redisCacheKey)
        if (cachedValue != null){
            return cachedValue toDto KISBalanceResponseDto::class.java
        }

        val headerDto = createHeader(userWithMockKey, TradeId.getTradeIdByEnum(TradeId.INQUIRE_BALANCE))
        val kisBalanceRequestParameterDto = parameterDto.asDomain(userWithMockKey.accountNumber)

        val response = kisTradingClient
            .getBalance(headerDto, kisBalanceRequestParameterDto)
            .awaitSingle()
        RedisUtil.setData(redisCacheKey, response, ONE_DAY)

        return response
    }

    suspend fun getPsAmount(
        parameterDto: PsAmountRequestParameterDto,
        email: String
    ): KISPsAmountResponseDto{
        val userWithMockKey = getUser(email)

        val headerDto = createHeader(userWithMockKey, TradeId.getTradeIdByEnum(TradeId.INQUIRE_PSAMOUNT))
        val kisPsAmountRequestParameterDto = parameterDto.asDomain(userWithMockKey.accountNumber)

        return kisTradingClient
            .getPsAmount(headerDto, kisPsAmountRequestParameterDto)
            .awaitSingle()
    }

    suspend fun getPresentBalance(
        parameterDto: PresentBalanceRequestParameterDto,
        email: String
    ): KISPresentBalanceResponseDto{
        val userWithMockKey = getUser(email)

        val redisCacheKey = email tag
                "getPresentBalance" tag
                parameterDto.currencyDivisionCode tag
                parameterDto.countryCode tag
                parameterDto.marketCode tag
                parameterDto.inquiryDivisionCode


        val cachedValue = RedisUtil.getData(redisCacheKey)
        if (cachedValue != null){
            return cachedValue toDto KISPresentBalanceResponseDto::class.java
        }

        val headerDto = createHeader(userWithMockKey, TradeId.getTradeIdByEnum(TradeId.INQUIRE_PRESENT_BALANCE))
        val kisPresentBalanceRequestParameterDto = parameterDto.asDomain(userWithMockKey.accountNumber)

        val response =  kisTradingClient
            .getPresentBalance(headerDto, kisPresentBalanceRequestParameterDto)
            .awaitSingle()

        RedisUtil.setData(redisCacheKey, response, THIRTY_MINUTES)

        return response
    }

    suspend fun getCCNL(
        parameterDto: CCNLRequestParameterDto,
        email: String
    ): KISCCNLResponseDto{
        val userWithMockKey = getUser(email)

        val redisCacheKey = email tag
                "getCCNL" tag
                parameterDto.orderStartDate tag
                parameterDto.orderEndDate tag
                parameterDto.continuousSearchKey200 tag
                parameterDto.continuousSearchCondition200

        val cachedValue = RedisUtil.getData(redisCacheKey)
        if (cachedValue != null){
            return cachedValue toDto KISCCNLResponseDto::class.java
        }

        val headerDto = createHeader(userWithMockKey, TradeId.getTradeIdByEnum(TradeId.INQUIRE_CCNL))
        headerDto.transactionContinuation = parameterDto.transactionContinuation
        val kisccnlRequestParameterDto = parameterDto.asDomain(userWithMockKey.accountNumber)

        val response =  kisTradingClient
            .getCCNL(headerDto, kisccnlRequestParameterDto)
            .awaitSingle()

        RedisUtil.setData(redisCacheKey, response, ONE_DAY)

        return response
    }

    private suspend fun getUser(email: String) = userRepository.findByEmailWithMockKey(email)
        .orThrow(ErrorCode.USER_NOT_FOUND)
        .awaitFirst()

}