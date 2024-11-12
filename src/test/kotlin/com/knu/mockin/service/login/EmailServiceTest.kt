package com.knu.mockin.service.login

import com.knu.mockin.dsl.RestDocsUtils.readJsonFile
import com.knu.mockin.exception.CustomException
import com.knu.mockin.exception.ErrorCode
import com.knu.mockin.model.dto.request.login.EmailCheckRequestDto
import com.knu.mockin.model.dto.response.SimpleMessageResponseDto
import com.knu.mockin.model.entity.User
import com.knu.mockin.util.ExtensionUtil.toDto
import com.knu.mockin.util.RedisUtil
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import jakarta.mail.MessagingException
import jakarta.mail.internet.MimeMessage
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.mail.javamail.JavaMailSender
import java.util.concurrent.TimeUnit

class EmailServiceTest(
    private val javaMailSender: JavaMailSender = mockk<JavaMailSender>()
):BehaviorSpec({
    val emailService = EmailService(javaMailSender)
    val redisTemplate = mockk<RedisTemplate<String, String>>()
    RedisUtil.init(redisTemplate)

    val user = readJsonFile("setting", "user.json") toDto User::class.java
    val baseUri = "auth"

    beforeTest{
        emailService.serviceName = "mockin"
    }

    Context("sendEmail 함수의 경우"){
        val uri = "$baseUri/send"

        Given("적절한 dto가 주어질 때"){
            val expectedDto = readJsonFile(uri, "responseDto.json") toDto SimpleMessageResponseDto::class.java
            val message:MimeMessage = mockk<MimeMessage>(relaxed = true)

            When("랜덤한 인증번호를 생성하고, 사용자에게 인증 이메일을 보낸 후"){
                every { javaMailSender.createMimeMessage() } returns message
                every { javaMailSender.send(message) } returns Unit
                every { RedisUtil.saveEmailCode(any(), any(), 180, TimeUnit.SECONDS) } returns Unit

                Then("응답 DTO를 정상적으로 받아야 한다."){
                    val result = emailService.sendEmail(user.email)
                    result shouldBe expectedDto
                }
            }
        }

        Given("적절한 dto가 주어졌지만"){
            val message:MimeMessage = mockk<MimeMessage>(relaxed = true)

            When("이메일 보내기에 문제가 생겼을 경우"){
                every { javaMailSender.createMimeMessage() } returns message
                every { javaMailSender.send(message) } throws MessagingException("Failed to send email")

                Then("INTERNAL_SERVER_ERROR 에러를 받아야 한다."){
                    val result = shouldThrowExactly<CustomException> {
                        emailService.sendEmail(user.email)
                    }
                    result shouldBe CustomException(ErrorCode.INTERNAL_SERVER_ERROR)
                }
            }
        }
    }

    Context("checkAuthNum 함수의 경우"){
        val uri = "$baseUri/check"

        Given("적절한 dto가 주어질 때"){
            val bodyDto = readJsonFile(uri, "requestDto.json") toDto EmailCheckRequestDto::class.java
            val expectedDto = readJsonFile(uri, "responseDto.json") toDto SimpleMessageResponseDto::class.java

            When("Redis에 저장된 인증 코드와 요청 dto의 응답 코드를 비교하여"){
                every { RedisUtil.getToken(bodyDto.email) } returns bodyDto.authNum
                every { redisTemplate.delete(bodyDto.email) } returns true

                Then("두 값이 같으면 응답 DTO를 정상적으로 받아야 한다."){
                    val result = emailService.checkAuthNum(bodyDto)
                    result shouldBe expectedDto
                }
            }
        }

        Given("적절한 dto가 주어졌지만,"){
            val bodyDto = readJsonFile(uri, "requestDto.json") toDto EmailCheckRequestDto::class.java

            When("Redis에 저장된 인증 코드와 요청 dto의 응답 코드를 비교하여"){
                every { RedisUtil.getToken(bodyDto.email) } returns "00"

                Then("두 값이 같지 않으면 INVALID_INPUT 에러를 받아야 한다."){
                    val result = shouldThrowExactly<CustomException> {
                        emailService.checkAuthNum(bodyDto)
                    }
                    result shouldBe CustomException(ErrorCode.INVALID_INPUT)
                }
            }
        }
    }
})