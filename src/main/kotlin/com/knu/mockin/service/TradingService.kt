package com.knu.mockin.service

import com.knu.mockin.kisclient.KISTradingClient
import com.knu.mockin.model.dto.kisheader.request.KISOverSeaRequestHeaderDto
import com.knu.mockin.model.dto.kisrequest.trading.KISBalanceRequestParameterDto
import com.knu.mockin.model.dto.kisrequest.trading.KISNCCSRequestParameterDto
import com.knu.mockin.model.dto.kisrequest.trading.KISOrderRequestBodyDto
import com.knu.mockin.model.dto.kisrequest.trading.KISPsAmountRequestParameterDto
import com.knu.mockin.model.dto.kisresponse.trading.KISBalanceResponseDto
import com.knu.mockin.model.dto.kisresponse.trading.KISNCCSResponseDto
import com.knu.mockin.model.dto.kisresponse.trading.KISOrderResponseDto
import com.knu.mockin.model.dto.kisresponse.trading.KISPsAmountResponseDto
import com.knu.mockin.model.dto.request.trading.OrderRequestDto
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
        orderRequestDto: OrderRequestDto
    ):KISOrderResponseDto {
        val user = userRepository.findById(1).awaitFirst()
        val kisOverSeaRequestHeaderDto = KISOverSeaRequestHeaderDto(
            authorization = "Bearer ${user.token}",
            appKey = user.appKey,
            appSecret = user.appSecret,
            transactionId = orderRequestDto.transactionId
        )
        val kisOrderRequestBodyDto = KISOrderRequestBodyDto(
            accountNumber = user.accountNumber,
            accountProductCode = "01",
            overseasExchangeCode = orderRequestDto.overseasExchangeCode,
            productNumber = orderRequestDto.productNumber,
            orderQuantity = orderRequestDto.orderQuantity,
            overseasOrderUnitPrice = orderRequestDto.overseasOrderUnitPrice
        )
        return kisTradingClient
            .postOrder(kisOverSeaRequestHeaderDto, kisOrderRequestBodyDto)
            .awaitSingle()
    }

    suspend fun getNCCS(): KISNCCSResponseDto{
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
            overseasExchangeCode = ExchangeCode.SHAA.name,
            sortOrder = "DS",
            continuousSearchCondition200 = "",
            continuousSearchKey200 = ""
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

    suspend fun getPsAmount(): KISPsAmountResponseDto{
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
            overseasExchangeCode = ExchangeCode.SZAA.name,
            overseasOrderUnitPrice = "100.00",
            itemCode = "1380"
        )
        return kisTradingClient
            .getPsAmount(kisOverSeaRequestHeaderDto, kisPsAmountRequestParameterDto)
            .awaitSingle()
    }
}