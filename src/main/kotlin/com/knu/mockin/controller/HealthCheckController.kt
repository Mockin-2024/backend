package com.knu.mockin.controller

import com.knu.mockin.model.dto.response.HealthCheckResponseDto
import com.knu.mockin.service.HealthCheckService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/health")
class HealthCheckController(
    private val healthCheckService: HealthCheckService
) {
    @GetMapping()
    fun healthCheck(): ResponseEntity<HealthCheckResponseDto>{
        return ResponseEntity.ok(healthCheckService.healthCheck())
    }
}