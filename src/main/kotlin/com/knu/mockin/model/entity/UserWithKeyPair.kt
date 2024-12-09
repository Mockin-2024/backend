package com.knu.mockin.model.entity

data class UserWithKeyPair(
    val email: String = "",
    val accountNumber: String = "",
    val appKey: String = "",
    val appSecret: String = ""
)

data class UserInfo(
    val email: String = "",
    val password: String = "",
    val accountNumber: String = "",
    val appKey: String = "",
    val appSecret: String = ""
)