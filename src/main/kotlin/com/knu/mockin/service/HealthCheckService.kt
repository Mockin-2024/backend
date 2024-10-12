package com.knu.mockin.service

import com.knu.mockin.model.dto.response.SimpleMessageResponseDto
import org.springframework.stereotype.Service

@Service
class HealthCheckService {
    fun healthCheck(): SimpleMessageResponseDto {
        return SimpleMessageResponseDto(message = "정상 동작")
    }
}