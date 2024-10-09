package com.knu.mockin.controller

import com.knu.mockin.model.dto.kisresponse.trading.KISBalanceResponseDto
import com.knu.mockin.model.dto.kisresponse.trading.KISNCCSResponseDto
import com.knu.mockin.model.dto.kisresponse.trading.KISOrderResponseDto
import com.knu.mockin.model.dto.kisresponse.trading.KISPsAmountResponseDto
import com.knu.mockin.service.TradingService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/trading")
class TradingController(
    private val tradingService: TradingService,
) {
    @PostMapping("/order")
    suspend fun order(): ResponseEntity<KISOrderResponseDto>{
        val result = tradingService.postOrder()

        return ResponseEntity.ok(result)
    }

    @GetMapping("/nccs")
    suspend fun getNCCS(): ResponseEntity<KISNCCSResponseDto>{
        val result = tradingService.getNCCS()

        return ResponseEntity.ok(result)
    }

    @GetMapping("/balance")
    suspend fun inquireBalance(): ResponseEntity<KISBalanceResponseDto> {
        val result = tradingService.getBalance()

        return ResponseEntity.ok(result)
    }

    @GetMapping("/psamount")
    suspend fun getPsAmount(): ResponseEntity<KISPsAmountResponseDto> {
        val result = tradingService.getPsAmount()

        return ResponseEntity.ok(result)
    }
}