package com.knu.mockin.dsl

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.knu.mockin.model.*
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

    fun String.toPairs(): List<Pair<String, String>> {
        val jsonNode: JsonNode = objectMapper.readTree(this)
        return jsonNode.fieldNames().asSequence().map { fieldName ->
            fieldName to jsonNode[fieldName].asText()
        }.toList()
    }

    fun String.toBody(): List<Pair<Field, String>> {
        val jsonNode: JsonNode = objectMapper.readTree(this)
        val fieldDescriptions = mutableListOf<Pair<Field, String>>()

        jsonNode.fieldNames().forEach { fieldName ->
            if (fieldName.contains("output")) {
                fieldDescriptions.addAll(jsonNode[fieldName].processOutput(fieldName))
            }else{
                if(fieldName in listOf("expire_in")){
                    fieldDescriptions.add(fieldName type NUMBER means jsonNode[fieldName].asText())
                }
                else fieldDescriptions.add(fieldName type STRING means jsonNode[fieldName].asText())
            }
        }

        return fieldDescriptions
    }
    private fun JsonNode?.processOutput(name: String): List<Pair<Field, String>> {
        val fieldDescriptions = mutableListOf<Pair<Field, String>>()
        if (this != null) {
            if (this.isArray) {
                fieldDescriptions.add(name type ARRAY means "$name 상세")
                val firstItem = this.firstOrNull()
                firstItem?.fieldNames()?.forEach { fieldName ->
                    fieldDescriptions.add("$name[0].$fieldName" type STRING means firstItem[fieldName].asText())
                }
            } else {
                fieldDescriptions.add(name type OBJECT means "$name 상세")
                this.fieldNames().forEach { fieldName ->
                    fieldDescriptions.add("$name.$fieldName" type STRING means this[fieldName].asText())
                }
            }
        }
        return fieldDescriptions
    }

}