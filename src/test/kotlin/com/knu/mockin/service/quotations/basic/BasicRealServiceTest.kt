package com.knu.mockin.service.quotations.basic

import com.knu.mockin.dsl.RestDocsUtils.readJsonFile
import com.knu.mockin.dsl.toDto
import com.knu.mockin.kisclient.quotations.basic.KISBasicRealClient
import com.knu.mockin.model.dto.kisresponse.quotations.basic.real.*
import com.knu.mockin.model.dto.request.quotations.basic.real.*
import com.knu.mockin.model.entity.UserWithKeyPair
import com.knu.mockin.model.enum.TradeId
import com.knu.mockin.repository.UserRepository
import com.knu.mockin.service.quotations.basic.real.BasicRealService
import com.knu.mockin.service.util.ServiceUtil.createHeader
import com.knu.mockin.util.RedisUtil
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.data.redis.core.RedisTemplate
import reactor.core.publisher.Mono

class BasicRealServiceTest(
    private val kisBasicRealClient: KISBasicRealClient = mockk<KISBasicRealClient>(),
    private val userRepository: UserRepository = mockk<UserRepository>()
) : BehaviorSpec({
    val basicRealService = BasicRealService(kisBasicRealClient, userRepository)
    val user = readJsonFile("setting", "userWithKeyPair.json") toDto UserWithKeyPair::class.java
    val redisTemplate = mockk<RedisTemplate<String, String>>()
    RedisUtil.init(redisTemplate)

    beforeTest{
        every { userRepository.findByEmailWithRealKey(any()) } returns Mono.just(user)
        every { RedisUtil.getToken(any()) } returns "token"
    }

    Context("getCountriesHoliday 함수의 경우") {
        val uri = "/quotations/basic/countries-holiday"

        Given("적절한 dto가 주어질 때") {
            val bodyDto = readJsonFile(uri, "requestDto.json") toDto CountriesHolidayRequestParameterDto::class.java
            val requestDto = bodyDto.asDomain()
            val headerDto = createHeader(user, TradeId.getTradeIdByEnum(TradeId.COUNTRIES_HOLIDAY), true)
            val expectedDto = readJsonFile(uri, "responseDto.json") toDto KISCountriesHolidayResponseDto::class.java

            When("KIS API로 요청을 보내면") {
                every { kisBasicRealClient.getCountriesHoliday(headerDto, requestDto) } returns Mono.just(expectedDto)

                Then("응답 DTO를 정상적으로 받아야 한다.") {
                    val result = basicRealService.getCountriesHoliday(bodyDto, user.email)
                    result shouldBe expectedDto
                }
            }
        }
    }

    Context("getPriceDetail 함수의 경우") {
        val uri = "/quotations/basic/price-detail"

        Given("적절한 dto가 주어질 때") {
            val bodyDto = readJsonFile(uri, "requestDto.json") toDto PriceDetailRequestParameterDto::class.java
            val requestDto = bodyDto.asDomain()
            val headerDto = createHeader(user, TradeId.getTradeIdByEnum(TradeId.PRICE_DETAIL), true)
            val expectedDto = readJsonFile(uri, "responseDto.json") toDto KISPriceDetailResponseDto::class.java

            When("KIS API로 요청을 보내면") {
                every { kisBasicRealClient.getPriceDetail(headerDto, requestDto) } returns Mono.just(expectedDto)

                Then("응답 DTO를 정상적으로 받아야 한다.") {
                    val result = basicRealService.getPriceDetail(bodyDto, user.email)
                    result shouldBe expectedDto
                }
            }
        }
    }

    Context("getInquireTimeItemChartPrice 함수의 경우") {
        val uri = "/quotations/basic/inquire-time-itemchartprice"

        Given("적절한 dto가 주어질 때") {
            val bodyDto = readJsonFile(uri, "requestDto.json") toDto InquireTimeItemChartPriceRequestParameterDto::class.java
            val requestDto = bodyDto.asDomain()
            val headerDto = createHeader(user, TradeId.getTradeIdByEnum(TradeId.INQUIRE_TIME_ITEM_CHART_PRICE), true)
            val expectedDto = readJsonFile(uri, "responseDto.json") toDto KISInquireTimeItemChartPriceResponseDto::class.java

            When("KIS API로 요청을 보내면") {
                every { kisBasicRealClient.getInquireTimeItemChartPrice(headerDto, requestDto) } returns Mono.just(expectedDto)

                Then("응답 DTO를 정상적으로 받아야 한다.") {
                    val result = basicRealService.getInquireTimeItemChartPrice(bodyDto, user.email)
                    result shouldBe expectedDto
                }
            }
        }
    }

    Context("getInquireTimeIndexChartPrice 함수의 경우") {
        val uri = "/quotations/basic/inquire-time-indexchartprice"

        Given("적절한 dto가 주어질 때") {
            val bodyDto = readJsonFile(uri, "requestDto.json") toDto InquireTimeIndexChartPriceRequestParameterDto::class.java
            val requestDto = bodyDto.asDomain()
            val headerDto = createHeader(user, TradeId.getTradeIdByEnum(TradeId.INQUIRE_TIME_INDEX_CHART_PRICE), true)
            val expectedDto = readJsonFile(uri, "responseDto.json") toDto KISInquireTimeIndexChartPriceResponseDto::class.java

            When("KIS API로 요청을 보내면") {
                every { kisBasicRealClient.getInquireTimeIndexChartPrice(headerDto, requestDto) } returns Mono.just(expectedDto)

                Then("응답 DTO를 정상적으로 받아야 한다.") {
                    val result = basicRealService.getInquireTimeIndexChartPrice(bodyDto, user.email)
                    result shouldBe expectedDto
                }
            }
        }
    }

    Context("getSearchInfo 함수의 경우") {
        val uri = "/quotations/basic/search-info"

        Given("적절한 dto가 주어질 때") {
            val bodyDto = readJsonFile(uri, "requestDto.json") toDto SearchInfoRequestParameterDto::class.java
            val requestDto = bodyDto.asDomain()
            val headerDto = createHeader(user, TradeId.getTradeIdByEnum(TradeId.SEARCH_INFO), true)
            val expectedDto = readJsonFile(uri, "responseDto.json") toDto KISSearchInfoResponseDto::class.java

            When("KIS API로 요청을 보내면") {
                every { kisBasicRealClient.getSearchInfo(headerDto, requestDto) } returns Mono.just(expectedDto)

                Then("응답 DTO를 정상적으로 받아야 한다.") {
                    val result = basicRealService.getSearchInfo(bodyDto, user.email)
                    result shouldBe expectedDto
                }
            }
        }
    }

    Context("getInquireAskingPrice 함수의 경우") {
        val uri = "/quotations/basic/inquire-asking-price"

        Given("적절한 dto가 주어질 때") {
            val bodyDto = readJsonFile(uri, "requestDto.json") toDto InquireAskingPriceRequestParameterDto::class.java
            val requestDto = bodyDto.asDomain()
            val headerDto = createHeader(user, TradeId.getTradeIdByEnum(TradeId.INQUIRE_ASKING_PRICE))
            val expectedDto = readJsonFile(uri, "responseDto.json") toDto KISInquireAskingPriceResponseDto::class.java

            When("KIS API로 요청을 보내면") {
                every { kisBasicRealClient.getInquireAskingPrice(headerDto, requestDto) } returns Mono.just(expectedDto)

                Then("응답 DTO를 정상적으로 받아야 한다.") {
                    val result = basicRealService.getInquireAskingPrice(bodyDto, user.email)
                    result shouldBe expectedDto
                }
            }
        }
    }


})