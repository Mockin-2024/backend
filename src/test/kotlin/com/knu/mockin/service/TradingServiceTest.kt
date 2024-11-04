package com.knu.mockin.service

import com.knu.mockin.dsl.RestDocsUtils.readJsonFile
import com.knu.mockin.dsl.toDto
import com.knu.mockin.kisclient.KISTradingClient
import com.knu.mockin.model.dto.kisresponse.trading.*
import com.knu.mockin.model.dto.request.trading.*
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

    Context("postOrderReserve 함수의 경우"){
        val uri = "$baseUri/order-reserve"

        Given("적절한 dto가 주어질 때"){
            val bodyDto = readJsonFile(uri, "requestDto.json") toDto OrderReserveRequestBodyDto::class.java
            val requestDto = bodyDto.asDomain(user.accountNumber)
            val headerDto = createHeader(user, bodyDto.transactionId)
            val expectedDto = readJsonFile(uri, "responseDto.json") toDto KISOrderReserveResponseDto::class.java

            When("KIS API로 요청을 보내면"){
                every { kisTradingClient.postOrderReserve(headerDto, requestDto) } returns Mono.just(expectedDto)

                Then("응답 DTO를 정상적으로 받아야 한다."){
                    val result = tradingService.postOrderReserve(bodyDto, user.email)
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

    Context("getBalance 함수의 경우"){
        val uri = "$baseUri/balance"

        Given("적절한 dto가 주어질 때"){
            val bodyDto = readJsonFile(uri, "requestDto.json") toDto BalanceRequestParameterDto::class.java
            val requestDto = bodyDto.asDomain(user.accountNumber)
            val headerDto = createHeader(user, TradeId.getTradeIdByEnum(TradeId.INQUIRE_BALANCE))
            val expectedDto = readJsonFile(uri, "responseDto.json") toDto KISBalanceResponseDto::class.java

            When("KIS API로 요청을 보내면"){
                every { kisTradingClient.getBalance(headerDto, requestDto) } returns Mono.just(expectedDto)

                Then("응답 DTO를 정상적으로 받아야 한다."){
                    val result = tradingService.getBalance(bodyDto, user.email)
                    result shouldBe expectedDto
                }
            }
        }
    }

    Context("getPsAmount 함수의 경우"){
        val uri = "$baseUri/psamount"

        Given("적절한 dto가 주어질 때"){
            val bodyDto = readJsonFile(uri, "requestDto.json") toDto PsAmountRequestParameterDto::class.java
            val requestDto = bodyDto.asDomain(user.accountNumber)
            val headerDto = createHeader(user, TradeId.getTradeIdByEnum(TradeId.INQUIRE_PSAMOUNT))
            val expectedDto = readJsonFile(uri, "responseDto.json") toDto KISPsAmountResponseDto::class.java

            When("KIS API로 요청을 보내면"){
                every { kisTradingClient.getPsAmount(headerDto, requestDto) } returns Mono.just(expectedDto)

                Then("응답 DTO를 정상적으로 받아야 한다."){
                    val result = tradingService.getPsAmount(bodyDto, user.email)
                    result shouldBe expectedDto
                }
            }
        }
    }

    Context("getPresentBalance 함수의 경우"){
        val uri = "$baseUri/present-balance"

        Given("적절한 dto가 주어질 때"){
            val bodyDto = readJsonFile(uri, "requestDto.json") toDto PresentBalanceRequestParameterDto::class.java
            val requestDto = bodyDto.asDomain(user.accountNumber)
            val headerDto = createHeader(user, TradeId.getTradeIdByEnum(TradeId.INQUIRE_PRESENT_BALANCE))
            val expectedDto = readJsonFile(uri, "responseDto.json") toDto KISPresentBalanceResponseDto::class.java

            When("KIS API로 요청을 보내면"){
                every { kisTradingClient.getPresentBalance(headerDto, requestDto) } returns Mono.just(expectedDto)

                Then("응답 DTO를 정상적으로 받아야 한다."){
                    val result = tradingService.getPresentBalance(bodyDto, user.email)
                    result shouldBe expectedDto
                }
            }
        }
    }

    Context("getCCNL 함수의 경우"){
        val uri = "$baseUri/ccnl"

        Given("적절한 dto가 주어질 때"){
            val bodyDto = readJsonFile(uri, "requestDto.json") toDto CCNLRequestParameterDto::class.java
            val requestDto = bodyDto.asDomain(user.accountNumber)
            val headerDto = createHeader(user, TradeId.getTradeIdByEnum(TradeId.INQUIRE_CCNL))
            headerDto.transactionContinuation = bodyDto.transactionContinuation
            val expectedDto = readJsonFile(uri, "responseDto.json") toDto KISCCNLResponseDto::class.java

            When("KIS API로 요청을 보내면"){
                every { kisTradingClient.getCCNL(headerDto, requestDto) } returns Mono.just(expectedDto)

                Then("응답 DTO를 정상적으로 받아야 한다."){
                    val result = tradingService.getCCNL(bodyDto, user.email)
                    result shouldBe expectedDto
                }
            }
        }
    }
})