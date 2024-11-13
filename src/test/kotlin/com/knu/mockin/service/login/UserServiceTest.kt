package com.knu.mockin.service.login

import com.knu.mockin.dsl.RestDocsUtils.readJsonFile
import com.knu.mockin.exception.CustomException
import com.knu.mockin.exception.ErrorCode
import com.knu.mockin.model.dto.request.login.Jwt
import com.knu.mockin.model.dto.request.login.LoginRequestDto
import com.knu.mockin.model.dto.request.login.SignupRequestDto
import com.knu.mockin.model.dto.response.SimpleMessageResponseDto
import com.knu.mockin.model.entity.User
import com.knu.mockin.repository.UserRepository
import com.knu.mockin.security.BearerToken
import com.knu.mockin.security.JwtUtil
import com.knu.mockin.util.ExtensionUtil.toDto
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.security.crypto.password.PasswordEncoder
import reactor.core.publisher.Mono

class UserServiceTest(
    private val encoder: PasswordEncoder = mockk<PasswordEncoder>(),
    private val jwtUtil: JwtUtil = mockk<JwtUtil>(),
    private val userRepository: UserRepository = mockk<UserRepository>()
) :BehaviorSpec({
    val userService = UserService(encoder, jwtUtil, userRepository)
    val user = readJsonFile("setting", "user.json") toDto User::class.java

    val baseUri = "auth"
    Context("createUser 함수의 경우"){
        val uri = "$baseUri/signup"

        Given("적절한 dto가 주어질 때"){
            val bodyDto = readJsonFile(uri, "requestDto.json") toDto SignupRequestDto::class.java
            val expectedDto = readJsonFile(uri, "responseDto.json") toDto SimpleMessageResponseDto::class.java

            When("사용자가 없는 경우 사용자를 생성하여 db에 저장한 후"){
                every { encoder.encode(any()) } returns "1111"
                every { userRepository.findByEmail(bodyDto.email) } returns Mono.justOrEmpty(null)
                every { userRepository.save(any()) } returns Mono.just(user)

                Then("응답 DTO를 정상적으로 받아야 한다."){
                    val result = userService.createUser(bodyDto)
                    result shouldBe expectedDto
                }
            }
        }

        Given("적절한 dto가 주어졌지만"){
            val bodyDto = readJsonFile(uri, "requestDto.json") toDto SignupRequestDto::class.java

            When("이미 존재하는 사용자일 경우"){
                every { userRepository.findByEmail(bodyDto.email) } returns Mono.justOrEmpty(user)

                Then("ALREADY_REGISTERED 에러를 받아야 한다."){
                    val result = shouldThrowExactly<CustomException> {
                        userService.createUser(bodyDto)
                    }
                    result shouldBe CustomException(ErrorCode.ALREADY_REGISTERED)
                }
            }
        }
    }

    Context("loginUser 함수의 경우"){
        val uri = "$baseUri/login"

        Given("적절한 dto가 주어질 때"){
            val bodyDto = readJsonFile(uri, "requestDto.json") toDto LoginRequestDto::class.java
            val expectedDto = readJsonFile(uri, "responseDto.json") toDto Jwt::class.java

            When("요청 dto의 패스워드가 db에 등록된 것과 같으면"){
                every { userRepository.findByEmail(bodyDto.email) } returns Mono.just(user)
                every { encoder.matches(any(), any()) } returns true
                every { jwtUtil.generate(user.email) } returns BearerToken(expectedDto.token)

                Then("JWT 토큰이 담긴 Dto를 정상적으로 받아야 한다."){
                    val result = userService.loginUser(bodyDto)
                    result shouldBe expectedDto
                }
            }
        }

        Given("적절한 dto가 주어졌지만"){
            val bodyDto = readJsonFile(uri, "requestDto.json") toDto LoginRequestDto::class.java

            When("요청 dto의 패스워드가 db에 등록된 것과 다르면"){
                every { userRepository.findByEmail(bodyDto.email) } returns Mono.just(user)
                every { encoder.matches(any(), any()) } returns false

                Then("INVALID_LOGIN 에러를 받아야 한다."){
                    val result = shouldThrowExactly<CustomException> {
                        userService.loginUser(bodyDto)
                    }
                    result shouldBe CustomException(ErrorCode.INVALID_LOGIN)
                }
            }
        }

        Given("적절한 dto가 주어졌지만"){
            val bodyDto = readJsonFile(uri, "requestDto.json") toDto LoginRequestDto::class.java

            When("사용자가 존재하지 않으면"){
                every { userRepository.findByEmail(bodyDto.email) } returns Mono.justOrEmpty(null)

                Then("INVALID_LOGIN 에러를 받아야 한다."){
                    val result = shouldThrowExactly<CustomException> {
                        userService.loginUser(bodyDto)
                    }
                    result shouldBe CustomException(ErrorCode.INVALID_LOGIN)
                }
            }
        }
    }
})