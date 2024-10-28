package com.knu.mockin.service

import com.knu.mockin.dsl.RestDocsUtils
import com.knu.mockin.dsl.toDto
import com.knu.mockin.kisclient.KISOauth2Client
import com.knu.mockin.kisclient.KISOauth2RealClient
import com.knu.mockin.model.dto.kisrequest.oauth.KISApprovalRequestDto
import com.knu.mockin.model.dto.kisrequest.oauth.KISTokenRequestDto
import com.knu.mockin.model.dto.response.AccessTokenAPIResponseDto
import com.knu.mockin.model.dto.response.ApprovalKeyResponseDto
import com.knu.mockin.model.entity.UserWithKeyPair
import com.knu.mockin.model.enum.Constant.MOCK
import com.knu.mockin.model.enum.Constant.REAL
import com.knu.mockin.repository.UserRepository
import com.knu.mockin.util.RedisUtil
import com.knu.mockin.util.tag
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.data.redis.core.RedisTemplate
import reactor.core.publisher.Mono

class Oauth2ServiceTest(
    private val kisOauth2Client: KISOauth2Client = mockk<KISOauth2Client>(),
    private val kisOauth2RealClient: KISOauth2RealClient = mockk<KISOauth2RealClient>(),
    private val userRepository: UserRepository = mockk<UserRepository>(),
): BehaviorSpec ({
    val oauth2Service = Oauth2Service(
        kisOauth2Client = kisOauth2Client,
        kisOauth2RealClient = kisOauth2RealClient,
        userRepository = userRepository
    )
    val user = RestDocsUtils.readJsonFile("setting", "userWithKeyPair.json") toDto UserWithKeyPair::class.java
    val redisTemplate = mockk<RedisTemplate<String, String>>()
    RedisUtil.init(redisTemplate)

    beforeTest {
        every { userRepository.findByEmailWithMockKey(user.email) } returns Mono.just(user)
        every { userRepository.findByEmailWithRealKey(user.email) } returns Mono.just(user)
    }

    val baseUri = "/oauth2"

    Context("getMockApprovalKey 함수의 경우"){
        val uri = "$baseUri/mock-approval-key"

        Given("적절한 dto가 주어질 때"){
            val requestDto = KISApprovalRequestDto("client_credentials", user.appKey, user.appSecret)
            val expectedDto = RestDocsUtils.readJsonFile(uri, "responseDto.json") toDto ApprovalKeyResponseDto::class.java

            When("KIS API로 요청을 보내면"){
                every { kisOauth2Client.postApproval(requestDto) } returns Mono.just(expectedDto)

                Then("응답 DTO를 정상적으로 받아야 한다."){
                    val result = oauth2Service.getMockApprovalKey(user.email)
                    result shouldBe expectedDto
                }
            }
        }
    }

    Context("getRealApprovalKey 함수의 경우"){
        val uri = "$baseUri/real-approval-key"

        Given("적절한 dto가 주어질 때"){
            val requestDto = KISApprovalRequestDto("client_credentials", user.appKey, user.appSecret)
            val expectedDto = RestDocsUtils.readJsonFile(uri, "responseDto.json") toDto ApprovalKeyResponseDto::class.java

            When("KIS API로 요청을 보내면"){
                every { kisOauth2RealClient.postApproval(requestDto) } returns Mono.just(expectedDto)

                Then("응답 DTO를 정상적으로 받아야 한다."){
                    val result = oauth2Service.getRealApprovalKey(user.email)
                    result shouldBe expectedDto
                }
            }
        }
    }

    Context("getMockAccessToken 함수의 경우"){
        val uri = "$baseUri/mock-token"

        Given("적절한 dto가 주어질 때"){
            val requestDto = KISTokenRequestDto("client_credentials", user.appKey, user.appSecret)
            val expectedDto = RestDocsUtils.readJsonFile(uri, "responseDto.json") toDto AccessTokenAPIResponseDto::class.java

            When("KIS API로 요청을 보내면"){
                every { kisOauth2Client.postTokenP(requestDto) } returns Mono.just(expectedDto)
                every {RedisUtil.saveToken(user.email tag MOCK, expectedDto.accessToken)} returns Unit

                Then("토큰을 redis에 저장하고, 응답 DTO를 정상적으로 받아야 한다."){
                    val result = oauth2Service.getMockAccessToken(user.email)
                    result shouldBe expectedDto
                }
            }
        }
    }

    Context("getRealAccessToken 함수의 경우"){
        val uri = "$baseUri/real-token"

        Given("적절한 dto가 주어질 때"){
            val requestDto = KISTokenRequestDto("client_credentials", user.appKey, user.appSecret)
            val expectedDto = RestDocsUtils.readJsonFile(uri, "responseDto.json") toDto AccessTokenAPIResponseDto::class.java

            When("KIS API로 요청을 보내면"){
                every { kisOauth2RealClient.postTokenP(requestDto) } returns Mono.just(expectedDto)
                every {RedisUtil.saveToken(user.email tag REAL, expectedDto.accessToken)} returns Unit

                Then("토큰을 redis에 저장하고, 응답 DTO를 정상적으로 받아야 한다."){
                    val result = oauth2Service.getRealAccessToken(user.email)
                    result shouldBe expectedDto
                }
            }
        }
    }
})