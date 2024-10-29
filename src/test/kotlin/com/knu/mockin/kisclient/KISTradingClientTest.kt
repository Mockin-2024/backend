package com.knu.mockin.kisclient

import com.knu.mockin.dsl.RestDocsUtils
import com.knu.mockin.dsl.toDto
import com.knu.mockin.exeption.CustomException
import com.knu.mockin.exeption.ErrorCode
import com.knu.mockin.model.dto.request.trading.*
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

        test("postOrderReverse 요청 보내기"){
            val uri = "${baseUri}/order-reverse"
            val requestDto = RestDocsUtils.readJsonFile(uri, "requestDto.json") toDto OrderReverseRequestBodyDto::class.java
            val bodyDto = requestDto.asDomain(user!!.accountNumber)
            val headerDto = ServiceUtil.createHeader(user, requestDto.transactionId)

            val response = kisTradingClient.postOrderReverse(headerDto, bodyDto)

            StepVerifier.create(response as Publisher<out Any>)
                .expectErrorMatches{ it is CustomException && it.errorCode == ErrorCode.KIS_API_FAILED }
                .verify()
        }

        test("getNCCS 요청 보내기"){
            val uri = "${baseUri}/nccs"
            val requestDto = RestDocsUtils.readJsonFile(uri, "requestDto.json") toDto NCCSRequestParameterDto::class.java
            val bodyDto = requestDto.asDomain(user!!.accountNumber)
            val headerDto = ServiceUtil.createHeader(user, TradeId.getTradeIdByEnum(TradeId.INQUIRE_NCCS))

            val response = kisTradingClient.getNCCS(headerDto, bodyDto).block()

            println(response)
        }

        test("getBalance 요청 보내기"){
            val uri = "${baseUri}/balance"
            val requestDto = RestDocsUtils.readJsonFile(uri, "requestDto.json") toDto BalanceRequestParameterDto::class.java
            val bodyDto = requestDto.asDomain(user!!.accountNumber)
            val headerDto = ServiceUtil.createHeader(user, TradeId.getTradeIdByEnum(TradeId.INQUIRE_BALANCE))

            val response = kisTradingClient.getBalance(headerDto, bodyDto).block()

            println(response)
        }

        test("getPsAmount 요청 보내기"){
            val uri = "${baseUri}/psamount"
            val requestDto = RestDocsUtils.readJsonFile(uri, "requestDto.json") toDto PsAmountRequestParameterDto::class.java
            val bodyDto = requestDto.asDomain(user!!.accountNumber)
            val headerDto = ServiceUtil.createHeader(user, TradeId.getTradeIdByEnum(TradeId.INQUIRE_PSAMOUNT))

            val response = kisTradingClient.getPsAmount(headerDto, bodyDto).block()

            println(response)
        }

        test("getPresentBalance 요청 보내기"){
            val uri = "${baseUri}/present-balance"
            val requestDto = RestDocsUtils.readJsonFile(uri, "requestDto.json") toDto PresentBalanceRequestParameterDto::class.java
            val bodyDto = requestDto.asDomain(user!!.accountNumber)
            val headerDto = ServiceUtil.createHeader(user, TradeId.getTradeIdByEnum(TradeId.INQUIRE_PRESENT_BALANCE))

            val response = kisTradingClient.getPresentBalance(headerDto, bodyDto).block()

            println(response)
        }

        test("getCCNL 요청 보내기"){
            val uri = "${baseUri}/ccnl"
            val requestDto = RestDocsUtils.readJsonFile(uri, "requestDto.json") toDto CCNLRequestParameterDto::class.java
            val bodyDto = requestDto.asDomain(user!!.accountNumber)
            val headerDto = ServiceUtil.createHeader(user, TradeId.getTradeIdByEnum(TradeId.INQUIRE_CCNL))

            val response = kisTradingClient.getCCNL(headerDto, bodyDto).block()

            println(response)
        }
    }
})