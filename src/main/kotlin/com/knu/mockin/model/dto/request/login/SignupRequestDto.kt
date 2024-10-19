package com.knu.mockin.model.dto.request.login

data class SignupRequestDto(
    val email: String = "",
    val password: String = "",
    val name: String = ""
)
