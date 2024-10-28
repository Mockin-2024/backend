package com.knu.mockin.service

import com.knu.mockin.dsl.RestDocsUtils
import com.knu.mockin.dsl.toDto
import com.knu.mockin.kisclient.KISOauth2Client
import com.knu.mockin.kisclient.KISOauth2RealClient
import com.knu.mockin.model.dto.kisrequest.oauth.KISApprovalRequestDto
import com.knu.mockin.model.dto.kisrequest.oauth.KISTokenRequestDto
import com.knu.mockin.model.dto.kisresponse.trading.KISOrderReverseResponseDto
import com.knu.mockin.model.dto.request.account.UserAccountNumberRequestDto
import com.knu.mockin.model.dto.request.trading.OrderReverseRequestBodyDto
import com.knu.mockin.model.dto.request.trading.asDomain
import com.knu.mockin.model.dto.response.AccessTokenAPIResponseDto
import com.knu.mockin.model.dto.response.ApprovalKeyResponseDto
import com.knu.mockin.model.dto.response.SimpleMessageResponseDto
import com.knu.mockin.model.entity.MockKey
import com.knu.mockin.model.entity.User
import com.knu.mockin.model.entity.UserWithKeyPair
import com.knu.mockin.model.enum.Constant.MOCK
import com.knu.mockin.repository.MockKeyRepository
import com.knu.mockin.repository.RealKeyRepository
import com.knu.mockin.repository.UserRepository
import com.knu.mockin.service.util.ServiceUtil
import com.knu.mockin.util.RedisUtil
import com.knu.mockin.util.tag
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.data.redis.core.RedisTemplate
import reactor.core.publisher.Mono


class AccountServiceTest(
    private val kisOauth2Client: KISOauth2Client = mockk<KISOauth2Client>(),
    private val kisOauth2RealClient: KISOauth2RealClient = mockk<KISOauth2RealClient>(),
    private val mockKeyRepository: MockKeyRepository = mockk<MockKeyRepository>(),
    private val userRepository: UserRepository = mockk<UserRepository>(),
    private val realKeyRepository: RealKeyRepository = mockk<RealKeyRepository>()
): BehaviorSpec({
    val accountService = AccountService(
        kisOauth2Client = kisOauth2Client,
        kisOauth2RealClient = kisOauth2RealClient,
        mockKeyRepository = mockKeyRepository,
        realKeyRepository = realKeyRepository,
        userRepository = userRepository
        )
    val redisTemplate = mockk<RedisTemplate<String, String>>()
    RedisUtil.init(redisTemplate)
    val user = RestDocsUtils.readJsonFile("setting", "user.json") toDto User::class.java

    beforeTest {
        every { userRepository.findByEmail(user.email) } returns Mono.just(user)
    }

    val baseUri = "/account"
    Context("patchUser 함수의 경우"){
        val uri = "$baseUri/userPatch"

        Given("적절한 dto가 주어질 때"){
            val bodyDto = RestDocsUtils.readJsonFile(uri, "requestDto.json") toDto UserAccountNumberRequestDto::class.java
            val expectedDto = RestDocsUtils.readJsonFile(uri, "responseDto.json") toDto SimpleMessageResponseDto::class.java

            When("사용자 계좌번호를 정상적으로 등록한 후"){
                every { userRepository.updateByEmail(user.email, bodyDto.accountNumber) } returns Mono.just(user)

                Then("응답 DTO를 정상적으로 받아야 한다."){
                    val result = accountService.patchUser(bodyDto, user.email)
                    result shouldBe expectedDto
                }
            }
        }
    }
})