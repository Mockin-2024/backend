package com.knu.mockin.controller

import com.knu.mockin.model.dto.kisresponse.basic.mock.KISCurrentPriceResponseDto
import com.knu.mockin.model.dto.kisresponse.basic.real.KISCountriesHolidayResponseDto
import com.knu.mockin.service.BasicRealService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/basics")
class BasicRealController (
        private val basicRealService: BasicRealService,
) {
    @GetMapping("/countries-holiday")
    suspend fun getCountriesHoliday(): ResponseEntity<KISCountriesHolidayResponseDto> {
        val result = basicRealService.getCountriesHoliday()

        return ResponseEntity.ok(result)
    }
}