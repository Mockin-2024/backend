package com.knu.mockin.controller

import com.knu.mockin.model.dto.kisresponse.basicprice.conditionsearch.KISConditionSearchResponseDto
import com.knu.mockin.model.dto.kisresponse.basicprice.currentprice.KISCurrentPriceResponseDto
import com.knu.mockin.model.dto.kisresponse.basicprice.exchangeprice.KISExchangePriceResponseDto
import com.knu.mockin.model.dto.kisresponse.basicprice.termprice.KISTermPriceResponseDto
import com.knu.mockin.service.ConditionSearchService
import com.knu.mockin.service.CurrentPriceService
import com.knu.mockin.service.ExchangePriceService
import com.knu.mockin.service.TermPriceService
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
){

    @GetMapping("/current")
    fun getCurrentPrice(): Mono<ResponseEntity<KISCurrentPriceResponseDto>> {
        return currentPriceService.getCurrentPrice().map {
            dto -> ResponseEntity.ok(dto)
        }
    }

    @GetMapping("/term")
    fun getTermPrice(): Mono<ResponseEntity<KISTermPriceResponseDto>> {
        return termPriceService.getTermPrice().map {
            dto -> ResponseEntity.ok(dto)
        }
    }

    @GetMapping("/exchange")
    fun getExchangePrice(): Mono<ResponseEntity<KISExchangePriceResponseDto>> {
        return exchangePriceService.getExchangePrice().map {
            dto -> ResponseEntity.ok(dto)
        }
    }

    @GetMapping("/condition")
    fun getConditionSearch(): Mono<ResponseEntity<KISConditionSearchResponseDto>> {
        return conditionSearchService.getConditionSearch().map {
            dto -> ResponseEntity.ok(dto)
        }
    }
}