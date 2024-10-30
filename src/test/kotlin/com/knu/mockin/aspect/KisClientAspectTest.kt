package com.knu.mockin.aspect

import com.knu.mockin.exception.CustomException
import com.knu.mockin.exception.ErrorCode
import com.knu.mockin.model.dto.kisresponse.trading.KISOrderReverseResponseDto
import com.knu.mockin.model.dto.response.ApprovalKeyResponseDto
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.reflect.MethodSignature
import org.reactivestreams.Publisher
import org.springframework.web.reactive.function.client.WebClientResponseException
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

class KisClientAspectTest: BehaviorSpec({
    val kisClientAspect = KisClientAspect()
    val joinPoint = mockk<ProceedingJoinPoint>()
    val methodSignature = mockk<MethodSignature>(relaxed = true)

    beforeTest{
        every { joinPoint.signature } returns methodSignature
        every { methodSignature.declaringTypeName } returns "com.knu.mockin.Oauth2Controller"
        every { methodSignature.name } returns "mock-approval-key"
        every { joinPoint.args } returns arrayOf(Unit)
    }

    Context("모든 kisclient 패키지 내에서 메소드 실행이 일어날 때"){
        Given("정상 응답, 응답 코드 null"){
            val response = ApprovalKeyResponseDto("key")

            When("KIS로부터 응답을 받으면"){
                every { joinPoint.proceed() } returns Mono.just(response)

                Then("그 결과를 반환한다.") {
                    val result = kisClientAspect.handleKISWebClientException(joinPoint)
                    StepVerifier.create(result as Publisher<out Any>)
                        .expectNext(response)
                        .verifyComplete()
                }
            }
        }

        Given("정상 응답, 응답 성공 코드"){
            val response = KISOrderReverseResponseDto(
                successFailureStatus = "0",
                responseMessage = "정상 처리되었습니다."
            )

            When("KIS의 응답의 성공코드가 0이면"){
                every { joinPoint.proceed() } returns Mono.just(response)

                Then("그 결과를 반환한다.") {
                    val result = kisClientAspect.handleKISWebClientException(joinPoint)
                    StepVerifier.create(result as Publisher<out Any>)
                        .expectNext(response)
                        .verifyComplete()
                }
            }
        }

        Given("KIS 응답 에러 403"){
            val responseException = WebClientResponseException.create(
                403, "Forbidden",
                org.springframework.http.HttpHeaders(),
                "Error body".toByteArray(),
                null
            )
            When("KIS로부터 403 에러를 받으면"){
                every { joinPoint.proceed() } returns Mono.error<WebClientResponseException>(responseException )

                Then("그 결과를 반환한다.") {
                    val result = kisClientAspect.handleKISWebClientException(joinPoint)
                    StepVerifier.create(result as Publisher<out Any>)
                        .expectErrorMatches { it is CustomException && it.errorCode == ErrorCode.FORBIDDEN }
                        .verify()
                }
            }
        }

        Given("KIS 응답 에러 500"){
            val responseException = WebClientResponseException.create(
                500, "Internal_Server_Error",
                org.springframework.http.HttpHeaders(),
                "Error body".toByteArray(),
                null
            )
            When("KIS로부터 500 에러를 받으면"){
                every { joinPoint.proceed() } returns Mono.error<WebClientResponseException>(responseException )

                Then("그 결과를 반환한다.") {
                    val result = kisClientAspect.handleKISWebClientException(joinPoint)
                    StepVerifier.create(result as Publisher<out Any>)
                        .expectErrorMatches { it is CustomException && it.errorCode == ErrorCode.INTERNAL_SERVER_ERROR }
                        .verify()
                }
            }
        }

        Given("정상 응답, 응답 실패 코드"){
            val response = KISOrderReverseResponseDto(
                successFailureStatus = "1",
                responseMessage = "모의장이 휴장입니다."
            )

            When("KIS의 응답의 성공코드가 0이 아니면"){
                every { joinPoint.proceed() } returns Mono.just(response)

                Then("에러를 일으키고 메세지를 반환한다.") {
                    val result = kisClientAspect.handleKISWebClientException(joinPoint)
                    StepVerifier.create(result as Publisher<out Any>)
                        .expectErrorMatches { it is CustomException &&
                                it.errorCode == ErrorCode.KIS_API_FAILED &&
                                it.message == "모의장이 휴장입니다."}
                        .verify()
                }
            }
        }

        Given("KIS 요청에서 예측되지 못한 에러의 경우"){
            val responseException = CustomException(ErrorCode.INTERNAL_SERVER_ERROR)

            When("알 수 없는 에러가 발생하면"){
                every { joinPoint.proceed() } returns Mono.error<CustomException>(responseException )

                Then("그 결과를 그대로 반환한다.") {
                    val result = kisClientAspect.handleKISWebClientException(joinPoint)
                    StepVerifier.create(result as Publisher<out Any>)
                        .expectErrorMatches { it is CustomException && it.errorCode == ErrorCode.INTERNAL_SERVER_ERROR }
                        .verify()
                }
            }
        }

        Given("KIS 요청 이전에 예측 불가능 에러"){

            When("중간에 알 수 없는 에러가 발생하면"){
                every { joinPoint.proceed() } throws CustomException(ErrorCode.INTERNAL_SERVER_ERROR)

                Then("그 에러를 반환한다.") {
                    val result = kisClientAspect.handleKISWebClientException(joinPoint)
                    StepVerifier.create(result as Publisher<out Any>)
                        .expectErrorMatches { it is CustomException && it.errorCode == ErrorCode.INTERNAL_SERVER_ERROR }
                        .verify()
                }
            }
        }
    }
})