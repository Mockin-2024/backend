package com.knu.mockin.dsl

import com.knu.mockin.model.Field
import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.request.ParameterDescriptor
import org.springframework.restdocs.request.RequestDocumentation

fun setParameters(vararg params: Pair<String, String>): List<ParameterDescriptor> {
    return params.map { (name, description) ->
        RequestDocumentation.parameterWithName(name).description(description) }
}

fun setBody(vararg bodies: Pair<Field, String>): List<FieldDescriptor>{
    return bodies.map{ (field, description) ->
        field.descriptor.description(description)
    }
}