    package com.knu.mockin.controller

    import com.knu.mockin.controller.util.*
    import com.knu.mockin.dsl.RestDocsUtils.readJsonFile
    import com.knu.mockin.dsl.RestDocsUtils.toBody
    import com.knu.mockin.model.dto.request.login.Jwt
    import com.knu.mockin.model.dto.response.SimpleMessageResponseDto
    import com.knu.mockin.model.entity.User
    import com.knu.mockin.repository.UserRepository
    import com.knu.mockin.security.JwtUtil
    import com.knu.mockin.security.SecurityTestConfig
    import com.knu.mockin.service.login.EmailService
    import com.knu.mockin.service.login.UserService
    import com.knu.mockin.util.ExtensionUtil.toDto
    import com.ninjasquad.springmockk.MockkBean
    import io.kotest.core.spec.style.StringSpec
    import io.kotest.extensions.spring.SpringExtension
    import io.mockk.coEvery
    import io.mockk.mockk
    import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
    import org.springframework.context.ApplicationContext
    import org.springframework.context.annotation.Import
    import org.springframework.restdocs.ManualRestDocumentation
    import org.springframework.test.web.reactive.server.WebTestClient
    import reactor.core.publisher.Mono

    @WebFluxTest(controllers = [LoginController::class])
    @Import(SecurityTestConfig::class)
    class LoginControllerTest(
        @MockkBean
        val emailService: EmailService = mockk(),
        @MockkBean
        val userService: UserService = mockk(),
        @MockkBean
        val userRepository: UserRepository = mockk(),
        @MockkBean
        val jwtUtil: JwtUtil = mockk<JwtUtil>(),
        private val context: ApplicationContext,
    ): StringSpec({
        val restDocumentation = ManualRestDocumentation()
        lateinit var webTestClient: WebTestClient

        beforeTest {
            webTestClient = buildWebTestClient(context, restDocumentation)
            restDocumentation.beforeTest(LoginControllerTest::class.java, it.name.testName)

            val user = readJsonFile("setting", "user.json") toDto User::class.java
            coEvery { userRepository.findByEmail(user.email) } returns Mono.just(user)
            coEvery { jwtUtil.getUsername(any()) } returns user.email
            coEvery { jwtUtil.isValid(any(), any()) } returns true
        }

        afterTest {
            restDocumentation.afterTest()
        }

        val baseUri = "auth"

        "POST /auth/signup" {
            val uri = "${baseUri}/signup"
            val requestDto = readJsonFile(uri, "requestDto.json")
            val expectedDto = readJsonFile(uri, "responseDto.json") toDto SimpleMessageResponseDto::class.java
            coEvery { userService.createUser(any()) } returns expectedDto

            val response = webTestClient.postWithBody(uri, requestDto, expectedDto)

            response.makeDocument(
                uri,
                requestBody(readJsonFile(uri, "requestDtoDescription.json").toBody()),
                responseBody(readJsonFile(uri, "responseDtoDescription.json").toBody())
            )
        }

        "POST /auth/send" {
            val uri = "${baseUri}/send"
            val requestDto = readJsonFile(uri, "requestDto.json")
            val expectedDto = readJsonFile(uri, "responseDto.json") toDto SimpleMessageResponseDto::class.java
            coEvery { emailService.sendEmail(any()) } returns expectedDto

            val response = webTestClient.postWithBody(uri, requestDto, expectedDto)

            response.makeDocument(
                uri,
                requestBody(readJsonFile(uri, "requestDtoDescription.json").toBody()),
                responseBody(readJsonFile(uri, "responseDtoDescription.json").toBody())
            )
        }

        "POST /auth/login" {
            val uri = "${baseUri}/login"
            val requestDto = readJsonFile(uri, "requestDto.json")
            val expectedDto = readJsonFile(uri, "responseDto.json") toDto Jwt::class.java
            coEvery { userService.loginUser(any()) } returns expectedDto


            val response = webTestClient.postWithBody(uri, requestDto, expectedDto)

            response.makeDocument(
                uri,
                requestBody(readJsonFile(uri, "requestDtoDescription.json").toBody()),
                responseBody(readJsonFile(uri, "responseDtoDescription.json").toBody())
            )
        }

        "POST /auth/check" {
            val uri = "${baseUri}/check"
            val requestDto = readJsonFile(uri, "requestDto.json")
            val expectedDto = readJsonFile(uri, "responseDto.json") toDto SimpleMessageResponseDto::class.java
            coEvery { emailService.checkAuthNum(any()) } returns expectedDto

            val response = webTestClient.postWithBody(uri, requestDto, expectedDto)

            response.makeDocument(
                uri,
                requestBody(readJsonFile(uri, "requestDtoDescription.json").toBody()),
                responseBody(readJsonFile(uri, "responseDtoDescription.json").toBody())
            )
        }
    }) {
        override fun extensions() = listOf(SpringExtension)
    }
