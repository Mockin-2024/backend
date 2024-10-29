package com.knu.mockin.kisclient

import com.knu.mockin.dsl.RestDocsUtils
import com.knu.mockin.dsl.toDto
import com.knu.mockin.exeption.CustomException
import com.knu.mockin.exeption.ErrorCode
import com.knu.mockin.model.dto.request.trading.BalanceRequestParameterDto
import com.knu.mockin.model.dto.request.trading.OrderRequestBodyDto
import com.knu.mockin.model.dto.request.trading.asDomain
import com.knu.mockin.model.enum.TradeId
import com.knu.mockin.repository.UserRepository
import com.knu.mockin.service.util.ServiceUtil
import io.kotest.core.spec.style.FunSpec
import org.reactivestreams.Publisher
import org.springframework.boot.test.context.SpringBootTest
import reactor.test.StepVerifier

@SpringBootTest
class KISTradingClientTest(
    val kisTradingClient: KISTradingClient,
    val userRepository: UserRepository
): FunSpec({
    xcontext("TradingClient 테스트"){
        val baseUri = "trading"
        val user = userRepository.findByEmailWithMockKey("rkdgustn123@knu.ac.kr").block()

        test("postOrder 요청 보내기"){
            val uri = "${baseUri}/order"
            val requestDto = RestDocsUtils.readJsonFile(uri, "requestDto.json") toDto OrderRequestBodyDto::class.java
            val bodyDto = requestDto.asDomain(user!!.accountNumber)
            val headerDto = ServiceUtil.createHeader(user, requestDto.transactionId)

            val response = kisTradingClient.postOrder(headerDto, bodyDto)

            StepVerifier.create(response as Publisher<out Any>)
                .expectErrorMatches{ it is CustomException && it.errorCode == ErrorCode.KIS_API_FAILED }
                .verify()
        }

        test("getBalance 요청 보내기"){
            val uri = "${baseUri}/balance"
            val requestDto = RestDocsUtils.readJsonFile(uri, "requestDto.json") toDto BalanceRequestParameterDto::class.java
            val bodyDto = requestDto.asDomain(user!!.accountNumber)
            val headerDto = ServiceUtil.createHeader(user, TradeId.getTradeIdByEnum(TradeId.INQUIRE_BALANCE))

            val response = kisTradingClient.getBalance(headerDto, bodyDto).block()

            println(response)
        }
    }
})