package com.knu.mockin.controller.quotations.basic

import com.knu.mockin.model.dto.kisresponse.quotations.basic.mock.*
import com.knu.mockin.model.dto.request.quotations.basic.mock.*
import com.knu.mockin.service.quotations.basic.mock.BasicService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/quotations/basic")
class BasicController (
    private val basicService : BasicService,
) {

    @GetMapping("/price")
    suspend fun getPrice(
        @ModelAttribute priceRequestParameterDto: PriceRequestParameterDto,
        authentication: Authentication
    ): ResponseEntity<KISPriceResponseDto> {
        val result = basicService.getPrice(priceRequestParameterDto, authentication.name)

        return ResponseEntity.ok(result)
    }

    @GetMapping("/daily-price")
    suspend fun getDailyPrice(
            @ModelAttribute dailyPriceRequestParameterDto: DailyPriceRequestParameterDto,
            authentication: Authentication
    ): ResponseEntity<KISDailyPriceResponseDto> {
        val result = basicService.getDailyPrice(dailyPriceRequestParameterDto, authentication.name)

        return ResponseEntity.ok(result)
    }

    @GetMapping("/inquire-daily-chartprice")
    suspend fun getInquireDailyChartPrice(
        @ModelAttribute inquireDailyChartPriceRequestParameterDto: InquireDailyChartPriceRequestParameterDto,
        authentication: Authentication
    ): ResponseEntity<KISInquireDailyChartPriceResponseDto> {
        val result = basicService.getInquireDailyChartPrice(inquireDailyChartPriceRequestParameterDto, authentication.name)

        return ResponseEntity.ok(result)
    }

    @GetMapping("/inquire-search")
    suspend fun getInquireSearch(
        @ModelAttribute inquireSearchRequestParameterDto: InquireSearchRequestParameterDto,
        authentication: Authentication
    ): ResponseEntity<KISInquireSearchResponseDto> {
        val result = basicService.getInquireSearch(inquireSearchRequestParameterDto, authentication.name)

        return ResponseEntity.ok(result)
    }
}