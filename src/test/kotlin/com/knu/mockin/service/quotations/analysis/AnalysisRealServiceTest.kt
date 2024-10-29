package com.knu.mockin.service.quotations.analysis

import com.knu.mockin.dsl.RestDocsUtils
import com.knu.mockin.dsl.toDto
import com.knu.mockin.kisclient.quotations.analysis.KISAnalysisRealClient
import com.knu.mockin.model.dto.kisresponse.quotations.analysis.KISNewsTitleResponseDto
import com.knu.mockin.model.dto.kisresponse.quotations.basic.real.KISCountriesHolidayResponseDto
import com.knu.mockin.model.dto.request.quotations.analysis.real.NewsTitleRequestParameterDto
import com.knu.mockin.model.dto.request.quotations.analysis.real.asDomain
import com.knu.mockin.model.dto.request.quotations.basic.real.CountriesHolidayRequestParameterDto
import com.knu.mockin.model.dto.request.quotations.basic.real.asDomain
import com.knu.mockin.model.entity.UserWithKeyPair
import com.knu.mockin.model.enum.TradeId
import com.knu.mockin.repository.UserRepository
import com.knu.mockin.service.quotations.basic.real.BasicRealService
import com.knu.mockin.service.util.ServiceUtil
import com.knu.mockin.util.RedisUtil
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.data.redis.core.RedisTemplate
import reactor.core.publisher.Mono

class AnalysisRealServiceTest(
    private val kisAnalysisRealClient: KISAnalysisRealClient = mockk<KISAnalysisRealClient>(),
    private val userRepository: UserRepository = mockk<UserRepository>()

): BehaviorSpec({
    val analysisRealService = AnalysisRealService(kisAnalysisRealClient, userRepository)
    val user = RestDocsUtils.readJsonFile("setting", "userWithKeyPair.json") toDto UserWithKeyPair::class.java
    val redisTemplate = mockk<RedisTemplate<String, String>>()
    RedisUtil.init(redisTemplate)

    beforeTest{
        every { userRepository.findByEmailWithRealKey(any()) } returns Mono.just(user)
        every { RedisUtil.getToken(any()) } returns "token"
    }

    Context("getNewsTitle 함수의 경우") {
        val uri = "/quotations/analysis/news-title"

        Given("적절한 dto가 주어질 때") {
            val bodyDto = RestDocsUtils.readJsonFile(uri, "requestDto.json") toDto NewsTitleRequestParameterDto::class.java
            val requestDto = bodyDto.asDomain()
            val headerDto = ServiceUtil.createHeader(user, TradeId.getTradeIdByEnum(TradeId.NEWS_TITLE), true)
            val expectedDto = RestDocsUtils.readJsonFile(uri, "responseDto.json") toDto KISNewsTitleResponseDto::class.java

            When("KIS API로 요청을 보내면") {
                every { kisAnalysisRealClient.getNewsTitle(headerDto, requestDto) } returns Mono.just(expectedDto)

                Then("응답 DTO를 정상적으로 받아야 한다.") {
                    val result = analysisRealService.getNewsTitle(bodyDto, user.email)
                    result shouldBe expectedDto
                }
            }
        }
    }
})