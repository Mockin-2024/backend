package com.knu.mockin.aspect

import com.knu.mockin.model.dto.response.SimpleMessageResponseDto
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.*
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.reflect.MethodSignature
import org.reactivestreams.Publisher
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.core.context.SecurityContext
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

class LoggingAspectTest :BehaviorSpec({
    val loggingAspect = LoggingAspect()

    val securityContext = mockk<SecurityContext>(relaxed = true)
    val authentication = mockk<Authentication>(relaxed = true)

    beforeTest{
        mockkStatic(ReactiveSecurityContextHolder::class)
        every { ReactiveSecurityContextHolder.getContext() } returns Mono.just(securityContext)

        every { securityContext.authentication } returns authentication
        every { authentication.name } returns "test@knu.ac.kr"
    }

    afterTest { unmockkStatic(ReactiveSecurityContextHolder::class) }

    Context("컨트롤러의 모든 메소드에 대해"){
        Given("실행 전후로"){
            val joinPoint = mockk<ProceedingJoinPoint>(relaxed = true)
            val methodSignature = mockk<MethodSignature>(relaxed = true)
            val response = SimpleMessageResponseDto("성공")

            When("필요한 데이터를 담은 로그를 남기고"){
                every { joinPoint.proceed() } returns Mono.just(response)
                every { joinPoint.signature } returns methodSignature
                every { methodSignature.declaringTypeName } returns "com.knu.mockin.HealthCheckController"
                every { methodSignature.name } returns "healthCheck"

                Then("결과를 반환한다"){
                    val result = loggingAspect.logExecutionInController(joinPoint)

                    StepVerifier.create(result as Publisher<out Any>)
                        .expectNext(response)
                        .verifyComplete()
                }
            }
        }

    }
})