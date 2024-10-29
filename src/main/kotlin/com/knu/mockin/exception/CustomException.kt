package com.knu.mockin.exception

class CustomException(
    val errorCode: ErrorCode,
    message: String? = null
) :RuntimeException(message ?: errorCode.message) {
    val result: Int = errorCode.status
}