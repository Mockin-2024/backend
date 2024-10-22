package com.knu.mockin.controller

import com.knu.mockin.model.dto.kisresponse.basic.*
import com.knu.mockin.model.dto.request.basic.*
import com.knu.mockin.service.BasicRealService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
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

    @GetMapping("/index-chart-price")
    suspend fun getIndexChartPrice(
            @ModelAttribute indexChartPriceRequestParameterDto: IndexChartPriceRequestParameterDto
    ): ResponseEntity<KISIndexChartPriceResponseDto> {
        val result = basicRealService.getIndexChartPrice(indexChartPriceRequestParameterDto)

        return ResponseEntity.ok(result)
    }


    @GetMapping("/search-info")
    suspend fun getSearchInfo(
        @ModelAttribute searchInfoRequestParameterDto: SearchInfoRequestParameterDto
    ): ResponseEntity<KISSearchInfoResponseDto> {
        val result = basicRealService.getSearchInfo(searchInfoRequestParameterDto)

        return ResponseEntity.ok(result)
    }


    @GetMapping("/news-title")
    suspend fun getNewsTitle(
        @ModelAttribute newsTitleRequestParameterDto: NewsTitleRequestParameterDto
    ): ResponseEntity<KISNewsTitleResponseDto> {
        val result = basicRealService.getNewsTitle(newsTitleRequestParameterDto)

        return ResponseEntity.ok(result)
    }
}