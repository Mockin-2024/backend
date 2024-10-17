package com.knu.mockin.dsl

import com.knu.mockin.model.Field
import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.request.ParameterDescriptor
import org.springframework.restdocs.request.RequestDocumentation

fun parameters(vararg params: Pair<String, String>): List<ParameterDescriptor> {
    return params.map { (name, description) ->
        RequestDocumentation.parameterWithName(name).description(description) }
}

fun parametersTemp(params: List<Pair<String, String>>): List<ParameterDescriptor> {
    return params.map { (name, description) ->
        RequestDocumentation.parameterWithName(name).description(description) }
}

fun requestBody(vararg bodies: Pair<Field, String>): List<FieldDescriptor>{
    return bodies.map{ (field, description) ->
        field.descriptor.description(description)
    }
}
fun requestBodyTemp(bodies: List<Pair<Field, String>>): List<FieldDescriptor>{
    return bodies.map{ (field, description) ->
        field.descriptor.description(description)
    }
}
fun responseBody(vararg bodies: Pair<Field, String>): List<FieldDescriptor>{
    return bodies.map{ (field, description) ->
        field.descriptor.description(description)
    }
}

fun responseBodyTemp(bodies: List<Pair<Field, String>>): List<FieldDescriptor>{
    return bodies.map{ (field, description) ->
        field.descriptor.description(description)
    }
}