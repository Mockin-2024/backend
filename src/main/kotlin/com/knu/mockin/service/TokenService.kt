package com.knu.mockin.service

import org.springframework.cache.annotation.Cacheable
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class TokenService(
        private val redisTemplate: RedisTemplate<String, String>
) {

    private val expiration = Duration.ofDays(1)

    fun saveToken(email: String, token: String) {
        redisTemplate.opsForValue().set(email, token, expiration)
    }

    @Cacheable("token")
    fun getToken(email: String): String? {
        return redisTemplate.opsForValue().get(email)
    }

    fun removeToken(email: String) {
        redisTemplate.delete(email)
    }
}