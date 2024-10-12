package com.knu.mockin.dsl

import com.knu.mockin.logging.utils.LogUtil
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.request.ParameterDescriptor
import org.springframework.restdocs.request.RequestDocumentation.queryParameters
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActionsDsl
import org.springframework.test.web.servlet.get
import kotlin.reflect.full.memberProperties

fun <T> MockMvc.getWithParams(uri: String, requestParams: T, expectedDto: T): ResultActionsDsl {
    val params = requestParams!!::class.java.kotlin.memberProperties
        .associate { it.name to it.call(requestParams)?.toString() }

    return this.get(uri) {
        params.forEach { (key, value) ->
            if (value != null) param(key, value.toString())
        }
    }.asyncDispatch().andExpect {
        content {
            json(LogUtil.toJson(expectedDto))
        }
    }
}

fun <T> MockMvc.postWithBody(){

}

fun ResultActionsDsl.makeDocument(
    identifier:String,
    parameters: List<ParameterDescriptor>,
    body: List<FieldDescriptor>
): ResultActionsDsl {
    return this.andExpect {
        status { is2xxSuccessful() }
    }.andDo {
        handle(
            MockMvcRestDocumentation.document(
                identifier,
                queryParameters(parameters),
                responseFields(body)
            )
        )
    }
}