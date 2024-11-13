package com.knu.mockin.kisclient

import com.knu.mockin.config.ConstantConfig
import com.knu.mockin.model.dto.kisrequest.oauth.KISApprovalRequestDto
import com.knu.mockin.model.dto.kisrequest.oauth.KISTokenRequestDto
import com.knu.mockin.model.enum.Constant
import com.knu.mockin.repository.UserRepository
import com.knu.mockin.util.RedisUtil
import com.knu.mockin.util.tag
import io.kotest.core.spec.style.FunSpec
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class KISOauth2ClientTest(
    val kisOauth2Client: KISOauth2Client,
    val userRepository: UserRepository,
    private val constantConfig: ConstantConfig
): FunSpec({
    xcontext("KISOauth2Client 테스트"){
        val user = userRepository.findByEmailWithMockKey(constantConfig.user.email).block()

        test("postTokenP 테스트 후 redis에 저장"){
            val requestDto = KISTokenRequestDto(
                grantType = "client_credentials",
                appKey = user!!.appKey,
                appSecret = user.appSecret)
            val dto = kisOauth2Client.postTokenP(requestDto).awaitSingle()

            RedisUtil.saveToken(user.email tag Constant.MOCK, dto.accessToken)
        }

        test("postApproval 테스트"){
            val requestDto = KISApprovalRequestDto(
                grantType = "client_credentials",
                appKey = user!!.appKey,
                secretKey = user.appSecret)
            val dto = kisOauth2Client.postApproval(requestDto).awaitSingle()
            println(dto)

        }
    }
})