package com.knu.mockin.controller

import com.knu.mockin.controller.util.*
import com.knu.mockin.dsl.RestDocsUtils.readJsonFile
import com.knu.mockin.dsl.RestDocsUtils.toBody
import com.knu.mockin.dsl.RestDocsUtils.toPairs
import com.knu.mockin.model.dto.request.account.UserAccountNumberRequestDto
import com.knu.mockin.model.dto.request.favorite.FavoriteRequestDto
import com.knu.mockin.model.dto.request.login.EmailRequestDto
import com.knu.mockin.model.dto.response.FavoriteListDto
import com.knu.mockin.model.dto.response.SimpleMessageResponseDto
import com.knu.mockin.model.entity.User
import com.knu.mockin.repository.UserRepository
import com.knu.mockin.security.JwtUtil
import com.knu.mockin.security.SecurityTestConfig
import com.knu.mockin.service.FavoriteService
import com.knu.mockin.util.ExtensionUtil.toDto
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.StringSpec
import io.mockk.coEvery
import io.mockk.mockk
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Import
import org.springframework.restdocs.ManualRestDocumentation
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono

@WebFluxTest(controllers = [FavoriteController::class])
@Import(SecurityTestConfig::class)
class FavoriteControllerTest(
    @MockkBean
    val favoriteService: FavoriteService = mockk(),
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
        restDocumentation.beforeTest(FavoriteControllerTest::class.java, it.name.testName)

        val user = readJsonFile("setting", "user.json") toDto User::class.java
        coEvery { userRepository.findByEmail(user.email) } returns Mono.just(user)
        coEvery { jwtUtil.getUsername(any()) } returns user.email
        coEvery { jwtUtil.isValid(any(), any()) } returns true
    }

    afterTest {
        restDocumentation.afterTest()
    }

    val baseUri = "favorite"

    "POST /favorite/add" {
        val uri = "${baseUri}/add"
        val requestDto = readJsonFile(uri, "requestDto.json")
        val expectedDto = readJsonFile(uri, "responseDto.json") toDto SimpleMessageResponseDto::class.java
        coEvery { favoriteService.addFavorite(any(), any()) } returns expectedDto

        val response = webTestClient.postWithBody(uri, requestDto, expectedDto)

        response.makeDocument(
            uri,
            requestBody(readJsonFile(uri, "requestDtoDescription.json").toBody()),
            responseBody(readJsonFile(uri, "responseDtoDescription.json").toBody())
        )
    }

    "GET /favorite/read" {
        val uri = "${baseUri}/read"
        val requestParams = readJsonFile(uri, "requestDto.json") toDto EmailRequestDto::class.java
        val expectedDto = readJsonFile(uri, "responseDto.json") toDto FavoriteListDto::class.java
        coEvery { favoriteService.readAllFavorite(any()) } returns expectedDto

        val response = webTestClient.getWithParams(uri, requestParams, expectedDto)

        response.makeDocument(
            uri,
            parameters(readJsonFile(uri, "requestDtoDescription.json").toPairs()),
            responseBody(readJsonFile(uri, "responseDtoDescription.json").toBody())
        )
    }

    "DELETE /favorite/delete" {
        val uri = "${baseUri}/delete"
        val requestDto = readJsonFile(uri, "requestDto.json")
        val expectedDto = readJsonFile(uri, "responseDto.json") toDto SimpleMessageResponseDto::class.java
        coEvery { favoriteService.deleteFavorite(any(), any()) } returns expectedDto

        val response = webTestClient.deleteWithBody(uri, requestDto, expectedDto)

        response.makeDocument(
            uri,
            requestBody(readJsonFile(uri, "requestDtoDescription.json").toBody()),
            responseBody(readJsonFile(uri, "responseDtoDescription.json").toBody())
        )
    }

})