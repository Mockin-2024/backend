package com.knu.mockin.service

import com.knu.mockin.kisclient.KISTradingClient
import com.knu.mockin.logging.model.LogAPIEntry
import com.knu.mockin.logging.utils.LogUtil
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
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class TradingService(
    private val kisTradingClient: KISTradingClient,
    private val userRepository: UserRepository
) {
    private val log = LoggerFactory.getLogger(TradingService::class.java)
    suspend fun postOrder(): KISOrderResponseDto {
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
        return kisTradingClient
            .postOrder(kisOverSeaRequestHeaderDto, kisOrderRequestDto)
            .awaitSingle()
            .component2()
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
            accountNumber = user.accountNumber,                       // 종합계좌번호
            accountProductCode = "01",                        // 계좌상품코드
            overseasExchangeCode = ExchangeCode.NASD.name,                  // 해외거래소코드
            transactionCurrencyCode = TradeCurrencyCode.USD.name,                  // 거래통화코드
            continuousSearchCondition200 = "",    // 연속조회검색조건200
            continuousSearchKey200 = ""                 // 연속조회키200
        )
        return kisTradingClient
            .getBalance(kisOverSeaRequestHeaderDto, kisBalanceRequestDto)
            .awaitSingle()
            .component2()
    }
}