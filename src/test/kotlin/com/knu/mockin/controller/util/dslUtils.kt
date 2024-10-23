package com.knu.mockin.controller.util

import com.knu.mockin.model.Field
import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.request.ParameterDescriptor
import org.springframework.restdocs.request.RequestDocumentation
import org.springframework.security.core.userdetails.User
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import org.springframework.test.web.servlet.request.RequestPostProcessor

fun parameters(params: List<Pair<String, String>>): List<ParameterDescriptor> {
    return params.map { (name, description) ->
        RequestDocumentation.parameterWithName(name).description(description) }
}

fun requestBody(bodies: List<Pair<Field, String>>): List<FieldDescriptor>{
    return bodies.map{ (field, description) ->
        field.descriptor.description(description)
    }
}

fun responseBody(bodies: List<Pair<Field, String>>): List<FieldDescriptor>{
    return bodies.map{ (field, description) ->
        field.descriptor.description(description)
    }
}

fun authUser(): RequestPostProcessor {
    return user("test@naver.com").password("1111").roles("USER").authorities()
}