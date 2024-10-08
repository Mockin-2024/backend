package com.knu.mockin.controller

import com.knu.mockin.model.dto.kisresponse.basicprice.mock.conditionsearch.KISConditionSearchResponseDto
import com.knu.mockin.model.dto.kisresponse.basicprice.mock.currentprice.KISCurrentPriceResponseDto
import com.knu.mockin.model.dto.kisresponse.basicprice.mock.exchangeprice.KISExchangePriceResponseDto
import com.knu.mockin.model.dto.kisresponse.basicprice.mock.termprice.KISTermPriceResponseDto
import com.knu.mockin.model.dto.kisresponse.basicprice.real.countriesholiday.KISCountriesHolidayResponseDto
import com.knu.mockin.service.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/basic")
class BasicPriceController (
        private val currentPriceService : CurrentPriceService,
        private val termPriceService: TermPriceService,
        private val exchangePriceService: ExchangePriceService,
        private val conditionSearchService: ConditionSearchService,
        private val countriesHolidayService: CountriesHolidayService,
){

    @GetMapping("/current")
    suspend fun getCurrentPrice(): ResponseEntity<KISCurrentPriceResponseDto> {
        val result = currentPriceService.getCurrentPrice()

        return ResponseEntity.ok(result);
    }

    @GetMapping("/term")
    suspend fun getTermPrice(): ResponseEntity<KISTermPriceResponseDto> {
        val result = termPriceService.getTermPrice()

        return ResponseEntity.ok(result);
    }

    @GetMapping("/exchange")
    suspend fun getExchangePrice(): ResponseEntity<KISExchangePriceResponseDto> {
        val result = exchangePriceService.getExchangePrice()

        return ResponseEntity.ok(result);
    }

    @GetMapping("/condition")
    suspend fun getConditionSearch(): ResponseEntity<KISConditionSearchResponseDto> {
        val result = conditionSearchService.getConditionSearch()

        return ResponseEntity.ok(result)
    }

    @GetMapping("/countries-holiday")
    suspend fun getCountriesHoliday() : ResponseEntity<KISCountriesHolidayResponseDto> {
        val result = countriesHolidayService.getCountriesHoliday()

        return ResponseEntity.ok(result)
    }

}