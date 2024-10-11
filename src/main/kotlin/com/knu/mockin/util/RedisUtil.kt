package com.knu.mockin.util

import org.springframework.cache.annotation.Cacheable
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.time.Duration

@Component
object RedisUtil{
    private lateinit var  redisTemplate: RedisTemplate<String, String>
    private val expiration = Duration.ofDays(1)

    fun init(template: RedisTemplate<String, String>) {
        redisTemplate = template
    }

    fun saveToken(email: String, token: String) {
        redisTemplate.opsForValue().set(email, token, expiration)
    }

    fun getToken(email: String): String? {
        return redisTemplate.opsForValue().get(email)
    }

    fun removeToken(email: String) {
        redisTemplate.delete(email)
    }
}