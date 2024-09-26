package com.knu.mockin.logging.model

data class LogEntry(
    val traceId: String,
    val userId: Long,
    val api: String,
    val message: String
)
