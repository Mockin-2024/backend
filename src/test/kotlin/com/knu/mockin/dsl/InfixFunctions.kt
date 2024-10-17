package com.knu.mockin.dsl

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.knu.mockin.exeption.CustomException
import com.knu.mockin.exeption.ErrorCode
import com.knu.mockin.model.*
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation

infix fun <T> T.means(description: String): Pair<T, String> {
    return Pair(this, description)
}

infix fun String.type(docsFieldType: DocsFieldType): Field {
    return createField(this, docsFieldType.type)
}

infix fun <T> String.toDto(dto: Class<T>): T {
    val objectMapper = ObjectMapper()
    return objectMapper.readValue(this, dto)
}

private fun createField(value: String, type: JsonFieldType): Field {
    val descriptor = PayloadDocumentation.fieldWithPath(value)
        .type(type)
        .description("")

    return Field(descriptor)
}

fun String.toPairs(): List<Pair<String, String>> {
    val objectMapper = ObjectMapper()
    val jsonNode: JsonNode = objectMapper.readTree(this)
    return jsonNode.fieldNames().asSequence().map { fieldName ->
        fieldName to jsonNode[fieldName].asText()
    }.toList()
}