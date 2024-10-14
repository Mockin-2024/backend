package com.knu.mockin.controller

import com.knu.mockin.model.dto.kisresponse.basic.KISCurrentPriceResponseDto
import com.knu.mockin.model.dto.kisresponse.basic.KISDailyChartPriceResponseDto
import com.knu.mockin.model.dto.kisresponse.basic.KISSearchResponseDto
import com.knu.mockin.model.dto.kisresponse.basic.KISTermPriceResponseDto
import com.knu.mockin.model.dto.request.basic.CurrentPriceRequestParameterDto
import com.knu.mockin.model.dto.request.basic.DailyChartPriceRequestParameterDto
import com.knu.mockin.model.dto.request.basic.SearchRequestParameterDto
import com.knu.mockin.model.dto.request.basic.TermPriceRequestParameterDto
import com.knu.mockin.service.BasicService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/basic")
class BasicController (
        private val basicService : BasicService,
) {

    @GetMapping("/current")
    suspend fun getCurrentPrice(
            @ModelAttribute currentPriceRequestParameterDto: CurrentPriceRequestParameterDto
    ): ResponseEntity<KISCurrentPriceResponseDto> {
        val result = basicService.getCurrentPrice(currentPriceRequestParameterDto)

        return ResponseEntity.ok(result)
    }

    @GetMapping("/term")
    suspend fun getTermPrice(
            @ModelAttribute termPriceRequestParameterDto: TermPriceRequestParameterDto
    ): ResponseEntity<KISTermPriceResponseDto> {
        val result = basicService.getTermPrice(termPriceRequestParameterDto)

        return ResponseEntity.ok(result)
    }

    @GetMapping("/daily-chart-price")
    suspend fun getDailyChartPrice(
            @ModelAttribute dailyChartPriceRequestParameterDto: DailyChartPriceRequestParameterDto
    ): ResponseEntity<KISDailyChartPriceResponseDto> {
        val result = basicService.getDailyChartPrice(dailyChartPriceRequestParameterDto)

        return ResponseEntity.ok(result)
    }

    @GetMapping("/search")
    suspend fun getSearch(
            @ModelAttribute searchRequestParameterDto: SearchRequestParameterDto
    ): ResponseEntity<KISSearchResponseDto> {
        val result = basicService.getSearch(searchRequestParameterDto)

        return ResponseEntity.ok(result)
    }
}