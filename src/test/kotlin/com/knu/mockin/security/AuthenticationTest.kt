package com.knu.mockin.security

import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import org.reactivestreams.Publisher
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

class AuthenticationTest: BehaviorSpec({
    val jwtUtil = mockk<JwtUtil>()
    val customUserDetailsService = mockk<CustomUserDetailsService>()
    val jwtAuthenticationManager = JwtAuthenticationManager(jwtUtil, customUserDetailsService)

    Context("JwtAuthenticationManager 테스트"){
        Given("authenticate 테스트"){
            val invalidToken = BearerToken("invalid_token")
            val username = "test@naver.com"

            val userDetail =org.springframework.security.core.userdetails.User.withUsername("other name")
                .password("1111")
                .roles("USER")
                .build()

            When("JWT가 inValid 하면"){
                every { jwtUtil.getUsername(invalidToken) } returns username
                every { customUserDetailsService.findByUsername(username) } returns Mono.just(userDetail)
                every { jwtUtil.isValid(invalidToken, userDetail) } returns false

                Then("에러를 반환한다."){
                    val result = jwtAuthenticationManager.authenticate(invalidToken)
                    StepVerifier.create(result as Publisher<out Any>)
                        .expectErrorMatches { it is InvalidBearerToken && it.message == "적절한 토큰이 없어 접근이 거부당했습니다." }
                        .verify()
                }
            }
        }
    }
})