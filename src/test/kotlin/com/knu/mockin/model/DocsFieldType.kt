package com.knu.mockin.model

import org.springframework.restdocs.payload.JsonFieldType

sealed class DocsFieldType(
    val type: JsonFieldType
)

data object ARRAY: DocsFieldType(JsonFieldType.ARRAY)
data object BOOLEAN: DocsFieldType(JsonFieldType.BOOLEAN)
data object OBJECT: DocsFieldType(JsonFieldType.OBJECT)
data object NUMBER: DocsFieldType(JsonFieldType.NUMBER)
data object NULL: DocsFieldType(JsonFieldType.NULL)
data object STRING: DocsFieldType(JsonFieldType.STRING)
data object ANY: DocsFieldType(JsonFieldType.VARIES)
data object DATE: DocsFieldType(JsonFieldType.STRING)
data object DATETIME: DocsFieldType(JsonFieldType.STRING)

