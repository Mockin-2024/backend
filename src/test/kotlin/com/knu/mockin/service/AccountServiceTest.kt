package com.knu.mockin.service

import com.knu.mockin.dsl.RestDocsUtils
import com.knu.mockin.dsl.RestDocsUtils.readJsonFile
import com.knu.mockin.dsl.toDto
import com.knu.mockin.kisclient.KISOauth2Client
import com.knu.mockin.kisclient.KISOauth2RealClient
import com.knu.mockin.model.dto.request.account.KeyPairRequestDto
import com.knu.mockin.model.dto.request.account.UserAccountNumberRequestDto
import com.knu.mockin.model.dto.response.AccessTokenAPIResponseDto
import com.knu.mockin.model.dto.response.ApprovalKeyResponseDto
import com.knu.mockin.model.dto.response.SimpleMessageResponseDto
import com.knu.mockin.model.entity.MockKey
import com.knu.mockin.model.entity.RealKey
import com.knu.mockin.model.entity.User
import com.knu.mockin.repository.MockKeyRepository
import com.knu.mockin.repository.RealKeyRepository
import com.knu.mockin.repository.UserRepository
import com.knu.mockin.util.RedisUtil
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.data.redis.core.RedisTemplate
import reactor.core.publisher.Mono


class AccountServiceTest(
    private val mockKeyRepository: MockKeyRepository = mockk<MockKeyRepository>(),
    private val userRepository: UserRepository = mockk<UserRepository>(),
    private val realKeyRepository: RealKeyRepository = mockk<RealKeyRepository>(),
    private val kisOauth2Client: KISOauth2Client = mockk(),
    private val kisOauth2RealClient: KISOauth2RealClient = mockk()
): BehaviorSpec({
    val accountService = AccountService(
        mockKeyRepository = mockKeyRepository,
        realKeyRepository = realKeyRepository,
        userRepository = userRepository,
        kisOauth2Client = kisOauth2Client,
        kisOauth2RealClient = kisOauth2RealClient,
        )
    val redisTemplate = mockk<RedisTemplate<String, String>>()
    RedisUtil.init(redisTemplate)
    val user = readJsonFile("setting", "user.json") toDto User::class.java
    val token = readJsonFile("/oauth2/mock-token", "responseDto.json") toDto AccessTokenAPIResponseDto::class.java

    beforeTest {
        every { userRepository.findByEmail(user.email) } returns Mono.just(user)
    }

    val baseUri = "/account"
    Context("patchUser 함수의 경우"){
        val uri = "$baseUri/userPatch"

        Given("적절한 dto가 주어질 때"){
            val bodyDto = readJsonFile(uri, "requestDto.json") toDto UserAccountNumberRequestDto::class.java
            val expectedDto = readJsonFile(uri, "responseDto.json") toDto SimpleMessageResponseDto::class.java

            When("사용자 계좌번호를 정상적으로 등록한 후"){
                every { userRepository.updateByEmail(user.email, bodyDto.accountNumber) } returns Mono.just(user)

                Then("응답 DTO를 정상적으로 받아야 한다."){
                    val result = accountService.patchUser(bodyDto, user.email)
                    result shouldBe expectedDto
                }
            }
        }
    }

    Context("postMockKeyPair 함수의 경우"){
        val uri = "$baseUri/mock-key"

        Given("적절한 dto가 주어질 때"){
            val bodyDto = readJsonFile(uri, "requestDto.json") toDto KeyPairRequestDto::class.java
            val expectedDto = readJsonFile(uri, "responseDto.json") toDto SimpleMessageResponseDto::class.java
            val mockKey = MockKey(user.email, bodyDto.appKey, bodyDto.appSecret)

            When("모의투자 키의 유효성을 검증한 후 적절하면"){
                every { kisOauth2Client.postTokenP(any()) } returns Mono.just(token)
                every { mockKeyRepository.save(mockKey) } returns Mono.just(mockKey)

                Then("db에 저장하고 응답 DTO를 정상적으로 받아야 한다."){
                    val result = accountService.postMockKeyPair(bodyDto, user.email)
                    result shouldBe expectedDto
                }
            }
        }
    }

    Context("postRealKeyPair 함수의 경우"){
        val uri = "$baseUri/real-key"

        Given("적절한 dto가 주어질 때"){
            val bodyDto = readJsonFile(uri, "requestDto.json") toDto KeyPairRequestDto::class.java
            val expectedDto = readJsonFile(uri, "responseDto.json") toDto SimpleMessageResponseDto::class.java
            val realKey = RealKey(user.email, bodyDto.appKey, bodyDto.appSecret)

            When("실전투자 키의 유효성을 검증한 후 적절하면"){
                every { kisOauth2RealClient.postTokenP(any()) } returns Mono.just(token)
                every { realKeyRepository.save(realKey) } returns Mono.just(realKey)

                Then("db에 저장하고 응답 DTO를 정상적으로 받아야 한다."){
                    val result = accountService.postRealKeyPair(bodyDto, user.email)
                    result shouldBe expectedDto
                }
            }
        }
    }
})