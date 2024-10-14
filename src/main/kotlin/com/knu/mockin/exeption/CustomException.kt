package com.knu.mockin.exeption

class CustomException(
    val errorCode: ErrorCode,
    message: String? = null
) :RuntimeException(message ?: errorCode.message) {
    val result: Int = errorCode.status
}