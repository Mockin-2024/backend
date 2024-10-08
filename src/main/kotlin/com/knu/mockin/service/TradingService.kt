package com.knu.mockin.service

import com.knu.mockin.kisclient.KISTradingClient
import com.knu.mockin.model.dto.kisheader.request.KISOverSeaRequestHeaderDto
import com.knu.mockin.model.dto.kisrequest.trading.KISBalanceRequestDto
import com.knu.mockin.model.dto.kisrequest.trading.KISOrderRequestDto
import com.knu.mockin.model.dto.kisresponse.trading.KISBalanceResponseDto
import com.knu.mockin.model.dto.kisresponse.trading.KISOrderResponseDto
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
    suspend fun postOrder(): KISOrderResponseDto {
        val user = userRepository.findById(1).awaitFirst()
        val kisOverSeaRequestHeaderDto = KISOverSeaRequestHeaderDto(
            authorization = "Bearer ${user.token}",
            appKey = user.appKey,
            appSecret = user.appSecret,
            transactionId = TradeId.getTradeIdByEnum(TradeId.SHENZHEN_BUY)
        )
        val kisOrderRequestDto = KISOrderRequestDto(
            accountNumber = user.accountNumber,
            accountProductCode = "01",
            overseasExchangeCode = ExchangeCode.SZAA.name,
            productNumber = "1380",
            orderQuantity = "1",
            overseasOrderUnitPrice = "0",
        )
        return kisTradingClient
            .postOrder(kisOverSeaRequestHeaderDto, kisOrderRequestDto)
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
        val kisBalanceRequestDto = KISBalanceRequestDto(
            accountNumber = user.accountNumber,
            accountProductCode = "01",
            overseasExchangeCode = ExchangeCode.SHAA.name,
            transactionCurrencyCode = TradeCurrencyCode.CNY.name,
            continuousSearchCondition200 = "",
            continuousSearchKey200 = ""
        )
        return kisTradingClient
            .getBalance(kisOverSeaRequestHeaderDto, kisBalanceRequestDto)
            .awaitSingle()
    }
}