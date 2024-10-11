package com.knu.mockin.dsl

import com.knu.mockin.model.RestDocsAttributeKeys
import org.springframework.restdocs.snippet.Attributes

object RestDocsUtils {
    fun defaultValue(value: String): Attributes.Attribute {
        return Attributes.Attribute(RestDocsAttributeKeys.KEY_DEFAULT_VALUE, value)
    }

    fun customFormat(value: String): Attributes.Attribute {
        return Attributes.Attribute(RestDocsAttributeKeys.KEY_FORMAT, value)
    }

    fun customSample(value: String): Attributes.Attribute {
        return Attributes.Attribute(RestDocsAttributeKeys.KEY_SAMPLE, value)
    }
}