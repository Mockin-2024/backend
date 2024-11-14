package com.knu.mockin.service.quotations.basic

import com.knu.mockin.dsl.RestDocsUtils.readJsonFile
import com.knu.mockin.kisclient.quotations.basic.KISBasicRealClient
import com.knu.mockin.model.dto.kisresponse.quotations.basic.real.*
import com.knu.mockin.model.dto.request.quotations.basic.real.*
import com.knu.mockin.model.entity.UserWithKeyPair
import com.knu.mockin.model.enum.Constant
import com.knu.mockin.model.enum.Constant.JWT
import com.knu.mockin.model.enum.Constant.MOCK
import com.knu.mockin.model.enum.Constant.REAL
import com.knu.mockin.model.enum.TradeId
import com.knu.mockin.repository.UserRepository
import com.knu.mockin.service.quotations.basic.real.BasicRealService
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
        every { RedisUtil.getToken(user.email tag JWT) } returns "token"
        every { RedisUtil.getToken(user.email tag MOCK) } returns "token"
        every { RedisUtil.getToken(user.email tag REAL) } returns "token"
    }

    Context("getCountriesHoliday 함수의 경우") {
        val uri = "/quotations/basic/countries-holiday"

        Given("Redis 캐시에 값이 없을 때") {
            val bodyDto = readJsonFile(uri, "requestDto.json") toDto CountriesHolidayRequestParameterDto::class.java
            val redisCacheKey = "getCountriesHoliday" tag bodyDto.tradDt
            val requestDto = bodyDto.asDomain()
            val headerDto = createHeader(user, TradeId.getTradeIdByEnum(TradeId.COUNTRIES_HOLIDAY), true)
            val expectedDto = readJsonFile(uri, "responseDto.json") toDto KISCountriesHolidayResponseDto::class.java

            When("KIS API로 요청을 보내면") {
                every { RedisUtil.getData(redisCacheKey) } returns null
                every { kisBasicRealClient.getCountriesHoliday(headerDto, requestDto) } returns Mono.just(expectedDto)
                every { RedisUtil.setData(redisCacheKey, expectedDto, any()) } returns Unit

                Then("응답 DTO를 redis에 저장하고, 반환해야 한다.") {
                    val result = basicRealService.getCountriesHoliday(bodyDto, user.email)
                    result shouldBe expectedDto
                }
            }
        }

        Given("Redis 캐시에 값이 있을 때") {
            val bodyDto = readJsonFile(uri, "requestDto.json") toDto CountriesHolidayRequestParameterDto::class.java
            val redisCacheKey = "getCountriesHoliday" tag bodyDto.tradDt
            val expectedDto = readJsonFile(uri, "responseDto.json")

            When("redis에서 값을 가져와") {
                every { RedisUtil.getData(redisCacheKey) } returns expectedDto

                Then("응답 DTO를 반환해야 한다.") {
                    val result = basicRealService.getCountriesHoliday(bodyDto, user.email)
                    result shouldBe (expectedDto toDto KISCountriesHolidayResponseDto::class.java)
                }
            }
        }
    }

    Context("getPriceDetail 함수의 경우") {
        val uri = "/quotations/basic/price-detail"

        Given("Redis 캐시에 값이 없을 때") {
            val bodyDto = readJsonFile(uri, "requestDto.json") toDto PriceDetailRequestParameterDto::class.java
            val redisCacheKey = "getPriceDetail" tag bodyDto.SYMB
            val requestDto = bodyDto.asDomain()
            val headerDto = createHeader(user, TradeId.getTradeIdByEnum(TradeId.PRICE_DETAIL), true)
            val expectedDto = readJsonFile(uri, "responseDto.json") toDto KISPriceDetailResponseDto::class.java

            When("KIS API로 요청을 보내면") {
                every { RedisUtil.getData(redisCacheKey) } returns null
                every { kisBasicRealClient.getPriceDetail(headerDto, requestDto) } returns Mono.just(expectedDto)
                every { RedisUtil.setData(redisCacheKey, expectedDto, any()) } returns Unit

                Then("응답 DTO를 redis에 저장하고, 반환해야 한다.") {
                    val result = basicRealService.getPriceDetail(bodyDto, user.email)
                    result shouldBe expectedDto
                }
            }
        }

        Given("Redis 캐시에 값이 있을 때") {
            val bodyDto = readJsonFile(uri, "requestDto.json") toDto PriceDetailRequestParameterDto::class.java
            val redisCacheKey = "getPriceDetail" tag bodyDto.SYMB
            val expectedDto = readJsonFile(uri, "responseDto.json")

            When("redis에서 값을 가져와") {
                every { RedisUtil.getData(redisCacheKey) } returns expectedDto

                Then("응답 DTO를 반환해야 한다.") {
                    val result = basicRealService.getPriceDetail(bodyDto, user.email)
                    result shouldBe (expectedDto toDto KISPriceDetailResponseDto::class.java)
                }
            }
        }
    }

    Context("getInquireTimeItemChartPrice 함수의 경우") {
        val uri = "/quotations/basic/inquire-time-itemchartprice"

        Given("Redis 캐시에 값이 없을 때") {
            val bodyDto = readJsonFile(uri, "requestDto.json") toDto InquireTimeItemChartPriceRequestParameterDto::class.java
            val redisCacheKey = "getInquireTimeItemChartPrice" tag
                    bodyDto.SYMB tag
                    bodyDto.NEXT tag
                    bodyDto.KEYB
            val requestDto = bodyDto.asDomain()
            val headerDto = createHeader(user, TradeId.getTradeIdByEnum(TradeId.INQUIRE_TIME_ITEM_CHART_PRICE), true)
            val expectedDto = readJsonFile(uri, "responseDto.json") toDto KISInquireTimeItemChartPriceResponseDto::class.java

            When("KIS API로 요청을 보내면") {
                every { RedisUtil.getData(redisCacheKey) } returns null
                every { kisBasicRealClient.getInquireTimeItemChartPrice(headerDto, requestDto) } returns Mono.just(expectedDto)
                every { RedisUtil.setData(redisCacheKey, expectedDto, any()) } returns Unit

                Then("응답 DTO를 redis에 저장하고, 반환해야 한다.") {
                    val result = basicRealService.getInquireTimeItemChartPrice(bodyDto, user.email)
                    result shouldBe expectedDto
                }
            }
        }

        Given("Redis 캐시에 값이 있을 때") {
            val bodyDto = readJsonFile(uri, "requestDto.json") toDto InquireTimeItemChartPriceRequestParameterDto::class.java
            val redisCacheKey = "getInquireTimeItemChartPrice" tag
                    bodyDto.SYMB tag
                    bodyDto.NEXT tag
                    bodyDto.KEYB
            val expectedDto = readJsonFile(uri, "responseDto.json")

            When("redis에서 값을 가져와") {
                every { RedisUtil.getData(redisCacheKey) } returns expectedDto

                Then("응답 DTO를 반환해야 한다.") {
                    val result = basicRealService.getInquireTimeItemChartPrice(bodyDto, user.email)
                    result shouldBe (expectedDto toDto KISInquireTimeItemChartPriceResponseDto::class.java)
                }
            }
        }
    }

    Context("getInquireTimeIndexChartPrice 함수의 경우") {
        val uri = "/quotations/basic/inquire-time-indexchartprice"

        Given("Redis 캐시에 값이 없을 때") {
            val bodyDto = readJsonFile(uri, "requestDto.json") toDto InquireTimeIndexChartPriceRequestParameterDto::class.java
            val redisCacheKey = "getInquireTimeIndexChartPrice" tag
                    bodyDto.fidInputIscd tag
                    bodyDto.fidCondMrktDivCode tag
                    bodyDto.fidPwDataIncuYn tag
                    bodyDto.fidHourClsCode
            val requestDto = bodyDto.asDomain()
            val headerDto = createHeader(user, TradeId.getTradeIdByEnum(TradeId.INQUIRE_TIME_INDEX_CHART_PRICE), true)
            val expectedDto = readJsonFile(uri, "responseDto.json") toDto KISInquireTimeIndexChartPriceResponseDto::class.java

            When("KIS API로 요청을 보내면") {
                every { RedisUtil.getData(redisCacheKey) } returns null
                every { kisBasicRealClient.getInquireTimeIndexChartPrice(headerDto, requestDto) } returns Mono.just(expectedDto)
                every { RedisUtil.setData(redisCacheKey, expectedDto, any()) } returns Unit

                Then("응답 DTO를 redis에 저장하고, 반환해야 한다.") {
                    val result = basicRealService.getInquireTimeIndexChartPrice(bodyDto, user.email)
                    result shouldBe expectedDto
                }
            }
        }

        Given("Redis 캐시에 값이 있을 때") {
            val bodyDto = readJsonFile(uri, "requestDto.json") toDto InquireTimeIndexChartPriceRequestParameterDto::class.java
            val redisCacheKey = "getInquireTimeIndexChartPrice" tag
                    bodyDto.fidInputIscd tag
                    bodyDto.fidCondMrktDivCode tag
                    bodyDto.fidPwDataIncuYn tag
                    bodyDto.fidHourClsCode
            val expectedDto = readJsonFile(uri, "responseDto.json")

            When("redis에서 값을 가져와") {
                every { RedisUtil.getData(redisCacheKey) } returns expectedDto

                Then("응답 DTO를 반환해야 한다.") {
                    val result = basicRealService.getInquireTimeIndexChartPrice(bodyDto, user.email)
                    result shouldBe (expectedDto toDto KISInquireTimeIndexChartPriceResponseDto::class.java)
                }
            }
        }
    }

    Context("getSearchInfo 함수의 경우") {
        val uri = "/quotations/basic/search-info"

        Given("Redis 캐시에 값이 없을 때") {
            val bodyDto = readJsonFile(uri, "requestDto.json") toDto SearchInfoRequestParameterDto::class.java
            val redisCacheKey = "getSearchInfo" tag bodyDto.pdno
            val requestDto = bodyDto.asDomain()
            val headerDto = createHeader(user, TradeId.getTradeIdByEnum(TradeId.SEARCH_INFO), true)
            val expectedDto = readJsonFile(uri, "responseDto.json") toDto KISSearchInfoResponseDto::class.java

            When("KIS API로 요청을 보내면") {
                every { RedisUtil.getData(redisCacheKey) } returns null
                every { kisBasicRealClient.getSearchInfo(headerDto, requestDto) } returns Mono.just(expectedDto)
                every { RedisUtil.setData(redisCacheKey, expectedDto, any()) } returns Unit

                Then("응답 DTO를 redis에 저장하고, 반환해야 한다.") {
                    val result = basicRealService.getSearchInfo(bodyDto, user.email)
                    result shouldBe expectedDto
                }
            }
        }

        Given("Redis 캐시에 값이 있을 때") {
            val bodyDto = readJsonFile(uri, "requestDto.json") toDto SearchInfoRequestParameterDto::class.java
            val redisCacheKey = "getSearchInfo" tag bodyDto.pdno
            val expectedDto = readJsonFile(uri, "responseDto.json")

            When("redis에서 값을 가져와") {
                every { RedisUtil.getData(redisCacheKey) } returns expectedDto

                Then("응답 DTO를 반환해야 한다.") {
                    val result = basicRealService.getSearchInfo(bodyDto, user.email)
                    result shouldBe (expectedDto toDto KISSearchInfoResponseDto::class.java)
                }
            }
        }
    }

    Context("getInquireAskingPrice 함수의 경우") {
        val uri = "/quotations/basic/inquire-asking-price"

        Given("Redis 캐시에 값이 없을 때") {
            val bodyDto = readJsonFile(uri, "requestDto.json") toDto InquireAskingPriceRequestParameterDto::class.java
            val redisCacheKey = "getInquireAskingPrice" tag bodyDto.SYMB
            val requestDto = bodyDto.asDomain()
            val headerDto = createHeader(user, TradeId.getTradeIdByEnum(TradeId.INQUIRE_ASKING_PRICE))
            val expectedDto = readJsonFile(uri, "responseDto.json") toDto KISInquireAskingPriceResponseDto::class.java

            When("KIS API로 요청을 보내면") {
                every { RedisUtil.getData(redisCacheKey) } returns null
                every { kisBasicRealClient.getInquireAskingPrice(headerDto, requestDto) } returns Mono.just(expectedDto)
                every { RedisUtil.setData(redisCacheKey, expectedDto, any()) } returns Unit

                Then("응답 DTO를 redis에 저장하고, 반환해야 한다.") {
                    val result = basicRealService.getInquireAskingPrice(bodyDto, user.email)
                    result shouldBe expectedDto
                }
            }
        }

        Given("Redis 캐시에 값이 있을 때") {
            val bodyDto = readJsonFile(uri, "requestDto.json") toDto InquireAskingPriceRequestParameterDto::class.java
            val redisCacheKey = "getInquireAskingPrice" tag bodyDto.SYMB
            val expectedDto = readJsonFile(uri, "responseDto.json")

            When("redis에서 값을 가져와") {
                every { RedisUtil.getData(redisCacheKey) } returns expectedDto

                Then("응답 DTO를 반환해야 한다.") {
                    val result = basicRealService.getInquireAskingPrice(bodyDto, user.email)
                    result shouldBe (expectedDto toDto KISInquireAskingPriceResponseDto::class.java)
                }
            }
        }
    }


})