package com.knu.mockin.model.entity

data class UserWithMockKey(
    val email: String,
    val accountNumber: String,
    val appKey: String,
    val appSecret: String
)
