package com.knu.mockin.exception

data class ExceptionDto(
    val status: Int,
    val errorCode: ErrorCode,
    val message: String?,
)