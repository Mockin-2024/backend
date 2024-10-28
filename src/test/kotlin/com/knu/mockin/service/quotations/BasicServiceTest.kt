package com.knu.mockin.service.quotations

import com.knu.mockin.dsl.RestDocsUtils.readJsonFile
import com.knu.mockin.dsl.toDto
import com.knu.mockin.kisclient.quotations.basic.KISBasicClient
import com.knu.mockin.model.dto.kisresponse.quotations.basic.mock.*
import com.knu.mockin.model.dto.request.quotations.basic.mock.*
import com.knu.mockin.model.entity.UserWithKeyPair
import com.knu.mockin.model.enum.TradeId
import com.knu.mockin.repository.UserRepository
import com.knu.mockin.service.quotations.basic.mock.BasicService
import com.knu.mockin.service.util.ServiceUtil.createHeader
import com.knu.mockin.util.RedisUtil
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.data.redis.core.RedisTemplate
import reactor.core.publisher.Mono

class BasicServiceTest(
    private val kisBasicClient: KISBasicClient = mockk<KISBasicClient>(),
    private val userRepository: UserRepository = mockk<UserRepository>()
) : BehaviorSpec({
    val basicService = BasicService(kisBasicClient, userRepository)
    val user = readJsonFile("setting", "userWithKeyPair.json") toDto UserWithKeyPair::class.java
    val redisTemplate = mockk<RedisTemplate<String, String>>()
    RedisUtil.init(redisTemplate)

    beforeTest{
        every { userRepository.findByEmailWithMockKey(any()) } returns Mono.just(user)
        every { RedisUtil.getToken(any()) } returns "token"
    }

    Context("getPrice 함수의 경우") {
        val uri = "/quotations/basic/price"

        Given("적절한 dto가 주어질 때") {
            val bodyDto = readJsonFile(uri, "requestDto.json") toDto PriceRequestParameterDto::class.java
            val requestDto = bodyDto.asDomain()
            val headerDto = createHeader(user, TradeId.getTradeIdByEnum(TradeId.PRICE))
            val expectedDto = readJsonFile(uri, "responseDto.json") toDto KISPriceResponseDto::class.java

            When("KIS API로 요청을 보내면") {
                every { kisBasicClient.getPrice(headerDto, requestDto) } returns Mono.just(expectedDto)

                Then("응답 DTO를 정상적으로 받아야 한다.") {
                    val result = basicService.getPrice(bodyDto, user.email)
                    result shouldBe expectedDto
                }
            }
        }
    }

    Context("getDailyPrice 함수의 경우") {
        val uri = "/quotations/basic/daily-price"

        Given("적절한 dto가 주어질 때") {
            val bodyDto = readJsonFile(uri, "requestDto.json") toDto DailyPriceRequestParameterDto::class.java
            val requestDto = bodyDto.asDomain()
            val headerDto = createHeader(user, TradeId.getTradeIdByEnum(TradeId.DAILY_PRICE))
            val expectedDto = readJsonFile(uri, "responseDto.json") toDto KISDailyPriceResponseDto::class.java

            When("KIS API로 요청을 보내면") {
                every { kisBasicClient.getDailyPrice(headerDto, requestDto) } returns Mono.just(expectedDto)

                Then("응답 DTO를 정상적으로 받아야 한다.") {
                    val result = basicService.getDailyPrice(bodyDto, user.email)
                    result shouldBe expectedDto
                }
            }
        }
    }

    Context("getInquireDailyChartPrice 함수의 경우") {
        val uri = "/quotations/basic/inquire-daily-chartprice"

        Given("적절한 dto가 주어질 때") {
            val bodyDto = readJsonFile(uri, "requestDto.json") toDto InquireDailyChartPriceRequestParameterDto::class.java
            val requestDto = bodyDto.asDomain()
            val headerDto = createHeader(user, TradeId.getTradeIdByEnum(TradeId.INQUIRE_DAILY_CHART_PRICE))
            val expectedDto = readJsonFile(uri, "responseDto.json") toDto KISInquireDailyChartPriceResponseDto::class.java

            When("KIS API로 요청을 보내면") {
                every { kisBasicClient.getInquireDailyChartPrice(headerDto, requestDto) } returns Mono.just(expectedDto)

                Then("응답 DTO를 정상적으로 받아야 한다.") {
                    val result = basicService.getInquireDailyChartPrice(bodyDto, user.email)
                    result shouldBe expectedDto
                }
            }
        }
    }

    Context("getInquireSearch 함수의 경우") {
        val uri = "/quotations/basic/inquire-search"

        Given("적절한 dto가 주어질 때") {
            val bodyDto = readJsonFile(uri, "requestDto.json") toDto InquireSearchRequestParameterDto::class.java
            val requestDto = bodyDto.asDomain()
            val headerDto = createHeader(user, TradeId.getTradeIdByEnum(TradeId.INQUIRE_SEARCH))
            val expectedDto = readJsonFile(uri, "responseDto.json") toDto KISInquireSearchResponseDto::class.java

            When("KIS API로 요청을 보내면") {
                every { kisBasicClient.getInquireSearch(headerDto, requestDto) } returns Mono.just(expectedDto)

                Then("응답 DTO를 정상적으로 받아야 한다.") {
                    val result = basicService.getInquireSearch(bodyDto, user.email)
                    result shouldBe expectedDto
                }
            }
        }
    }
})