package com.knu.mockin.util

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration

@Component
object RedisUtil{
    private lateinit var  redisTemplate: RedisTemplate<String, String>

    fun init(template: RedisTemplate<String, String>) {
        redisTemplate = template
    }

    fun saveToken(email: String, token: String, expiration: Duration = Duration.ofDays(1)) {
        redisTemplate.opsForValue().set(email, token, expiration)
    }

    fun getToken(email: String): String? {
        return redisTemplate.opsForValue().get(email)
    }

    fun saveEmailCode(email: String,
                      token: String,
                      timeout: Long,
                      unit: java.util.concurrent.TimeUnit) {
        return redisTemplate.opsForValue().set(email, token, timeout, unit)
    }


    fun removeToken(email: String) {
        redisTemplate.delete(email)
    }
}