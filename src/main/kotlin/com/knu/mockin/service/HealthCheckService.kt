package com.knu.mockin.service

import com.knu.mockin.model.dto.response.HealthCheckResponseDto
import org.springframework.stereotype.Service

@Service
class HealthCheckService {
    fun healthCheck(): HealthCheckResponseDto {
        return HealthCheckResponseDto(message = "정상 동작")
    }
}