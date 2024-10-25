package com.knu.mockin.service.accountservice

import com.knu.mockin.kisclient.KISOauth2Client
import com.knu.mockin.kisclient.KISOauth2RealClient
import com.knu.mockin.model.dto.kisrequest.oauth.KISApprovalRequestDto
import com.knu.mockin.model.dto.kisrequest.oauth.KISTokenRequestDto
import com.knu.mockin.model.dto.response.AccessTokenAPIResponseDto
import com.knu.mockin.model.dto.response.ApprovalKeyResponseDto
import com.knu.mockin.model.entity.MockKey
import com.knu.mockin.model.enum.Constant.MOCK
import com.knu.mockin.repository.MockKeyRepository
import com.knu.mockin.repository.RealKeyRepository
import com.knu.mockin.repository.UserRepository
import com.knu.mockin.service.AccountService
import com.knu.mockin.util.RedisUtil
import com.knu.mockin.util.tag
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.data.redis.core.RedisTemplate
import reactor.core.publisher.Mono


class AccountServiceTest: BehaviorSpec({
    val kisOauth2Client: KISOauth2Client = mockk<KISOauth2Client>()
    val kisOauth2RealClient: KISOauth2RealClient = mockk<KISOauth2RealClient>()
    val mockKeyRepository: MockKeyRepository = mockk()
    val userRepository: UserRepository = mockk()
    val realKeyRepository: RealKeyRepository = mockk()
    val accountService = AccountService(
        kisOauth2Client = kisOauth2Client,
        kisOauth2RealClient = kisOauth2RealClient,
        mockKeyRepository = mockKeyRepository,
        realKeyRepository = realKeyRepository,
        userRepository = userRepository
        )
    val redisTemplate = mockk<RedisTemplate<String, String>>()
    RedisUtil.init(redisTemplate)
    val email = "test@naver.com"

    val mockKey = MockKey("test", "test appKey", "test appSecret")
    beforeTest {
        every { mockKeyRepository.findById(email) } returns Mono.just(mockKey)
    }

    Given("get approval key test"){
        val requestDto = KISApprovalRequestDto(
            grantType = "client_credentials",
            appKey = mockKey.appKey,
            secretKey = mockKey.appSecret)
        val expectedDto = ApprovalKeyResponseDto("test")

        every { kisOauth2Client.postApproval(requestDto) } returns Mono.just(expectedDto)
        When("서비스 계층에 요청을 보내면:"){
            val result = accountService.getMockApprovalKey(email)

            Then("키가 반환된다"){
                result shouldBe expectedDto
            }
        }
    }

    Given("get access token test"){
        val requestDto = KISTokenRequestDto(
            grantType = "client_credentials",
            appKey = mockKey.appKey,
            appSecret = mockKey.appSecret)
        val expectedDto = AccessTokenAPIResponseDto(
            "test",
            "Bearer",
            7776000,
            "2024-10-31")

        every { kisOauth2Client.postTokenP(requestDto) } returns Mono.just(expectedDto)
        every { RedisUtil.saveToken(mockKey.email tag MOCK, expectedDto.accessToken) } returns Unit

        When("서비스 계층에 요청을 보내면:"){
            val result = accountService.getMockAccessToken(email)

            Then("토큰이 반환된다"){
                result shouldBe expectedDto
            }
        }
    }
})