package com.knu.mockin.repository

import com.knu.mockin.model.entity.User
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono

interface UserRepository : ReactiveCrudRepository<User, String> {
    @Query("insert into user (email, name) " +
            "values (:#{#user.email}, :#{#user.name})")
    fun save(user: User): Mono<User>

    @Query("update user set account_number = :accountNumber where email=:email")
    fun updateByEmail(email: String, accountNumber: String): Mono<User>
    fun findByEmail(email: String): Mono<User>
}