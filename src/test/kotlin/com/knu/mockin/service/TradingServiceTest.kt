package com.knu.mockin.service

import com.knu.mockin.dsl.RestDocsUtils.readJsonFile
import com.knu.mockin.kisclient.KISTradingClient
import com.knu.mockin.model.dto.kisresponse.trading.*
import com.knu.mockin.model.dto.request.trading.*
import com.knu.mockin.model.entity.UserWithKeyPair
import com.knu.mockin.model.enum.Constant.JWT
import com.knu.mockin.model.enum.Constant.MOCK
import com.knu.mockin.model.enum.Constant.REAL
import com.knu.mockin.model.enum.TradeId
import com.knu.mockin.repository.UserRepository
import com.knu.mockin.service.util.ServiceUtil.createHeader
import com.knu.mockin.util.ExtensionUtil.toDto
import com.knu.mockin.util.RedisUtil
import com.knu.mockin.util.tag
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
        every { RedisUtil.getToken(user.email tag JWT) } returns "token"
        every { RedisUtil.getToken(user.email tag MOCK) } returns "token"
        every { RedisUtil.getToken(user.email tag REAL) } returns "token"
        every { RedisUtil.deleteData(any())} returns true
    }

    val baseUri = "/trading"
    Context("postOrder 함수의 경우"){
        val uri = "$baseUri/order"

        Given("요청이 들어오면"){
            val bodyDto = readJsonFile(uri, "requestDto.json") toDto OrderRequestBodyDto::class.java
            val requestDto = bodyDto.asDomain(user.accountNumber)
            val headerDto = createHeader(user, bodyDto.transactionId)
            val expectedDto = readJsonFile(uri, "responseDto.json") toDto KISOrderResponseDto::class.java

            When("KIS API로 요청을 보낸 후"){
                every { kisTradingClient.postOrder(headerDto, requestDto) } returns Mono.just(expectedDto)

                Then("redis 캐시를 비우고, 응답 DTO를 정상적으로 받아야 한다."){
                    val result = tradingService.postOrder(bodyDto, user.email)
                    result shouldBe expectedDto
                }
            }
        }
    }

    Context("postOrderReverse 함수의 경우"){
        val uri = "$baseUri/order-reverse"

        Given("요청이 들어오면"){
            val bodyDto = readJsonFile(uri, "requestDto.json") toDto OrderReverseRequestBodyDto::class.java
            val requestDto = bodyDto.asDomain(user.accountNumber)
            val headerDto = createHeader(user, bodyDto.transactionId)
            val expectedDto = readJsonFile(uri, "responseDto.json") toDto KISOrderReverseResponseDto::class.java

            When("KIS API로 요청을 보낸 후"){
                every { kisTradingClient.postOrderReverse(headerDto, requestDto) } returns Mono.just(expectedDto)

                Then("redis 캐시를 비우고, 응답 DTO를 정상적으로 받아야 한다."){
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
        val bodyDto = readJsonFile(uri, "requestDto.json") toDto NCCSRequestParameterDto::class.java
        val redisCacheKey = user.email tag
                "getNCCS" tag
                bodyDto.overseasExchangeCode tag
                bodyDto.continuousSearchKey200 tag
                bodyDto.continuousSearchCondition200

        Given("Redis 캐시에 값이 없을 때"){
            val requestDto = bodyDto.asDomain(user.accountNumber)
            val headerDto = createHeader(user, TradeId.getTradeIdByEnum(TradeId.INQUIRE_NCCS))
            val expectedDto = readJsonFile(uri, "responseDto.json") toDto KISNCCSResponseDto::class.java

            When("KIS API로 요청을 보낸 후"){
                every { RedisUtil.getData(redisCacheKey)} returns null
                every { kisTradingClient.getNCCS(headerDto, requestDto) } returns Mono.just(expectedDto)
                every { RedisUtil.setData(redisCacheKey, expectedDto, any()) } returns Unit

                Then("응답 DTO를 redis에 저장하고, 반환해야 한다."){
                    val result = tradingService.getNCCS(bodyDto, user.email)
                    result shouldBe expectedDto
                }
            }
        }

        Given("Redis 캐시에 값이 있을 때"){
            val expectedDto = readJsonFile(uri, "responseDto.json")

            When("redis에서 값을 가져와"){
                every { RedisUtil.getData(redisCacheKey)} returns expectedDto

                Then("응답 DTO를 반환해야 한다."){
                    val result = tradingService.getNCCS(bodyDto, user.email)
                    result shouldBe (expectedDto toDto KISNCCSResponseDto::class.java)
                }
            }
        }
    }

    Context("getBalance 함수의 경우"){
        val uri = "$baseUri/balance"
        val bodyDto = readJsonFile(uri, "requestDto.json") toDto BalanceRequestParameterDto::class.java
        val redisCacheKey = user.email tag
                "getBalance" tag
                bodyDto.overseasExchangeCode tag
                bodyDto.transactionCurrencyCode tag
                bodyDto.continuousSearchKey200 tag
                bodyDto.continuousSearchCondition200


        Given("Redis 캐시에 값이 없을 때"){
            val requestDto = bodyDto.asDomain(user.accountNumber)
            val headerDto = createHeader(user, TradeId.getTradeIdByEnum(TradeId.INQUIRE_BALANCE))
            val expectedDto = readJsonFile(uri, "responseDto.json") toDto KISBalanceResponseDto::class.java

            When("KIS API로 요청을 보낸 후"){
                every { RedisUtil.getData(redisCacheKey)} returns null
                every { kisTradingClient.getBalance(headerDto, requestDto) } returns Mono.just(expectedDto)
                every { RedisUtil.setData(redisCacheKey, expectedDto, any()) } returns Unit

                Then("응답 DTO를 redis에 저장하고, 반환해야 한다."){
                    val result = tradingService.getBalance(bodyDto, user.email)
                    result shouldBe expectedDto
                }
            }
        }

        Given("Redis 캐시에 값이 있을 때"){
            val expectedDto = readJsonFile(uri, "responseDto.json")

            When("redis에서 값을 가져와"){
                every { RedisUtil.getData(redisCacheKey)} returns expectedDto

                Then("응답 DTO를 반환해야 한다."){
                    val result = tradingService.getBalance(bodyDto, user.email)
                    result shouldBe (expectedDto toDto KISBalanceResponseDto::class.java)
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
        val bodyDto = readJsonFile(uri, "requestDto.json") toDto PresentBalanceRequestParameterDto::class.java
        val redisCacheKey = user.email tag
                "getPresentBalance" tag
                bodyDto.currencyDivisionCode tag
                bodyDto.countryCode tag
                bodyDto.marketCode tag
                bodyDto.inquiryDivisionCode


        Given("Redis 캐시에 값이 없을 때"){
            val requestDto = bodyDto.asDomain(user.accountNumber)
            val headerDto = createHeader(user, TradeId.getTradeIdByEnum(TradeId.INQUIRE_PRESENT_BALANCE))
            val expectedDto = readJsonFile(uri, "responseDto.json") toDto KISPresentBalanceResponseDto::class.java

            When("KIS API로 요청을 보낸 후"){
                every { RedisUtil.getData(redisCacheKey)} returns null
                every { kisTradingClient.getPresentBalance(headerDto, requestDto) } returns Mono.just(expectedDto)
                every { RedisUtil.setData(redisCacheKey, expectedDto, any()) } returns Unit

                Then("응답 DTO를 redis에 저장하고, 반환해야 한다."){
                    val result = tradingService.getPresentBalance(bodyDto, user.email)
                    result shouldBe expectedDto
                }
            }
        }

        Given("Redis 캐시에 값이 있을 때"){
            val expectedDto = readJsonFile(uri, "responseDto.json")

            When("redis에서 값을 가져와"){
                every { RedisUtil.getData(redisCacheKey)} returns expectedDto

                Then("응답 DTO를 반환해야 한다."){
                    val result = tradingService.getPresentBalance(bodyDto, user.email)
                    result shouldBe (expectedDto toDto KISPresentBalanceResponseDto::class.java)
                }
            }
        }
    }

    Context("getCCNL 함수의 경우"){
        val uri = "$baseUri/ccnl"
        val bodyDto = readJsonFile(uri, "requestDto.json") toDto CCNLRequestParameterDto::class.java
        val redisCacheKey = user.email tag
                "getCCNL" tag
                bodyDto.orderStartDate tag
                bodyDto.orderEndDate tag
                bodyDto.continuousSearchKey200 tag
                bodyDto.continuousSearchCondition200


        Given("Redis 캐시에 값이 없을 때"){
            val requestDto = bodyDto.asDomain(user.accountNumber)
            val headerDto = createHeader(user, TradeId.getTradeIdByEnum(TradeId.INQUIRE_CCNL))
            headerDto.transactionContinuation = bodyDto.transactionContinuation
            val expectedDto = readJsonFile(uri, "responseDto.json") toDto KISCCNLResponseDto::class.java

            When("KIS API로 요청을 보낸 후"){
                every { RedisUtil.getData(redisCacheKey)} returns null
                every { kisTradingClient.getCCNL(headerDto, requestDto) } returns Mono.just(expectedDto)
                every { RedisUtil.setData(redisCacheKey, expectedDto, any()) } returns Unit

                Then("응답 DTO를 redis에 저장하고, 반환해야 한다."){
                    val result = tradingService.getCCNL(bodyDto, user.email)
                    result shouldBe expectedDto
                }
            }
        }

        Given("Redis 캐시에 값이 있을 때"){
            val expectedDto = readJsonFile(uri, "responseDto.json")

            When("redis에서 값을 가져와"){
                every { RedisUtil.getData(redisCacheKey)} returns expectedDto

                Then("응답 DTO를 반환해야 한다."){
                    val result = tradingService.getCCNL(bodyDto, user.email)
                    result shouldBe (expectedDto toDto KISCCNLResponseDto::class.java)
                }
            }
        }
    }
})