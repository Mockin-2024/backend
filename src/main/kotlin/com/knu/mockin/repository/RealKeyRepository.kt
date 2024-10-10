package com.knu.mockin.repository

import com.knu.mockin.model.entity.RealKey
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono

interface RealKeyRepository : ReactiveCrudRepository<RealKey, String> {
    @Query("insert into real_key (email, app_key, app_secret)" +
            "values (:#{#realKey.email},:#{#realKey.appKey},:#{#realKey.appSecret})")
    fun save(realKey: RealKey): Mono<RealKey>
    fun findByEmail(email: String): Mono<RealKey>
}