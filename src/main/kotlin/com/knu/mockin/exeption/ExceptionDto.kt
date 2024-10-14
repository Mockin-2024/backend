package com.knu.mockin.exeption

data class ExceptionDto(
    val status: Int,
    val errorCode: ErrorCode,
    val message: String?,
)