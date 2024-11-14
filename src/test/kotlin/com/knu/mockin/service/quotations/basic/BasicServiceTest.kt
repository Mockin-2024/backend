package com.knu.mockin.service.quotations.basic

import com.knu.mockin.dsl.RestDocsUtils.readJsonFile
import com.knu.mockin.kisclient.quotations.basic.KISBasicClient
import com.knu.mockin.model.dto.kisresponse.quotations.basic.mock.*
import com.knu.mockin.model.dto.request.quotations.basic.mock.*
import com.knu.mockin.model.entity.UserWithKeyPair
import com.knu.mockin.model.enum.Constant
import com.knu.mockin.model.enum.Constant.JWT
import com.knu.mockin.model.enum.Constant.MOCK
import com.knu.mockin.model.enum.Constant.REAL
import com.knu.mockin.model.enum.TradeId
import com.knu.mockin.repository.UserRepository
import com.knu.mockin.service.quotations.basic.mock.BasicService
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
        every { RedisUtil.getToken(user.email tag JWT) } returns "token"
        every { RedisUtil.getToken(user.email tag MOCK) } returns "token"
        every { RedisUtil.getToken(user.email tag REAL) } returns "token"
    }

    Context("getPrice 함수의 경우") {
        val uri = "/quotations/basic/price"

        Given("Redis 캐시에 값이 없을 때") {
            val bodyDto = readJsonFile(uri, "requestDto.json") toDto PriceRequestParameterDto::class.java
            val redisCacheKey = "getPrice" tag bodyDto.SYMB
            val requestDto = bodyDto.asDomain()
            val headerDto = createHeader(user, TradeId.getTradeIdByEnum(TradeId.PRICE))
            val expectedDto = readJsonFile(uri, "responseDto.json") toDto KISPriceResponseDto::class.java

            When("KIS API로 요청을 보내면") {
                every { RedisUtil.getData(redisCacheKey)} returns null
                every { kisBasicClient.getPrice(headerDto, requestDto) } returns Mono.just(expectedDto)
                every { RedisUtil.setData(redisCacheKey, expectedDto, any()) } returns Unit

                Then("응답 DTO를 redis에 저장하고, 반환해야 한다.") {
                    val result = basicService.getPrice(bodyDto, user.email)
                    result shouldBe expectedDto
                }
            }
        }

        Given("Redis 캐시에 값이 있을 때") {
            val bodyDto = readJsonFile(uri, "requestDto.json") toDto PriceRequestParameterDto::class.java
            val redisCacheKey = "getPrice" tag bodyDto.SYMB
            val expectedDto = readJsonFile(uri, "responseDto.json")

            When("redis에서 값을 가져와") {
                every { RedisUtil.getData(redisCacheKey)} returns expectedDto

                Then("응답 DTO를 반환해야 한다.") {
                    val result = basicService.getPrice(bodyDto, user.email)
                    result shouldBe (expectedDto toDto KISPriceResponseDto::class.java)
                }
            }
        }
    }

    Context("getDailyPrice 함수의 경우") {
        val uri = "/quotations/basic/daily-price"

        Given("Redis 캐시에 값이 없을 때") {
            val bodyDto = readJsonFile(uri, "requestDto.json") toDto DailyPriceRequestParameterDto::class.java
            val redisCacheKey = "getDailyPrice" tag
                    bodyDto.SYMB tag
                    bodyDto.GUBN tag
                    bodyDto.MODP tag
                    bodyDto.BYMD
            val requestDto = bodyDto.asDomain()
            val headerDto = createHeader(user, TradeId.getTradeIdByEnum(TradeId.DAILY_PRICE))
            val expectedDto = readJsonFile(uri, "responseDto.json") toDto KISDailyPriceResponseDto::class.java

            When("KIS API로 요청을 보내면") {
                every { RedisUtil.getData(redisCacheKey)} returns null
                every { kisBasicClient.getDailyPrice(headerDto, requestDto) } returns Mono.just(expectedDto)
                every { RedisUtil.setData(redisCacheKey, expectedDto, any()) } returns Unit

                Then("응답 DTO를 redis에 저장하고, 반환해야 한다.") {
                    val result = basicService.getDailyPrice(bodyDto, user.email)
                    result shouldBe expectedDto
                }
            }
        }

        Given("Redis 캐시에 값이 있을 때") {
            val bodyDto = readJsonFile(uri, "requestDto.json") toDto DailyPriceRequestParameterDto::class.java
            val redisCacheKey = "getDailyPrice" tag
                    bodyDto.SYMB tag
                    bodyDto.GUBN tag
                    bodyDto.MODP tag
                    bodyDto.BYMD
            val expectedDto = readJsonFile(uri, "responseDto.json")

            When("redis에서 값을 가져와") {
                every { RedisUtil.getData(redisCacheKey)} returns expectedDto

                Then("응답 DTO를 반환해야 한다.") {
                    val result = basicService.getDailyPrice(bodyDto, user.email)
                    result shouldBe (expectedDto toDto KISDailyPriceResponseDto::class.java)
                }
            }
        }
    }

    Context("getInquireDailyChartPrice 함수의 경우") {
        val uri = "/quotations/basic/inquire-daily-chartprice"

        Given("Redis 캐시에 값이 없을 때") {
            val bodyDto = readJsonFile(uri, "requestDto.json") toDto InquireDailyChartPriceRequestParameterDto::class.java
            val redisCacheKey = "getInquireDailyChartPrice" tag
                    bodyDto.fidInputIscd tag
                    bodyDto.fidCondMrktDivCode tag
                    bodyDto.fidPeriodDivCode tag
                    bodyDto.fidInputDate1 tag
                    bodyDto.fidInputDate2
            val requestDto = bodyDto.asDomain()
            val headerDto = createHeader(user, TradeId.getTradeIdByEnum(TradeId.INQUIRE_DAILY_CHART_PRICE))
            val expectedDto = readJsonFile(uri, "responseDto.json") toDto KISInquireDailyChartPriceResponseDto::class.java

            When("KIS API로 요청을 보내면") {
                every { RedisUtil.getData(redisCacheKey)} returns null
                every { kisBasicClient.getInquireDailyChartPrice(headerDto, requestDto) } returns Mono.just(expectedDto)
                every { RedisUtil.setData(redisCacheKey, expectedDto, any()) } returns Unit

                Then("응답 DTO를 redis에 저장하고, 반환해야 한다.") {
                    val result = basicService.getInquireDailyChartPrice(bodyDto, user.email)
                    result shouldBe expectedDto
                }
            }
        }

        Given("Redis 캐시에 값이 있을 때") {
            val bodyDto = readJsonFile(uri, "requestDto.json") toDto InquireDailyChartPriceRequestParameterDto::class.java
            val redisCacheKey = "getInquireDailyChartPrice" tag
                    bodyDto.fidInputIscd tag
                    bodyDto.fidCondMrktDivCode tag
                    bodyDto.fidPeriodDivCode tag
                    bodyDto.fidInputDate1 tag
                    bodyDto.fidInputDate2
            val expectedDto = readJsonFile(uri, "responseDto.json")

            When("redis에서 값을 가져와") {
                every { RedisUtil.getData(redisCacheKey)} returns expectedDto

                Then("응답 DTO를 반환해야 한다.") {
                    val result = basicService.getInquireDailyChartPrice(bodyDto, user.email)
                    result shouldBe (expectedDto toDto KISInquireDailyChartPriceResponseDto::class.java)
                }
            }
        }
    }

    Context("getInquireSearch 함수의 경우") {
        val uri = "/quotations/basic/inquire-search"

        val bodyDto = readJsonFile(uri, "requestDto.json") toDto InquireSearchRequestParameterDto::class.java
        val requestDto = bodyDto.asDomain()
        val headerDto = createHeader(user, TradeId.getTradeIdByEnum(TradeId.INQUIRE_SEARCH))
        val expectedDto = readJsonFile(uri, "responseDto.json") toDto KISInquireSearchResponseDto::class.java

        When("KIS API로 요청을 보내면") {
            every { kisBasicClient.getInquireSearch(headerDto, requestDto) } returns Mono.just(expectedDto)

            val result = basicService.getInquireSearch(bodyDto, user.email)

            result shouldBe expectedDto

        }
    }
})