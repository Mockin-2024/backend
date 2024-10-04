package com.knu.mockin.controller

import com.knu.mockin.logging.utils.LogUtil
import com.knu.mockin.model.dto.kisrequest.oauth.KISApprovalRequestDto
import com.knu.mockin.model.dto.response.ApprovalKeyResponseDto
import com.knu.mockin.service.AccountService
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.StringSpec
import io.kotest.extensions.spring.SpringExtension
import io.mockk.coEvery
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.restdocs.ManualRestDocumentation
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.restdocs.snippet.Snippet
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.servlet.MockMvcResultHandlersDsl
import org.springframework.test.web.servlet.client.MockMvcWebTestClient
import org.springframework.web.context.WebApplicationContext

//@WebMvcTest(AccountController::class)
@SpringBootTest
//@WebFluxTest
//@WebAppConfiguration
//@AutoConfigureRestDocs
class AccountControllerTest(
    @MockkBean
    private val accountService:AccountService,
    private val webApplicationContext:WebApplicationContext,
    @Value("\${ki.app-key}") var appKey: String,
    @Value("\${ki.app-secret}") var appSecret: String
): StringSpec({

    lateinit var webTestClient: WebTestClient
    val restDocumentation = ManualRestDocumentation()

    beforeTest {
        webTestClient = MockMvcWebTestClient.bindToApplicationContext(webApplicationContext)
            .configureClient()
            .filters { documentationConfiguration(restDocumentation) }
            .build()
        restDocumentation.beforeTest(AccountControllerTest::class.java, it.name.testName)
    }

    afterTest {
        restDocumentation.afterTest()
    }
    "POST /account returns approvalKey" {
        val requestDto = KISApprovalRequestDto(
            grantType = "client_credentials",
            appKey = appKey,
            secretKey = appSecret)
        val expectedDto = ApprovalKeyResponseDto("test")
        coEvery { accountService.getApprovalKey(requestDto) } returns expectedDto

        val result = webTestClient.post().uri("/account").accept(APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .json(LogUtil.toJson(expectedDto))
            .consumeWith{
                document("get approval key",
                    PayloadDocumentation.responseFields(
                        PayloadDocumentation
                            .fieldWithPath("approval_key")
                            .type(JsonFieldType.STRING)
                            .description("해당 approval key"))
                )
            }
            .returnResult()
//        MockMvcWebTestClient.resultActionsFor(result)
//            .andExpect(model().attribute("approval_key", "test"))
    }
}){
    override fun extensions() = listOf(SpringExtension)

    fun MockMvcResultHandlersDsl.document(identifier: String, vararg snippets: Snippet) {
        handle(MockMvcRestDocumentation.document(identifier, *snippets))
    }
}


////@WebMvcTest(AccountController::class)
//@SpringBootTest
//@WebAppConfiguration
//class AccountControllerTest(
//    @MockkBean
//    private val accountService:AccountService,
//    private val webApplicationContext:WebApplicationContext,
//    @Value("\${ki.app-key}") var appKey: String,
//    @Value("\${ki.app-secret}") var appSecret: String
//): StringSpec({
//
//    lateinit var mockMvc:MockMvc
//    val restDocumentation = ManualRestDocumentation()
//
//    beforeTest {
//        mockMvc = MockMvcBuilders
//            .webAppContextSetup(webApplicationContext)
//            .apply<DefaultMockMvcBuilder>(
//                MockMvcRestDocumentation.documentationConfiguration(restDocumentation)
//                    .operationPreprocessors()
//                    .withResponseDefaults(prettyPrint())
//            )
//            .build()
//        restDocumentation.beforeTest(AccountControllerTest::class.java, it.name.testName)
//    }
//
//    afterTest {
//        restDocumentation.afterTest()
//    }
//    "POST /account returns approvalKey" {
//        val requestDto = KISApprovalRequestDto(
//            grantType = "client_credentials",
//            appKey = appKey,
//            secretKey = appSecret)
//        val expectedDto = ApprovalKeyResponseDto("test")
//        every { accountService.getApprovalKey(requestDto) } returns Mono.just(expectedDto)
//
//        mockMvc.post("/account"){
//            accept(APPLICATION_JSON)
//        }.andExpect{
//            status().isOk
//            content().contentType(APPLICATION_JSON)
//            jsonPath("$.approval_key").value(expectedDto.approvalKey)
//        }.andDo{
//            handle(
//                document("get approval key",
//                PayloadDocumentation.responseFields(
//                    PayloadDocumentation
//                        .fieldWithPath("approval_key")
//                        .type(JsonFieldType.STRING)
//                        .description("해당 approval key"))
//                )
//            )
//        }.andReturn().asyncResult
//
////        StepVerifier.create(result)
////            .expectNextMatches { response ->
////                response.approvalKey == expectedDto.approvalKey
////            }
////            .verifyComplete()
//    }
//}){
//    override fun extensions() = listOf(SpringExtension)
//
//    fun MockMvcResultHandlersDsl.document(identifier: String, vararg snippets: Snippet) {
//        handle(MockMvcRestDocumentation.document(identifier, *snippets))
//    }
//}