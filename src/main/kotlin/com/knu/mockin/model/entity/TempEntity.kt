package com.knu.mockin.model.entity

data class TempEntity(
    private val status: String
)

/**
 * 예시
 * @Entity
 * @Table(name = "users")
 * data class User(
 *     @Id
 *     @GeneratedValue(strategy = GenerationType.IDENTITY)
 *     val id: Long = 0,
 *
 *     @Column(nullable = false, unique = true)
 *     val username: String,
 *
 *     @Column(nullable = false)
 *     val password: String
 * )
 * */