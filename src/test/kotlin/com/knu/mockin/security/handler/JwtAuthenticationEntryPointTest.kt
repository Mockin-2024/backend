package com.knu.mockin.security.handler

import com.knu.mockin.security.InvalidBearerToken
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.core.io.buffer.DefaultDataBufferFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

class JwtAuthenticationEntryPointTest :BehaviorSpec({
    Context("JwtAuthenticationEntryPoint 테스트"){
        val entryPoint = JwtAuthenticationEntryPoint()

        Given("필터에서 JWT 인증에 실패하면"){
            val exchange = mockk<ServerWebExchange>(relaxed = true)
            val response = mockk<ServerHttpResponse>(relaxed = true)
            val exception = InvalidBearerToken("Authentication failed")

            When("해당하는 응답 메세지를 생성하여"){
                every { exchange.response } returns response
                every { response.bufferFactory() } returns DefaultDataBufferFactory.sharedInstance
                every { response.writeWith(any()) } returns Mono.empty()

                Then("메세지를 반환한다."){
                    val result = entryPoint.commence(exchange, exception)
                    StepVerifier.create(result)
                        .verifyComplete()

                    verify {
                        response.statusCode = HttpStatus.UNAUTHORIZED
                        response.headers.contentType = APPLICATION_JSON
                        response.writeWith(any())
                    }
                }
            }
        }
    }
})