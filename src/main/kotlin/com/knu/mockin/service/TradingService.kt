package com.knu.mockin.service

import com.knu.mockin.exception.ErrorCode
import com.knu.mockin.kisclient.KISTradingClient
import com.knu.mockin.model.dto.kisresponse.trading.*
import com.knu.mockin.model.dto.request.trading.*
import com.knu.mockin.model.enum.TradeId
import com.knu.mockin.repository.UserRepository
import com.knu.mockin.service.util.ServiceUtil.createHeader
import com.knu.mockin.util.ExtensionUtil.orThrow
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

        return kisTradingClient
            .postOrderReverse(headerDto, kisOrderReverseRequestBodyDto)
            .awaitSingle()
    }

    suspend fun getNCCS(
        parameterDto: NCCSRequestParameterDto,
        email: String
    ): KISNCCSResponseDto{
        val userWithMockKey = getUser(email)

        val headerDto = createHeader(userWithMockKey, TradeId.getTradeIdByEnum(TradeId.INQUIRE_NCCS))
        val kisnccsRequestParameterDto = parameterDto.asDomain(userWithMockKey.accountNumber)

        return kisTradingClient
            .getNCCS(headerDto, kisnccsRequestParameterDto)
            .awaitSingle()
    }

    suspend fun getBalance(
        parameterDto: BalanceRequestParameterDto,
        email: String
    ): KISBalanceResponseDto{
        val userWithMockKey = getUser(email)

        val headerDto = createHeader(userWithMockKey, TradeId.getTradeIdByEnum(TradeId.INQUIRE_BALANCE))
        val kisBalanceRequestParameterDto = parameterDto.asDomain(userWithMockKey.accountNumber)

        return kisTradingClient
            .getBalance(headerDto, kisBalanceRequestParameterDto)
            .awaitSingle()
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

        val headerDto = createHeader(userWithMockKey, TradeId.getTradeIdByEnum(TradeId.INQUIRE_PRESENT_BALANCE))
        val kisPresentBalanceRequestParameterDto = parameterDto.asDomain(userWithMockKey.accountNumber)

        return kisTradingClient
            .getPresentBalance(headerDto, kisPresentBalanceRequestParameterDto)
            .awaitSingle()
    }

    suspend fun getCCNL(
        parameterDto: CCNLRequestParameterDto,
        email: String
    ): KISCCNLResponseDto{
        val userWithMockKey = getUser(email)

        val headerDto = createHeader(userWithMockKey, TradeId.getTradeIdByEnum(TradeId.INQUIRE_CCNL))
        headerDto.transactionContinuation = parameterDto.transactionContinuation
        val kisccnlRequestParameterDto = parameterDto.asDomain(userWithMockKey.accountNumber)

        return kisTradingClient
            .getCCNL(headerDto, kisccnlRequestParameterDto)
            .awaitSingle()
    }

    private suspend fun getUser(email: String) = userRepository.findByEmailWithMockKey(email)
        .orThrow(ErrorCode.USER_NOT_FOUND)
        .awaitFirst()

}