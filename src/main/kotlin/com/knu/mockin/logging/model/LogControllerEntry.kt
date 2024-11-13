package com.knu.mockin.logging.model

data class LogControllerEntry (
    val timestamp: String,        // 현재 시간
    val traceId: String,          // 트레이스 ID
    val userId: String?,            // 사용자 ID
    val className: String,        // 클래스 이름
    val methodName: String,       // 메서드 이름
    val executionTime: Long,      // 실행 시간 (밀리초)
    val message: String?          // 메세지
)