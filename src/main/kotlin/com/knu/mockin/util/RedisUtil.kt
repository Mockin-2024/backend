package com.knu.mockin.util

import com.knu.mockin.logging.utils.LogUtil.toJson
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration
import java.util.concurrent.TimeUnit

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
                      unit: TimeUnit) {
        return redisTemplate.opsForValue().set(email, token, timeout, unit)
    }


    fun removeToken(email: String) {
        redisTemplate.delete(email)
    }


    fun <T> setData(key: String, value: T, expiration: Long) {
        redisTemplate.opsForValue().set(key, toJson(value), expiration, TimeUnit.MINUTES)
    }

    fun getData(key: String): String? {
        return redisTemplate.opsForValue().get(key)
    }

    fun deleteData(key: String): Boolean {
        return redisTemplate.delete(key)
    }

    fun deleteByPattern(pattern: String) {
        val keys = redisTemplate.keys(pattern)
        keys.forEach { key ->
            redisTemplate.delete(key)
        }
    }
}