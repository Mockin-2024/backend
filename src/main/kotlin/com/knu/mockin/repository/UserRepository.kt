package com.knu.mockin.repository

import com.knu.mockin.model.entity.User
import com.knu.mockin.model.entity.UserWithMockKey
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

    @Query("select u.email, u.account_number, m.app_key, m.app_secret " +
            "from user as u " +
            "inner join mock_key as m on u.email = m.email " +
            "where u.email = :email")
    fun findByEmailWithMockKey(email: String): Mono<UserWithMockKey>
}