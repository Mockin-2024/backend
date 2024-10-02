package com.knu.mockin.model.entity

import org.springframework.data.annotation.Id

data class User(
    @Id val id: Long,
    val appKey: String,
    val appSecret: String,
    var token: String?
)