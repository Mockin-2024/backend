package com.knu.mockin.dsl

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.knu.mockin.model.DocsFieldType
import com.knu.mockin.model.Field
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation


infix fun <T> T.means(description: String): Pair<T, String> {
    return Pair(this, description)
}

infix fun String.type(docsFieldType: DocsFieldType): Field {
    return createField(this, docsFieldType.type)
}

private fun createField(value: String, type: JsonFieldType): Field {
    val descriptor = PayloadDocumentation.fieldWithPath(value)
        .type(type)
        .description("")

    return Field(descriptor)
}
