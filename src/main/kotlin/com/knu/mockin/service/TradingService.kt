package com.knu.mockin.service

import com.knu.mockin.kisclient.KISTradingClient
import com.knu.mockin.model.dto.kisheader.request.KISOverSeaRequestHeaderDto
import com.knu.mockin.model.dto.kisrequest.trading.*
import com.knu.mockin.model.dto.kisresponse.trading.*
import com.knu.mockin.model.dto.request.trading.*
import com.knu.mockin.model.enum.TradeId
import com.knu.mockin.repository.MockKeyRepository
import com.knu.mockin.repository.UserRepository
import com.knu.mockin.util.RedisUtil
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service

@Service
class TradingService(
    private val kisTradingClient: KISTradingClient,
    private val userRepository: UserRepository,
    private val mockKeyRepository: MockKeyRepository
) {
    suspend fun postOrder(
        orderRequestBodyDto: OrderRequestBodyDto
    ):KISOrderResponseDto {
        val user = userRepository.findById(orderRequestBodyDto.email).awaitFirst()
        val mockKey = mockKeyRepository.findByEmail(orderRequestBodyDto.email).awaitFirst()
        val kisOverSeaRequestHeaderDto = KISOverSeaRequestHeaderDto(
            authorization = "Bearer ${RedisUtil.getToken(user.email)}",
            appKey = mockKey.appKey,
            appSecret = mockKey.appSecret,
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
        val user = userRepository.findById(nccsRequestParameterDto.email).awaitFirst()
        val mockKey = mockKeyRepository.findByEmail(nccsRequestParameterDto.email).awaitFirst()
        val kisOverSeaRequestHeaderDto = KISOverSeaRequestHeaderDto(
            authorization = "Bearer ${RedisUtil.getToken(user.email)}",
            appKey = mockKey.appKey,
            appSecret = mockKey.appSecret,
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
    suspend fun getBalance(
        balanceRequestParameterDto: BalanceRequestParameterDto
    ): KISBalanceResponseDto{
        val user = userRepository.findById(balanceRequestParameterDto.email).awaitFirst()
        val mockKey = mockKeyRepository.findByEmail(balanceRequestParameterDto.email).awaitFirst()
        val kisOverSeaRequestHeaderDto = KISOverSeaRequestHeaderDto(
            authorization = "Bearer ${RedisUtil.getToken(user.email)}",
            appKey = mockKey.appKey,
            appSecret = mockKey.appSecret,
            transactionId = TradeId.getTradeIdByEnum(TradeId.INQUIRE_BALANCE)
        )
        val kisBalanceRequestParameterDto = KISBalanceRequestParameterDto(
            accountNumber = user.accountNumber,
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
        val user = userRepository.findById(psAmountRequestParameterDto.email).awaitFirst()
        val mockKey = mockKeyRepository.findByEmail(psAmountRequestParameterDto.email).awaitFirst()
        val kisOverSeaRequestHeaderDto = KISOverSeaRequestHeaderDto(
            authorization = "Bearer ${RedisUtil.getToken(user.email)}",
            appKey = mockKey.appKey,
            appSecret = mockKey.appSecret,
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
        val user = userRepository.findById(presentBalanceRequestParameterDto.email).awaitFirst()
        val mockKey = mockKeyRepository.findByEmail(presentBalanceRequestParameterDto.email).awaitFirst()
        val kisOverSeaRequestHeaderDto = KISOverSeaRequestHeaderDto(
            authorization = "Bearer ${RedisUtil.getToken(user.email)}",
            appKey = mockKey.appKey,
            appSecret = mockKey.appSecret,
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
        val user = userRepository.findById(ccnlRequestParameterDto.email).awaitFirst()
        val mockKey = mockKeyRepository.findByEmail(ccnlRequestParameterDto.email).awaitFirst()
        val kisOverSeaRequestHeaderDto = KISOverSeaRequestHeaderDto(
            authorization = "Bearer ${RedisUtil.getToken(user.email)}",
            appKey = mockKey.appKey,
            appSecret = mockKey.appSecret,
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