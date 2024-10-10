package com.knu.mockin.repository

import com.knu.mockin.model.entity.MockKey
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono

interface MockKeyRepository : ReactiveCrudRepository<MockKey, String> {
    @Query("insert into mock_key (email, app_key, app_secret)" +
            "values (:#{#mockKey.email}, :#{#mockKey.appKey},:#{#mockKey.appSecret})")
    fun save(mockKey: MockKey): Mono<MockKey>
    fun findByEmail(email: String): Mono<MockKey>
}