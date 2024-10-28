package com.knu.mockin.controller.util

import com.knu.mockin.logging.utils.LogUtil.toJson
import org.springframework.context.ApplicationContext
import org.springframework.http.MediaType
import org.springframework.restdocs.ManualRestDocumentation
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.restdocs.request.ParameterDescriptor
import org.springframework.restdocs.request.RequestDocumentation
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.WebTestClient.BodyContentSpec

fun buildWebTestClient(
    context: ApplicationContext,
    restDocumentation: ManualRestDocumentation
): WebTestClient{
    return WebTestClient
        .bindToApplicationContext(context)
        .apply(SecurityMockServerConfigurers.springSecurity())
        .configureClient()
        .filter(WebTestClientRestDocumentation
            .documentationConfiguration(restDocumentation)
            .operationPreprocessors()
            .withRequestDefaults(Preprocessors.prettyPrint())
            .withResponseDefaults(Preprocessors.prettyPrint()))
        .build()
}
val authHeader = "Bearer token"

fun <T: Any> WebTestClient.getWithParams(uri: String, requestParams: T, expectedDto: T): BodyContentSpec{
    val targetUri = buildUriString(uri, requestParams)

    return this.get()
        .uri("/$targetUri")
        .accept(MediaType.APPLICATION_JSON)
        .header("Authorization", authHeader)
        .exchange()
        .expectStatus()
        .isOk
        .expectBody()
        .json(toJson(expectedDto))
}

fun <T: Any> WebTestClient.postWithBody(uri: String, requestBody: T, expectedDto: T): BodyContentSpec{
    return this.post()
        .uri("/$uri")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .header("Authorization", authHeader)
        .bodyValue(requestBody)
        .exchange()
        .expectStatus()
        .isOk
        .expectBody()
        .json(toJson(expectedDto))
}
fun <T: Any> WebTestClient.patchWithBody(uri: String, requestBody: T, expectedDto: T): BodyContentSpec{
    return this.patch()
        .uri("/$uri")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .header("Authorization", authHeader)
        .bodyValue(requestBody)
        .exchange()
        .expectStatus()
        .isOk
        .expectBody()
        .json(toJson(expectedDto))
}
fun BodyContentSpec.makeDocument(
    identifier:String,
    parameters: List<ParameterDescriptor>,
    responseBody: List<FieldDescriptor>,
): BodyContentSpec {
    return this.consumeWith(document(
        identifier,
        RequestDocumentation.queryParameters(parameters),
        PayloadDocumentation.responseFields(responseBody)
    ))
}

fun BodyContentSpec.makeDocument(
    identifier:String,
    requestBody: List<FieldDescriptor>,
    responseBody: List<FieldDescriptor>,
    isRequestBodyPresent: Boolean? = true
): BodyContentSpec {
    return this.consumeWith(document(
        identifier,
        PayloadDocumentation.requestFields(requestBody),
        PayloadDocumentation.responseFields(responseBody)
    ))
}
