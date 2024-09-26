package com.knu.mockin.logging.utils

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.util.UUID
object LogUtil {
    val objectMapper = jacksonObjectMapper()

    fun generateTraceId(): String {
        return UUID.randomUUID().toString()
    }

    fun <T> toJson(target:T): String{
        return objectMapper.writeValueAsString(target)
    }
}