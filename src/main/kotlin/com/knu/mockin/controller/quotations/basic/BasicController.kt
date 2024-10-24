package com.knu.mockin.controller.quotations.basic

import com.knu.mockin.model.dto.kisresponse.quotations.basic.mock.KISCurrentPriceResponseDto
import com.knu.mockin.model.dto.kisresponse.quotations.basic.mock.KISDailyChartPriceResponseDto
import com.knu.mockin.model.dto.kisresponse.quotations.basic.mock.KISSearchResponseDto
import com.knu.mockin.model.dto.kisresponse.quotations.basic.mock.KISTermPriceResponseDto
import com.knu.mockin.model.dto.request.quotations.basic.mock.CurrentPriceRequestParameterDto
import com.knu.mockin.model.dto.request.quotations.basic.mock.DailyChartPriceRequestParameterDto
import com.knu.mockin.model.dto.request.quotations.basic.mock.SearchRequestParameterDto
import com.knu.mockin.model.dto.request.quotations.basic.mock.TermPriceRequestParameterDto
import com.knu.mockin.service.quotations.basic.mock.BasicService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/quotations/basic")
class BasicController (
    private val basicService : BasicService,
) {

    @GetMapping("/current")
    suspend fun getCurrentPrice(
            @ModelAttribute currentPriceRequestParameterDto: CurrentPriceRequestParameterDto,
            authentication: Authentication
    ): ResponseEntity<KISCurrentPriceResponseDto> {
        val result = basicService.getCurrentPrice(currentPriceRequestParameterDto, authentication.name)

        return ResponseEntity.ok(result)
    }

    @GetMapping("/term")
    suspend fun getTermPrice(
            @ModelAttribute termPriceRequestParameterDto: TermPriceRequestParameterDto,
            authentication: Authentication
    ): ResponseEntity<KISTermPriceResponseDto> {
        val result = basicService.getTermPrice(termPriceRequestParameterDto, authentication.name)

        return ResponseEntity.ok(result)
    }

    @GetMapping("/daily-chart-price")
    suspend fun getDailyChartPrice(
            @ModelAttribute dailyChartPriceRequestParameterDto: DailyChartPriceRequestParameterDto,
            authentication: Authentication
    ): ResponseEntity<KISDailyChartPriceResponseDto> {
        val result = basicService.getDailyChartPrice(dailyChartPriceRequestParameterDto, authentication.name)

        return ResponseEntity.ok(result)
    }

    @GetMapping("/search")
    suspend fun getSearch(
            @ModelAttribute searchRequestParameterDto: SearchRequestParameterDto,
            authentication: Authentication
    ): ResponseEntity<KISSearchResponseDto> {
        val result = basicService.getSearch(searchRequestParameterDto, authentication.name)

        return ResponseEntity.ok(result)
    }
}