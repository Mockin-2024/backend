package com.knu.mockin.controller

import com.knu.mockin.model.dto.kisresponse.basic.KISCountriesHolidayResponseDto
import com.knu.mockin.model.dto.kisresponse.basic.KISItemChartPriceResponseDto
import com.knu.mockin.model.dto.kisresponse.basic.KISPriceDetailResponseDto
import com.knu.mockin.model.dto.request.basic.CountriesHolidayRequestParameterDto
import com.knu.mockin.model.dto.request.basic.ItemChartPriceRequestParameterDto
import com.knu.mockin.model.dto.request.basic.PriceDetailRequestParameterDto
import com.knu.mockin.service.BasicRealService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
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
            @ModelAttribute countriesHolidayRequestParameterDto: CountriesHolidayRequestParameterDto
    ): ResponseEntity<KISCountriesHolidayResponseDto> {
        val result = basicRealService.getCountriesHoliday(countriesHolidayRequestParameterDto)

        return ResponseEntity.ok(result)
    }

    @GetMapping("/price-detail")
    suspend fun getPriceDetail(
            @ModelAttribute priceDetailRequestParameterDto: PriceDetailRequestParameterDto
    ): ResponseEntity<KISPriceDetailResponseDto> {
        val result = basicRealService.getPriceDetail(priceDetailRequestParameterDto)

        return ResponseEntity.ok(result)
    }

    @GetMapping("/item-chart-price")
    suspend fun getItemChartPrice(
            @ModelAttribute itemChartPriceRequestParameterDto: ItemChartPriceRequestParameterDto
    ): ResponseEntity<KISItemChartPriceResponseDto> {
        val result = basicRealService.getItemChartPrice(itemChartPriceRequestParameterDto)

        return ResponseEntity.ok(result)
    }

}