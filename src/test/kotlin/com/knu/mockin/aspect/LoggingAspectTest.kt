package com.knu.mockin.aspect

import com.knu.mockin.model.dto.request.login.Jwt
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

    val joinPoint = mockk<ProceedingJoinPoint>(relaxed = true)
    val methodSignature = mockk<MethodSignature>(relaxed = true)

    Context("인증을 필요로 하는 모든 컨트롤러 메소드에 대해"){
        mockkStatic(ReactiveSecurityContextHolder::class)
        Given("실행 전후로"){
            val securityContext = mockk<SecurityContext>(relaxed = true)
            val authentication = mockk<Authentication>(relaxed = true)

            val response = SimpleMessageResponseDto("성공")

            When("필요한 데이터를 담은 로그를 남기고"){
                every { joinPoint.proceed() } returns Mono.just(response)
                every { joinPoint.signature } returns methodSignature
                every { methodSignature.declaringTypeName } returns "com.knu.mockin.HealthCheckController"
                every { methodSignature.name } returns "healthCheck"

                every { ReactiveSecurityContextHolder.getContext() } returns Mono.just(securityContext)

                every { securityContext.authentication } returns authentication
                every { authentication.name } returns "test@knu.ac.kr"

                Then("결과를 반환한다"){
                    val result = loggingAspect.logControllerWithSecurity(joinPoint)

                    StepVerifier.create(result as Publisher<out Any>)
                        .expectNext(response)
                        .verifyComplete()
                }
            }
        }
        unmockkStatic(ReactiveSecurityContextHolder::class)
    }
    Context("Login Controller의 메소드에 대해"){
        Given("실행 전후로"){
            val response = Jwt("access token")

            When("적절한 로그를 남기고"){
                every { joinPoint.proceed() } returns Mono.just(response)
                every { joinPoint.signature } returns methodSignature
                every { methodSignature.declaringTypeName } returns "com.knu.mockin.LoginController"
                every { methodSignature.name } returns "login"

                Then("결과를 반환한다."){
                    val result = loggingAspect.logLoginController(joinPoint)
                    StepVerifier.create(result as Publisher<out Any>)
                    .expectNext(response)
                    .verifyComplete()
                }
            }
        }
    }
})