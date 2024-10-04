package com.knu.mockin.controller

import com.knu.mockin.model.dto.kisresponse.KISBasicPrice.currentPrice.KISCurrentPriceResponseDto
import com.knu.mockin.service.CurrentPriceService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/basic")
class BasicPriceController (
        private val currentPriceService : CurrentPriceService,
){

    @GetMapping("/current")
    fun getCurrentPrice(): Mono<ResponseEntity<KISCurrentPriceResponseDto>> {
        return currentPriceService.getCurrentPrice().map {
            dto -> ResponseEntity.ok(dto)
        }
    }
}