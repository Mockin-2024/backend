package com.knu.mockin.dsl

import com.fasterxml.jackson.databind.ObjectMapper
import com.knu.mockin.model.RestDocsAttributeKeys
import org.springframework.core.io.ClassPathResource
import org.springframework.restdocs.snippet.Attributes
import java.nio.file.Files

object RestDocsUtils {
    private val objectMapper = ObjectMapper().findAndRegisterModules()
    fun defaultValue(value: String): Attributes.Attribute {
        return Attributes.Attribute(RestDocsAttributeKeys.KEY_DEFAULT_VALUE, value)
    }

    fun customFormat(value: String): Attributes.Attribute {
        return Attributes.Attribute(RestDocsAttributeKeys.KEY_FORMAT, value)
    }

    fun customSample(value: String): Attributes.Attribute {
        return Attributes.Attribute(RestDocsAttributeKeys.KEY_SAMPLE, value)
    }

    fun readJsonFile(dirPath: String, fileName: String): String {
        val resource = ClassPathResource("json/$dirPath/$fileName") // test 폴더 하위
        return Files.readString(resource.file.toPath())
    }
}