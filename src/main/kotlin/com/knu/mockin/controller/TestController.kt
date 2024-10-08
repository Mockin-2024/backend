package com.knu.mockin.controller

import com.knu.mockin.model.dto.kisresponse.TestResponseDto
import com.knu.mockin.model.dto.kisresponse.basicprice.currentprice.KISCurrentPriceResponseDto
import com.knu.mockin.service.TestService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/test")
class TestController (
    private val testService: TestService
) {
    @GetMapping("")
    fun getTest(): Mono<ResponseEntity<TestResponseDto>> {
        return testService.getTest().map {
            dto -> ResponseEntity.ok(dto)
        }
    }
}