package com.knu.mockin.repository

import com.knu.mockin.model.entity.MockKey
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono

interface MockKeyRepository : ReactiveCrudRepository<MockKey, String> {
    fun findByEmail(email: String): Mono<MockKey>
}