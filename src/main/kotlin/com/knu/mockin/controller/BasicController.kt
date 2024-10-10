package com.knu.mockin.controller

import com.knu.mockin.model.dto.kisresponse.basic.KISCurrentPriceResponseDto
import com.knu.mockin.model.dto.kisresponse.basic.KISDailyChartPriceResponseDto
import com.knu.mockin.model.dto.kisresponse.basic.KISTermPriceResponseDto
import com.knu.mockin.service.BasicService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/basic")
class BasicController (
        private val basicService : BasicService,
) {

    @GetMapping("/current")
    suspend fun getCurrentPrice(): ResponseEntity<KISCurrentPriceResponseDto> {
        val result = basicService.getCurrentPrice()

        return ResponseEntity.ok(result)
    }

    @GetMapping("/term")
    suspend fun getTermPrice(): ResponseEntity<KISTermPriceResponseDto> {
        val result = basicService.getTermPrice()

        return ResponseEntity.ok(result)
    }

    @GetMapping("/daily-chart-price")
    suspend fun getDailyChartPrice(): ResponseEntity<KISDailyChartPriceResponseDto> {
        val result = basicService.getDailyChartPrice()

        return ResponseEntity.ok(result)
    }
}