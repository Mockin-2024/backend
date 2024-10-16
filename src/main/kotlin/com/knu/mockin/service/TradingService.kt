package com.knu.mockin.service

import com.knu.mockin.exeption.ErrorCode
import com.knu.mockin.kisclient.KISTradingClient
import com.knu.mockin.model.dto.kisheader.request.KISOverSeaRequestHeaderDto
import com.knu.mockin.model.dto.kisrequest.trading.*
import com.knu.mockin.model.dto.kisresponse.trading.*
import com.knu.mockin.model.dto.request.trading.*
import com.knu.mockin.model.enum.TradeId
import com.knu.mockin.repository.UserRepository
import com.knu.mockin.util.ExtensionUtil.orThrow
import com.knu.mockin.util.RedisUtil
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service

@Service
class TradingService(
    private val kisTradingClient: KISTradingClient,
    private val userRepository: UserRepository,
) {
    suspend fun postOrder(
        orderRequestBodyDto: OrderRequestBodyDto
    ):KISOrderResponseDto {
        val userWithMockKey = userRepository.findByEmailWithMockKey(orderRequestBodyDto.email)
            .orThrow(ErrorCode.USER_NOT_FOUND)
            .awaitFirst()

        val kisOverSeaRequestHeaderDto = KISOverSeaRequestHeaderDto(
            authorization = "Bearer ${RedisUtil.getToken(userWithMockKey.email)}",
            appKey = userWithMockKey.appKey,
            appSecret = userWithMockKey.appSecret,
            transactionId = orderRequestBodyDto.transactionId
        )
        val kisOrderRequestBodyDto = KISOrderRequestBodyDto(
            accountNumber = userWithMockKey.accountNumber,
            accountProductCode = "01",
            overseasExchangeCode = orderRequestBodyDto.overseasExchangeCode,
            productNumber = orderRequestBodyDto.productNumber,
            orderQuantity = orderRequestBodyDto.orderQuantity,
            overseasOrderUnitPrice = orderRequestBodyDto.overseasOrderUnitPrice
        )
        return kisTradingClient
            .postOrder(kisOverSeaRequestHeaderDto, kisOrderRequestBodyDto)
            .awaitSingle()
    }

    suspend fun postOrderReverse(
        orderReverseRequestBodyDto: OrderReverseRequestBodyDto
    ): KISOrderReverseResponseDto {
        val userWithMockKey = userRepository.findByEmailWithMockKey(orderReverseRequestBodyDto.email)
            .orThrow(ErrorCode.USER_NOT_FOUND)
            .awaitFirst()

        val kisOverSeaRequestHeaderDto = KISOverSeaRequestHeaderDto(
            authorization = "Bearer ${RedisUtil.getToken(userWithMockKey.email)}",
            appKey = userWithMockKey.appKey,
            appSecret = userWithMockKey.appSecret,
            transactionId = orderReverseRequestBodyDto.transactionId
        )

        val kisOrderReverseRequestBodyDto = KISOrderReverseRequestBodyDto(
            accountNumber = userWithMockKey.accountNumber,
            overseasExchangeCode = orderReverseRequestBodyDto.overseasExchangeCode,
            productNumber = orderReverseRequestBodyDto.productNumber,
            originalOrderNumber = orderReverseRequestBodyDto.originalOrderNumber,
            cancelOrReviseCode = orderReverseRequestBodyDto.cancelOrReviseCode,
            orderQuantity = orderReverseRequestBodyDto.orderQuantity,
            overseasOrderPrice = orderReverseRequestBodyDto.overseasOrderPrice
        )

        return kisTradingClient
            .postOrderReverse(kisOverSeaRequestHeaderDto, kisOrderReverseRequestBodyDto)
            .awaitSingle()
    }

    suspend fun getNCCS(
        nccsRequestParameterDto: NCCSRequestParameterDto
    ): KISNCCSResponseDto{
        val userWithMockKey = userRepository.findByEmailWithMockKey(nccsRequestParameterDto.email)
            .orThrow(ErrorCode.USER_NOT_FOUND)
            .awaitFirst()

        val kisOverSeaRequestHeaderDto = KISOverSeaRequestHeaderDto(
            authorization = "Bearer ${RedisUtil.getToken(userWithMockKey.email)}",
            appKey = userWithMockKey.appKey,
            appSecret = userWithMockKey.appSecret,
            transactionId = TradeId.getTradeIdByEnum(TradeId.INQUIRE_NCCS)
        )
        val kisnccsRequestParameterDto = KISNCCSRequestParameterDto(
            accountNumber = userWithMockKey.accountNumber,
            accountProductCode = "01",
            overseasExchangeCode = nccsRequestParameterDto.overseasExchangeCode,
            sortOrder = nccsRequestParameterDto.sortOrder,
            continuousSearchCondition200 = nccsRequestParameterDto.continuousSearchCondition200,
            continuousSearchKey200 = nccsRequestParameterDto.continuousSearchKey200
        )

        return kisTradingClient
            .getNCCS(kisOverSeaRequestHeaderDto, kisnccsRequestParameterDto)
            .awaitSingle()
    }
    suspend fun getBalance(
        balanceRequestParameterDto: BalanceRequestParameterDto
    ): KISBalanceResponseDto{
        val userWithMockKey = userRepository.findByEmailWithMockKey(balanceRequestParameterDto.email)
            .orThrow(ErrorCode.USER_NOT_FOUND)
            .awaitFirst()
        val kisOverSeaRequestHeaderDto = KISOverSeaRequestHeaderDto(
            authorization = "Bearer ${RedisUtil.getToken(userWithMockKey.email)}",
            appKey = userWithMockKey.appKey,
            appSecret = userWithMockKey.appSecret,
            transactionId = TradeId.getTradeIdByEnum(TradeId.INQUIRE_BALANCE)
        )
        val kisBalanceRequestParameterDto = KISBalanceRequestParameterDto(
            accountNumber = userWithMockKey.accountNumber,
            accountProductCode = "01",
            overseasExchangeCode = balanceRequestParameterDto.overseasExchangeCode,
            transactionCurrencyCode = balanceRequestParameterDto.transactionCurrencyCode,
            continuousSearchCondition200 = balanceRequestParameterDto.continuousSearchCondition200,
            continuousSearchKey200 = balanceRequestParameterDto.continuousSearchKey200
        )
        return kisTradingClient
            .getBalance(kisOverSeaRequestHeaderDto, kisBalanceRequestParameterDto)
            .awaitSingle()
    }

    suspend fun getPsAmount(
        psAmountRequestParameterDto: PsAmountRequestParameterDto
    ): KISPsAmountResponseDto{
        val userWithMockKey = userRepository.findByEmailWithMockKey(psAmountRequestParameterDto.email)
            .orThrow(ErrorCode.USER_NOT_FOUND)
            .awaitFirst()
        val kisOverSeaRequestHeaderDto = KISOverSeaRequestHeaderDto(
            authorization = "Bearer ${RedisUtil.getToken(userWithMockKey.email)}",
            appKey = userWithMockKey.appKey,
            appSecret = userWithMockKey.appSecret,
            transactionId = TradeId.getTradeIdByEnum(TradeId.INQUIRE_PSAMOUNT)
        )
        val kisPsAmountRequestParameterDto = KISPsAmountRequestParameterDto(
            accountNumber = userWithMockKey.accountNumber,
            accountProductCode = "01",
            overseasExchangeCode = psAmountRequestParameterDto.overseasExchangeCode,
            overseasOrderUnitPrice = psAmountRequestParameterDto.overseasOrderUnitPrice,
            itemCode = psAmountRequestParameterDto.itemCode
        )
        return kisTradingClient
            .getPsAmount(kisOverSeaRequestHeaderDto, kisPsAmountRequestParameterDto)
            .awaitSingle()
    }

    suspend fun getPresentBalance(
        presentBalanceRequestParameterDto: PresentBalanceRequestParameterDto
    ): KISPresentBalanceResponseDto{
        val userWithMockKey = userRepository.findByEmailWithMockKey(presentBalanceRequestParameterDto.email)
            .orThrow(ErrorCode.USER_NOT_FOUND)
            .awaitFirst()
        val kisOverSeaRequestHeaderDto = KISOverSeaRequestHeaderDto(
            authorization = "Bearer ${RedisUtil.getToken(userWithMockKey.email)}",
            appKey = userWithMockKey.appKey,
            appSecret = userWithMockKey.appSecret,
            transactionId = TradeId.getTradeIdByEnum(TradeId.INQUIRE_PRESENT_BALANCE)
        )
        val kisPresentBalanceRequestParameterDto = KISPresentBalanceRequestParameterDto(
            accountNumber = userWithMockKey.accountNumber,
            accountProductCode = "01",
            currencyDivisionCode= presentBalanceRequestParameterDto.currencyDivisionCode,
            countryCode= presentBalanceRequestParameterDto.countryCode,
            marketCode = presentBalanceRequestParameterDto.marketCode,
            inquiryDivisionCode= presentBalanceRequestParameterDto.inquiryDivisionCode
        )
        return kisTradingClient
            .getPresentBalance(kisOverSeaRequestHeaderDto, kisPresentBalanceRequestParameterDto)
            .awaitSingle()
    }

    suspend fun getCCNL(
        ccnlRequestParameterDto: CCNLRequestParameterDto
    ): KISCCNLResponseDto{
        val userWithMockKey = userRepository.findByEmailWithMockKey(ccnlRequestParameterDto.email)
            .orThrow(ErrorCode.USER_NOT_FOUND)
            .awaitFirst()
        val kisOverSeaRequestHeaderDto = KISOverSeaRequestHeaderDto(
            authorization = "Bearer ${RedisUtil.getToken(userWithMockKey.email)}",
            appKey = userWithMockKey.appKey,
            appSecret = userWithMockKey.appSecret,
            transactionId = TradeId.getTradeIdByEnum(TradeId.INQUIRE_CCNL)
        )
        val kisccnlRequestParameterDto = KISCCNLRequestParameterDto(
            accountNumber = userWithMockKey.accountNumber,
            accountProductCode = "01",
            orderStartDate = ccnlRequestParameterDto.orderStartDate,
            orderEndDate = ccnlRequestParameterDto.orderEndDate
        )
        return kisTradingClient
            .getCCNL(kisOverSeaRequestHeaderDto, kisccnlRequestParameterDto)
            .awaitSingle()
    }
}