package com.knu.mockin.model.dto.request.login

data class TokenValidationRequestDto(
    val email: String,
    val token: String
)
