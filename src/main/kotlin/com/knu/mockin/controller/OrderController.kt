package com.knu.mockin.controller

import com.knu.mockin.model.dto.kisresponse.order.KISOverSeaResponseDto
import com.knu.mockin.service.OrderService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/trading")
class OrderController(
    private val orderService: OrderService,
) {
    @PostMapping("/order")
    suspend fun order(): ResponseEntity<KISOverSeaResponseDto>{
        return ResponseEntity.ok(orderService.order())
    }
}