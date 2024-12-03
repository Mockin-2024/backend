package com.knu.mockin.performance

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.knu.mockin.dsl.RestDocsUtils.readJsonFile
import com.knu.mockin.kisclient.KISTradingClient
import com.knu.mockin.model.dto.kisresponse.trading.KISOrderResponseDto
import com.knu.mockin.model.dto.request.trading.OrderRequestBodyDto
import com.knu.mockin.model.dto.request.trading.asDomain
import com.knu.mockin.model.entity.UserWithKeyPair
import com.knu.mockin.repository.UserRepository
import com.knu.mockin.service.TradingService
import com.knu.mockin.service.util.ServiceUtil.createHeader
import com.knu.mockin.util.ExtensionUtil.toDto
import com.knu.mockin.util.RedisUtil
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.RedisTemplate
import reactor.core.publisher.Mono

@SpringBootTest
class RedisUtilTest(
    var redisTemplate: RedisTemplate<String, String>,
    @MockkBean
    private val kisTradingClient: KISTradingClient = mockk<KISTradingClient>(),
    @MockkBean
    private val userRepository: UserRepository = mockk<UserRepository>()
): BehaviorSpec({
    val user = readJsonFile("setting", "userWithKeyPair.json") toDto UserWithKeyPair::class.java
    val pattern = "test@naver.com-*"

    beforeTest {
        RedisUtil.init(redisTemplate)
        storeDataFromJson("setting/redis", "input.json", 60L)
        every { userRepository.findByEmailWithMockKey(user.email) } returns Mono.just(user)
    }

    afterTest {
        RedisUtil.deleteByPattern(pattern)
    }

    xContext("Redis에서 삭제 로직 실행 시 시간 측정"){
        Given("먼저 시작 시간을 측정하고"){
            val startTime = System.currentTimeMillis()

            When("Redis에서 삭제 로직을 실행한 후"){
                RedisUtil.deleteByPattern(pattern)

                Then("종료 시간을 측정하고, 출력한다."){
                    val endTime = System.currentTimeMillis()
                    val duration = endTime - startTime

                    println("삭제 시간: ${duration}ms")
                }
            }
        }
    }

    val baseUri = "/trading"
    xContext("postOrder 함수의 경우 Redis를 실제로 활용할 때"){
        val tradingService = TradingService(kisTradingClient, userRepository)
        val uri = "$baseUri/order"

        Given("요청이 들어오면"){
            val startTime = System.currentTimeMillis()

            val bodyDto = readJsonFile(uri, "requestDto.json") toDto OrderRequestBodyDto::class.java
            val requestDto = bodyDto.asDomain(user.accountNumber)
            val headerDto = createHeader(user, bodyDto.transactionId)
            val expectedDto = readJsonFile(uri, "responseDto.json") toDto KISOrderResponseDto::class.java

            When("KIS API로 요청을 보낸 후"){
                every { kisTradingClient.postOrder(headerDto, requestDto) } returns Mono.just(expectedDto)

                Then("redis 캐시를 비우고, 응답 DTO를 정상적으로 받아야 한다."){
                    val result = tradingService.postOrder(bodyDto, user.email)
                    val endTime = System.currentTimeMillis()
                    val duration = endTime - startTime

                    println("삭제 시간: ${duration}ms")
                    result shouldBe expectedDto
                }
            }
        }
    }
})

data class KeyValue(val key: String, val value: String)

fun storeDataFromJson(dirPath: String, fileName: String, expiration: Long) {
    val jsonString = readJsonFile(dirPath, fileName)

    val mapper = jacksonObjectMapper()
    val keyValueList: List<KeyValue> = mapper.readValue(jsonString, object : TypeReference<List<KeyValue>>() {})

    for (keyValue in keyValueList) {
        RedisUtil.setData(keyValue.key, keyValue.value, expiration)
    }
}