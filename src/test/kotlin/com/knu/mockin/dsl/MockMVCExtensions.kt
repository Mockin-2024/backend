package com.knu.mockin.dsl

import com.knu.mockin.logging.utils.LogUtil.toJson
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.restdocs.ManualRestDocumentation
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.PayloadDocumentation.requestFields
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.request.ParameterDescriptor
import org.springframework.restdocs.request.RequestDocumentation.queryParameters
import org.springframework.test.web.servlet.*
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import kotlin.reflect.full.memberProperties

fun buildMockMvc(
    webApplicationContext: WebApplicationContext,
    restDocumentation: ManualRestDocumentation
): MockMvc {
    return MockMvcBuilders
        .webAppContextSetup(webApplicationContext)
        .apply<DefaultMockMvcBuilder>(
            MockMvcRestDocumentation.documentationConfiguration(restDocumentation)
            .operationPreprocessors()
            .withRequestDefaults(Preprocessors.prettyPrint())
            .withResponseDefaults(Preprocessors.prettyPrint()))
        .build()
}

fun <T> MockMvc.getWithParams(uri: String, requestParams: T, expectedDto: T): ResultActionsDsl {
    val params = requestParams!!::class.java.kotlin.memberProperties
        .associate { it.name to it.call(requestParams)?.toString() }

    return this.get(uri) {
        params.forEach { (key, value) ->
            if (value != null) param(key, value.toString())
        }
    }.asyncDispatch().andExpect {
        status { isOk() }
        content {
            json(toJson(expectedDto))
        }
    }
}

fun <T> MockMvc.postWithBody(uri: String, requestBody: T, expectedDto: T): ResultActionsDsl{
    return this.post(uri){
        contentType = APPLICATION_JSON
        content = requestBody
    }.asyncDispatch().andExpect {
        status { isOk() }
        content {
            json(toJson(expectedDto))
        }
    }
}

fun <T> MockMvc.patchWithBody(uri: String, requestBody: T, expectedDto: T): ResultActionsDsl {
    return this.patch(uri) {
        contentType = APPLICATION_JSON
        content = toJson(requestBody)
    }.asyncDispatch().andExpect {
        status { isOk() }
        content {
            json(toJson(expectedDto))
        }
    }
}

fun ResultActionsDsl.makeDocument(
    identifier:String,
    parameters: List<ParameterDescriptor>,
    responseBody: List<FieldDescriptor>
): ResultActionsDsl {
    return this.andDo {
        handle(
            MockMvcRestDocumentation.document(
                identifier,
                queryParameters(parameters),
                responseFields(responseBody)
            )
        )
    }
}

fun ResultActionsDsl.makeDocument(
    identifier:String,
    requestBody: List<FieldDescriptor>,
    responseBody: List<FieldDescriptor>,
    isRequestBodyPresent: Boolean? = true
): ResultActionsDsl {
    return this.andDo {
        handle(
            MockMvcRestDocumentation.document(
                identifier,
                requestFields(requestBody),
                responseFields(responseBody)
            )
        )
    }
}