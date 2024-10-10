package com.knu.mockin.model.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("mock_key")
data class MockKey(
    @Id val email: String,
    val appKey: String,
    val appSecret: String
)