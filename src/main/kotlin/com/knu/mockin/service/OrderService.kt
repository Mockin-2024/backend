package com.knu.mockin.service

import com.knu.mockin.kisclient.KISOverSeaClient
import com.knu.mockin.model.dto.kisheader.request.KISOverSeaRequestHeaderDto
import com.knu.mockin.model.dto.kisrequest.order.KISOrderRequestDto
import com.knu.mockin.model.dto.kisresponse.order.KISOverSeaResponseDto
import com.knu.mockin.model.enum.ExchangeCode
import com.knu.mockin.model.enum.TradeId
import com.knu.mockin.repository.UserRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class OrderService(
    private val kisOverSeaClient: KISOverSeaClient,
    private val userRepository: UserRepository
) {
    fun order(): Mono<KISOverSeaResponseDto> {
        return userRepository.findById(1).flatMap {
            user ->
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
            kisOverSeaClient.postOrder(kisOverSeaRequestHeaderDto, kisOrderRequestDto)
        }
    }
}