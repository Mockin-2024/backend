package com.knu.mockin.model.dto.request.login

data class EmailCheckRequestDto (
    val email: String = "",
    val authNum: String = ""
)