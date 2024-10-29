package com.knu.mockin.security.handler

import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.core.io.buffer.DefaultDataBufferFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.security.access.AccessDeniedException
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

class JwtAccessDeniedHandlerTest: BehaviorSpec({
    Context("JwtAccessDeniedHandler 테스트"){
        val jwtAccessDeniedHandler = JwtAccessDeniedHandler()

        Given("필터에서 토큰 검증이 실패하면"){
            val exchange = mockk<ServerWebExchange>(relaxed = true)
            val response = mockk<ServerHttpResponse>(relaxed = true)
            val exception = AccessDeniedException("Access denied")

            When("적절한 에러 메세지를 생성하여"){
                every { exchange.response } returns response
                every { response.bufferFactory() } returns DefaultDataBufferFactory.sharedInstance
                every { response.writeWith(any()) } returns Mono.empty()

                Then("메세지를 반환한다."){
                    val result = jwtAccessDeniedHandler.handle(exchange, exception)

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