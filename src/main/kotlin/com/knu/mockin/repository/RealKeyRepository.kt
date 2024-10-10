package com.knu.mockin.repository

import com.knu.mockin.model.entity.RealKey
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono

interface RealKeyRepository : ReactiveCrudRepository<RealKey, String> {
    fun findByEmail(email: String): Mono<RealKey>
}