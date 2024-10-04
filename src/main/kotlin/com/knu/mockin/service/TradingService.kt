package com.knu.mockin.service

import com.knu.mockin.kisclient.KISTradingClient
import com.knu.mockin.logging.model.LogAPIEntry
import com.knu.mockin.logging.utils.LogUtil
import com.knu.mockin.model.dto.kisheader.request.KISOverSeaRequestHeaderDto
import com.knu.mockin.model.dto.kisrequest.trading.KISOrderRequestDto
import com.knu.mockin.model.dto.kisresponse.trading.KISOrderResponseDto
import com.knu.mockin.model.enum.ExchangeCode
import com.knu.mockin.model.enum.TradeId
import com.knu.mockin.repository.UserRepository
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactor.awaitSingle
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class TradingService(
    private val kisTradingClient: KISTradingClient,
    private val userRepository: UserRepository
) {
    private val log = LoggerFactory.getLogger(TradingService::class.java)
    suspend fun order(): KISOrderResponseDto {
        val user = userRepository.findById(1).awaitFirst()
        val kisOverSeaRequestHeaderDto = KISOverSeaRequestHeaderDto(
            authorization = "Bearer ${user.token}",
            appKey = user.appKey,
            appSecret = user.appSecret,
            transactionId = TradeId.getTradeIdByEnum(TradeId.HONG_KONG_BUY)
        )
        val kisOrderRequestDto = KISOrderRequestDto(
            accountNumber = user.accountNumber,
            accountProductCode = "01",
            overseasExchangeCode = ExchangeCode.SEHK.name,
            productNumber = "3",
            orderQuantity = "1",
            overseasOrderUnitPrice = "0",
        )
        log.info("{}", LogUtil.toJson(LogAPIEntry(kisOverSeaRequestHeaderDto)))
        log.info("{}", LogUtil.toJson(LogAPIEntry(kisOrderRequestDto)))
        return kisTradingClient.postOrder(kisOverSeaRequestHeaderDto, kisOrderRequestDto).awaitSingle().component2()
    }

    suspend fun inquireBalance(){

    }
}