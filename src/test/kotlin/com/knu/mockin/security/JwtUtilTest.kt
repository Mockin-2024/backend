package com.knu.mockin.security

import com.knu.mockin.model.enum.Constant.JWT
import com.knu.mockin.util.RedisUtil
import com.knu.mockin.util.tag
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.data.redis.core.RedisTemplate
import java.time.Duration

class JwtUtilTest :BehaviorSpec({
    val redisTemplate = mockk<RedisTemplate<String, String>>()
    RedisUtil.init(redisTemplate)
    val username = "test@naver.com"
    val password = "1111"

    beforeTest {
        every { RedisUtil.saveToken(username tag JWT, any(), Duration.ofDays(1) ) } returns Unit
    }

    Context("JwtUtil 테스트"){
        val jwtUtil = JwtUtil()
        val token = jwtUtil.generate(username)

        Given("generate 테스트") {

            When("메소드가 실행되면") {

                Then("토큰을 생성한 후 redis에 저장한다.") {
                    jwtUtil.generate(username)
                    verify {
                        RedisUtil.saveToken(username tag JWT, any(), Duration.ofDays(1) )
                    }
                }
            }
        }

        Given("getUsername 테스트"){

            When("메소드가 실행되면") {

                Then("토큰에 담긴 사용자 이름을 가져온다.") {
                    val result = jwtUtil.getUsername(token)
                    result shouldBe username
                }
            }
        }

        Given("isValid 테스트"){
            val userDetails =org.springframework.security.core.userdetails.User.withUsername(username)
                .password(password)
                .roles("USER")
                .build()

            When("토큰이 valid한 경우") {
                every { RedisUtil.getToken(username tag JWT) } returns token.value

                Then("true를 반환한다.") {
                    val result = jwtUtil.isValid(token, userDetails)
                    result shouldBe true
                }
            }

            val expiredToken = jwtUtil.generate(username, -10)

            When("토큰이 만료된 경우"){
                every { RedisUtil.getToken(username tag JWT) } returns expiredToken.value

                Then("false를 반환한다.") {
                    val result = jwtUtil.isValid(expiredToken, userDetails)
                    result shouldBe false
                }
            }

            val otherUser =org.springframework.security.core.userdetails.User.withUsername("other name")
                .password(password)
                .roles("USER")
                .build()
            When("사용자 이름이 일치하지 않는 경우"){
                every { RedisUtil.getToken(username tag JWT) } returns token.value

                Then("false를 반환한다.") {
                    val isInvalidUserResult = jwtUtil.isValid(token, otherUser)
                    isInvalidUserResult shouldBe false
                }
            }

            When("저장된 토큰과 값이 다른 경우"){
                every { RedisUtil.getToken(username tag JWT) } returns "other value"

                Then("false를 반환한다.") {
                    val isInvalidUserResult = jwtUtil.isValid(token, userDetails)
                    isInvalidUserResult shouldBe false
                }
            }

            When("사용자 정보가 없는 경우"){
                every { RedisUtil.getToken(username tag JWT) } returns token.value

                Then("false를 반환한다.") {
                    val isInvalidUserResult = jwtUtil.isValid(token, null)
                    isInvalidUserResult shouldBe false
                }
            }
        }
    }

})