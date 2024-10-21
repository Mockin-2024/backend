    package com.knu.mockin.controller

    import com.knu.mockin.controller.util.*
    import com.knu.mockin.dsl.RestDocsUtils.readJsonFile
    import com.knu.mockin.dsl.RestDocsUtils.toBody
    import com.knu.mockin.dsl.toDto
    import com.knu.mockin.model.dto.request.login.Jwt
    import com.knu.mockin.model.dto.response.SimpleMessageResponseDto
    import com.knu.mockin.service.EmailService
    import com.knu.mockin.service.UserService
    import com.ninjasquad.springmockk.MockkBean
    import io.kotest.core.spec.style.StringSpec
    import io.kotest.extensions.spring.SpringExtension
    import io.mockk.coEvery
    import io.mockk.mockk
    import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
    import org.springframework.restdocs.ManualRestDocumentation
    import org.springframework.test.web.servlet.MockMvc
    import org.springframework.web.context.WebApplicationContext

    @WebMvcTest(LoginController::class)
    class LoginControllerTest(
        @MockkBean
        val emailService: EmailService = mockk(),
        @MockkBean
        val userService: UserService = mockk(),
        private val webApplicationContext: WebApplicationContext
    ): StringSpec({
        val restDocumentation = ManualRestDocumentation()
        lateinit var mockMvc: MockMvc

        beforeTest {
            mockMvc = buildMockMvc(webApplicationContext, restDocumentation)
            restDocumentation.beforeTest(LoginControllerTest::class.java, it.name.testName)
        }

        afterTest {
            restDocumentation.afterTest()
        }

        val baseUri = "/auth"

        "POST /auth/signup" {
            val uri = "${baseUri}/signup"
            val requestDto = readJsonFile(uri, "requestDto.json")
            val expectedDto = readJsonFile(uri, "responseDto.json") toDto SimpleMessageResponseDto::class.java
            coEvery { userService.createUser(any()) } returns expectedDto

            val response = mockMvc.postWithBody(uri, requestDto, expectedDto)

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

            val response = mockMvc.postWithBody(uri, requestDto, expectedDto)

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


            val response = mockMvc.postWithBody(uri, requestDto, expectedDto)

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

            val response = mockMvc.postWithBody(uri, requestDto, expectedDto)

            response.makeDocument(
                uri,
                requestBody(readJsonFile(uri, "requestDtoDescription.json").toBody()),
                responseBody(readJsonFile(uri, "responseDtoDescription.json").toBody())
            )
        }
    }) {
        override fun extensions() = listOf(SpringExtension)
    }
