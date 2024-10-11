package com.knu.mockin.config

import com.knu.mockin.util.RedisUtil
import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.core.RedisTemplate

@Configuration
class RedisUtilConfig(
    private val redisTemplate: RedisTemplate<String, String>
) {
    @PostConstruct
    fun initialize(){
        RedisUtil.init(redisTemplate)
    }
}