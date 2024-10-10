package com.knu.mockin.model.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("user")
data class User(
    @Id val email: String,
    val name: String,
    val accountNumber: String
)