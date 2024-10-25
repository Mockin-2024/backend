package com.knu.mockin.service

import com.knu.mockin.dsl.RestDocsUtils.readJsonFile
import com.knu.mockin.dsl.toDto
import com.knu.mockin.kisclient.KISTradingClient
import com.knu.mockin.model.dto.kisresponse.trading.KISNCCSResponseDto
import com.knu.mockin.model.dto.kisresponse.trading.KISOrderResponseDto
import com.knu.mockin.model.dto.kisresponse.trading.KISOrderReverseResponseDto
import com.knu.mockin.model.dto.request.trading.NCCSRequestParameterDto
import com.knu.mockin.model.dto.request.trading.OrderRequestBodyDto
import com.knu.mockin.model.dto.request.trading.OrderReverseRequestBodyDto
import com.knu.mockin.model.dto.request.trading.asDomain
import com.knu.mockin.model.entity.UserWithKeyPair
import com.knu.mockin.model.enum.TradeId
import com.knu.mockin.repository.UserRepository
import com.knu.mockin.service.util.ServiceUtil.createHeader
import com.knu.mockin.util.RedisUtil
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.data.redis.core.RedisTemplate
import reactor.core.publisher.Mono

class TradingServiceTest(
    private val kisTradingClient: KISTradingClient = mockk<KISTradingClient>(),
    private val userRepository: UserRepository = mockk<UserRepository>()
):BehaviorSpec({
    val tradingService = TradingService(kisTradingClient, userRepository)
    val user = readJsonFile("setting", "userWithKeyPair.json") toDto UserWithKeyPair::class.java
    val redisTemplate = mockk<RedisTemplate<String, String>>()
    RedisUtil.init(redisTemplate)

    beforeTest{
        every { userRepository.findByEmailWithMockKey(any()) } returns Mono.just(user)
        every { RedisUtil.getToken(any()) } returns "token"
    }

    val baseUri = "/trading"
    Context("postOrder 함수의 경우"){
        val uri = "$baseUri/order"

        Given("적절한 dto가 주어질 때"){
            val bodyDto = readJsonFile(uri, "requestDto.json") toDto OrderRequestBodyDto::class.java
            val requestDto = bodyDto.asDomain(user.accountNumber)
            val headerDto = createHeader(user, bodyDto.transactionId)
            val expectedDto = readJsonFile(uri, "responseDto.json") toDto KISOrderResponseDto::class.java

            When("KIS API로 요청을 보내면"){
                every { kisTradingClient.postOrder(headerDto, requestDto) } returns Mono.just(expectedDto)

                Then("응답 DTO를 정상적으로 받아야 한다."){
                    val result = tradingService.postOrder(bodyDto, user.email)
                    result shouldBe expectedDto
                }
            }
        }
    }

    Context("postOrderReverse 함수의 경우"){
        val uri = "$baseUri/order-reverse"

        Given("적절한 dto가 주어질 때"){
            val bodyDto = readJsonFile(uri, "requestDto.json") toDto OrderReverseRequestBodyDto::class.java
            val requestDto = bodyDto.asDomain(user.accountNumber)
            val headerDto = createHeader(user, bodyDto.transactionId)
            val expectedDto = readJsonFile(uri, "responseDto.json") toDto KISOrderReverseResponseDto::class.java

            When("KIS API로 요청을 보내면"){
                every { kisTradingClient.postOrderReverse(headerDto, requestDto) } returns Mono.just(expectedDto)

                Then("응답 DTO를 정상적으로 받아야 한다."){
                    val result = tradingService.postOrderReverse(bodyDto, user.email)
                    result shouldBe expectedDto
                }
            }
        }
    }

    Context("getNCCS 함수의 경우"){
        val uri = "$baseUri/nccs"

        Given("적절한 dto가 주어질 때"){
            val bodyDto = readJsonFile(uri, "requestDto.json") toDto NCCSRequestParameterDto::class.java
            val requestDto = bodyDto.asDomain(user.accountNumber)
            val headerDto = createHeader(user, TradeId.getTradeIdByEnum(TradeId.INQUIRE_NCCS))
            val expectedDto = readJsonFile(uri, "responseDto.json") toDto KISNCCSResponseDto::class.java

            When("KIS API로 요청을 보내면"){
                every { kisTradingClient.getNCCS(headerDto, requestDto) } returns Mono.just(expectedDto)

                Then("응답 DTO를 정상적으로 받아야 한다."){
                    val result = tradingService.getNCCS(bodyDto, user.email)
                    result shouldBe expectedDto
                }
            }
        }
    }
})