package com.knu.mockin.model.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("user")
data class User(
    @Id val id: Long,
    val appKey: String,
    val appSecret: String,
    var token: String?,
    val accountNumber: String
)