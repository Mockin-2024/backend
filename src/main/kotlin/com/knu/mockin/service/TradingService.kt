package com.knu.mockin.service

import com.knu.mockin.kisclient.KISTradingClient
import com.knu.mockin.model.dto.kisheader.request.KISOverSeaRequestHeaderDto
import com.knu.mockin.model.dto.kisrequest.trading.*
import com.knu.mockin.model.dto.kisresponse.trading.*
import com.knu.mockin.model.dto.request.trading.*
import com.knu.mockin.model.enum.ExchangeCode
import com.knu.mockin.model.enum.TradeCurrencyCode
import com.knu.mockin.model.enum.TradeId
import com.knu.mockin.repository.UserRepository
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service

@Service
class TradingService(
    private val kisTradingClient: KISTradingClient,
    private val userRepository: UserRepository
) {
    suspend fun postOrder(
        orderRequestBodyDto: OrderRequestBodyDto
    ):KISOrderResponseDto {
        val user = userRepository.findById(1).awaitFirst()
        val kisOverSeaRequestHeaderDto = KISOverSeaRequestHeaderDto(
            authorization = "Bearer ${user.token}",
            appKey = user.appKey,
            appSecret = user.appSecret,
            transactionId = orderRequestBodyDto.transactionId
        )
        val kisOrderRequestBodyDto = KISOrderRequestBodyDto(
            accountNumber = user.accountNumber,
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

    suspend fun getNCCS(
        nccsRequestParameterDto: NCCSRequestParameterDto
    ): KISNCCSResponseDto{
        val user = userRepository.findById(1).awaitFirst()
        val kisOverSeaRequestHeaderDto = KISOverSeaRequestHeaderDto(
            authorization = "Bearer ${user.token}",
            appKey = user.appKey,
            appSecret = user.appSecret,
            transactionId = TradeId.getTradeIdByEnum(TradeId.INQUIRE_NCCS)
        )
        val kisnccsRequestParameterDto = KISNCCSRequestParameterDto(
            accountNumber = user.accountNumber,
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
    suspend fun getBalance(): KISBalanceResponseDto{
        val user = userRepository.findById(1).awaitFirst()
        val kisOverSeaRequestHeaderDto = KISOverSeaRequestHeaderDto(
            authorization = "Bearer ${user.token}",
            appKey = user.appKey,
            appSecret = user.appSecret,
            transactionId = TradeId.getTradeIdByEnum(TradeId.INQUIRE_BALANCE)
        )
        val kisBalanceRequestParameterDto = KISBalanceRequestParameterDto(
            accountNumber = user.accountNumber,
            accountProductCode = "01",
            overseasExchangeCode = ExchangeCode.SHAA.name,
            transactionCurrencyCode = TradeCurrencyCode.CNY.name,
            continuousSearchCondition200 = "",
            continuousSearchKey200 = ""
        )
        return kisTradingClient
            .getBalance(kisOverSeaRequestHeaderDto, kisBalanceRequestParameterDto)
            .awaitSingle()
    }

    suspend fun getPsAmount(
        psAmountRequestParameterDto: PsAmountRequestParameterDto
    ): KISPsAmountResponseDto{
        val user = userRepository.findById(1).awaitFirst()
        val kisOverSeaRequestHeaderDto = KISOverSeaRequestHeaderDto(
            authorization = "Bearer ${user.token}",
            appKey = user.appKey,
            appSecret = user.appSecret,
            transactionId = TradeId.getTradeIdByEnum(TradeId.INQUIRE_PSAMOUNT)
        )
        val kisPsAmountRequestParameterDto = KISPsAmountRequestParameterDto(
            accountNumber = user.accountNumber,
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
        val user = userRepository.findById(1).awaitFirst()
        val kisOverSeaRequestHeaderDto = KISOverSeaRequestHeaderDto(
            authorization = "Bearer ${user.token}",
            appKey = user.appKey,
            appSecret = user.appSecret,
            transactionId = TradeId.getTradeIdByEnum(TradeId.INQUIRE_PRESENT_BALANCE)
        )
        val kisPresentBalanceRequestParameterDto = KISPresentBalanceRequestParameterDto(
            accountNumber = user.accountNumber,
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
        val user = userRepository.findById(1).awaitFirst()
        val kisOverSeaRequestHeaderDto = KISOverSeaRequestHeaderDto(
            authorization = "Bearer ${user.token}",
            appKey = user.appKey,
            appSecret = user.appSecret,
            transactionId = TradeId.getTradeIdByEnum(TradeId.INQUIRE_CCNL)
        )
        val kisccnlRequestParameterDto = KISCCNLRequestParameterDto(
            accountNumber = user.accountNumber,
            accountProductCode = "01",
            orderStartDate = ccnlRequestParameterDto.orderStartDate,
            orderEndDate = ccnlRequestParameterDto.orderEndDate
        )
        return kisTradingClient
            .getCCNL(kisOverSeaRequestHeaderDto, kisccnlRequestParameterDto)
            .awaitSingle()
    }
}