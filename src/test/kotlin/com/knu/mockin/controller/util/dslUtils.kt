package com.knu.mockin.controller.util

import com.knu.mockin.model.Field
import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.request.ParameterDescriptor
import org.springframework.restdocs.request.RequestDocumentation

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