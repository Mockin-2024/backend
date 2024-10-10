package com.knu.mockin.repository

import com.knu.mockin.model.entity.User
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono

interface UserRepository : ReactiveCrudRepository<User, String> {
    fun findByEmail(email: String): Mono<User>
}