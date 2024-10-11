package com.knu.mockin.controller

import com.knu.mockin.model.dto.kisresponse.basic.KISCountriesHolidayResponseDto
import com.knu.mockin.model.dto.request.basic.CountriesHolidayRequestParameterDto
import com.knu.mockin.service.BasicRealService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/basic")
class BasicRealController (
        private val basicRealService: BasicRealService
) {

    @GetMapping("/countries-holiday")
    suspend fun getCountriesHoliday(
            @RequestBody countriesHolidayRequestParameterDto: CountriesHolidayRequestParameterDto
    ): ResponseEntity<KISCountriesHolidayResponseDto> {
        val result = basicRealService.getCountriesHoliday(countriesHolidayRequestParameterDto)

        return ResponseEntity.ok(result)
    }

}