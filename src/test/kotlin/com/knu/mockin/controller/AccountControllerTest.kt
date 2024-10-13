package com.knu.mockin.controller

import com.knu.mockin.dsl.*
import com.knu.mockin.model.STRING
import com.knu.mockin.model.dto.request.account.UserRequestDto
import com.knu.mockin.model.dto.response.SimpleMessageResponseDto
import com.knu.mockin.service.AccountService
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.StringSpec
import io.kotest.extensions.spring.SpringExtension
import io.mockk.coEvery
import io.mockk.mockk
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.restdocs.ManualRestDocumentation
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@WebMvcTest(AccountController::class)
class AccountControllerTest(
    @MockkBean
    val accountService:AccountService = mockk(),
    val webApplicationContext: WebApplicationContext
): StringSpec({
    val restDocumentation = ManualRestDocumentation()
    lateinit var mockMvc: MockMvc

    beforeTest {
        mockMvc = MockMvcBuilders
            .webAppContextSetup(webApplicationContext)
            .apply<DefaultMockMvcBuilder>(documentationConfiguration(restDocumentation)
                .operationPreprocessors()
                .withRequestDefaults(Preprocessors.prettyPrint())
                .withResponseDefaults(Preprocessors.prettyPrint()))
            .build()
        restDocumentation.beforeTest(TradingControllerTest::class.java, it.name.testName)
    }

    afterTest {
        restDocumentation.afterTest()
    }
    val baseUri = "/account"

    "POST /account/user" {
        val uri = "$baseUri/user"
        val requestDto = UserRequestDto(
            email = "test@naver.com",
            name = "test"
        )
        val expectedDto = SimpleMessageResponseDto("Register Complete")
        coEvery { accountService.postUser(requestDto) } returns expectedDto

        val response = mockMvc.postWithBody(uri, requestDto, expectedDto)

        response.makeDocument(
            identifier = "/account/user",
            requestBody = requestBody(
                "email" type STRING means "사용자 이메일",
                "name" type STRING means "사용자 이름",
            ),
            responseBody =  responseBody(
                "message" type STRING means "응답 메세지",
            )
        )
    }
}){
    override fun extensions() = listOf(SpringExtension)
}