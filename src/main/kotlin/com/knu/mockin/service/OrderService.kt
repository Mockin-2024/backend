package com.knu.mockin.service

import com.knu.mockin.kisclient.KISOverSeaClient
import com.knu.mockin.model.dto.kisheader.request.KISOverSeaRequestHeaderDto
import com.knu.mockin.model.dto.kisrequest.order.KISOrderRequestDto
import com.knu.mockin.model.dto.kisresponse.order.KISOverSeaResponseDto
import com.knu.mockin.model.enum.ExchangeCode
import com.knu.mockin.model.enum.TradeId
import com.knu.mockin.repository.UserRepository
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service

@Service
class OrderService(
    private val kisOverSeaClient: KISOverSeaClient,
    private val userRepository: UserRepository
) {
    suspend fun order(): KISOverSeaResponseDto {
        val user = userRepository.findById(1).awaitFirst()
        val kisOverSeaRequestHeaderDto = KISOverSeaRequestHeaderDto(
            authorization = "Bearer ${user.token}",
            appKey = user.appKey,
            appSecret = user.appSecret,
            transactionId = TradeId.SHANGHAI_BUY.name
        )
        val kisOrderRequestDto = KISOrderRequestDto(
            accountNumber = user.accountNumber,
            accountProductCode = "01",
            overseasExchangeCode = ExchangeCode.SHAA.name,
            productNumber = "600330",
            orderQuantity = "1",
            overseasOrderUnitPrice = "0",
        )
        return kisOverSeaClient.postOrder(kisOverSeaRequestHeaderDto, kisOrderRequestDto).awaitSingle()
    }
}