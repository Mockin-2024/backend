package com.knu.mockin.model.dto.request.account

data class KeyPairRequestDto(
    val appKey: String,
    val appSecret: String,
    val email: String
)