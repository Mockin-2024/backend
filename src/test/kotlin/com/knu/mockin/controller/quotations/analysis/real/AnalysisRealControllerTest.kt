package com.knu.mockin.controller.quotations.analysis.real

import com.knu.mockin.controller.quotations.analysis.AnalysisRealController
import com.knu.mockin.controller.quotations.basic.BasicRealController
import com.knu.mockin.controller.quotations.basic.real.BasicRealControllerTest
import com.knu.mockin.controller.util.*
import com.knu.mockin.dsl.RestDocsUtils
import com.knu.mockin.dsl.RestDocsUtils.toBody
import com.knu.mockin.dsl.RestDocsUtils.toPairs
import com.knu.mockin.dsl.toDto
import com.knu.mockin.model.dto.kisresponse.quotations.analysis.KISNewsTitleResponseDto
import com.knu.mockin.model.dto.kisresponse.quotations.basic.real.KISCountriesHolidayResponseDto
import com.knu.mockin.model.dto.request.quotations.analysis.real.NewsTitleRequestParameterDto
import com.knu.mockin.model.dto.request.quotations.basic.real.CountriesHolidayRequestParameterDto
import com.knu.mockin.model.entity.User
import com.knu.mockin.repository.UserRepository
import com.knu.mockin.security.JwtUtil
import com.knu.mockin.security.SecurityTestConfig
import com.knu.mockin.service.quotations.analysis.AnalysisRealService
import com.knu.mockin.service.quotations.basic.real.BasicRealService
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

@WebFluxTest(controllers = [AnalysisRealController::class])
@Import(SecurityTestConfig::class)
class AnalysisRealControllerTest (
    @MockkBean
    val basicRealService: AnalysisRealService = mockk<AnalysisRealService>(),
    @MockkBean
    val userRepository: UserRepository = mockk(),
    @MockkBean
    val jwtUtil: JwtUtil = mockk<JwtUtil>(),
    private val context: ApplicationContext,
) : StringSpec({
    val restDocumentation = ManualRestDocumentation()
    lateinit var webTestClient: WebTestClient

    beforeTest {
        webTestClient = buildWebTestClient(context, restDocumentation)
        restDocumentation.beforeTest(AnalysisRealControllerTest::class.java, it.name.testName)

        val user = RestDocsUtils.readJsonFile("setting", "user.json") toDto User::class.java
        coEvery { userRepository.findByEmail(user.email) } returns Mono.just(user)
        coEvery { jwtUtil.getUsername(any()) } returns user.email
        coEvery { jwtUtil.isValid(any(), any()) } returns true
    }

    afterTest {
        restDocumentation.afterTest()
    }

    val baseUri = "/quotations/analysis"

    "GET /quotations/analysis/news-title" {
        val uri = "${baseUri}/news-title"
        val requestParams =
            RestDocsUtils.readJsonFile(uri, "requestDto.json") toDto NewsTitleRequestParameterDto::class.java
        val expectedDto = RestDocsUtils.readJsonFile(uri, "responseDto.json") toDto KISNewsTitleResponseDto::class.java

        coEvery { basicRealService.getNewsTitle(any(), any()) } returns expectedDto
        val response = webTestClient.getWithParams(uri, requestParams, expectedDto)

        response.makeDocument(
            uri,
            parameters(RestDocsUtils.readJsonFile(uri, "requestDtoDescription.json").toPairs()),
            responseBody(RestDocsUtils.readJsonFile(uri, "responseDtoDescription.json").toBody())
        )
    }
})