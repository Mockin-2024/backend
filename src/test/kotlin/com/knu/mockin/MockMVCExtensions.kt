package com.knu.mockin

import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.request.ParameterDescriptor
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.queryParameters
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActionsDsl
import org.springframework.test.web.servlet.get
import kotlin.reflect.full.memberProperties

fun <T> MockMvc.getWithParams(uri: String, requestParams: T): ResultActionsDsl {
    val params = requestParams!!::class.java.kotlin.memberProperties
        .associate { it.name to it.call(requestParams)?.toString() }

    return this.get(uri) {
        params.forEach { (key, value) ->
            if (value != null) param(key, value.toString())
        }
    }
}

fun setParameters(vararg params: Pair<String, String>): List<ParameterDescriptor> {
    return params.map { (name, description) ->
        parameterWithName(name).description(description) }
}

fun ResultActionsDsl.makeDocument(
    identifier:String,
    parameters: List<ParameterDescriptor>
): ResultActionsDsl {
    return this.andExpect {
        status { is2xxSuccessful() }
    }.andDo {
        handle(
            MockMvcRestDocumentation.document(
                identifier,
                queryParameters(
                    parameters
                )
            )
        )
    }
}