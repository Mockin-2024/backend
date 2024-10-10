package com.knu.mockin.controller

import com.knu.mockin.model.dto.kisresponse.trading.*
import com.knu.mockin.model.dto.request.trading.OrderRequestBodyDto
import com.knu.mockin.model.dto.request.trading.PresentBalanceRequestParameterDto
import com.knu.mockin.model.dto.request.trading.PsAmountRequestParameterDto
import com.knu.mockin.service.TradingService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/trading")
class TradingController(
    private val tradingService: TradingService,
) {
    @PostMapping("/order")
    suspend fun postOrder(
        @RequestBody orderRequestBodyDto: OrderRequestBodyDto
    ): ResponseEntity<KISOrderResponseDto>{
        val result = tradingService.postOrder(orderRequestBodyDto)

        return ResponseEntity.ok(result)
    }

    @GetMapping("/nccs")
    suspend fun getNCCS(): ResponseEntity<KISNCCSResponseDto>{
        val result = tradingService.getNCCS()

        return ResponseEntity.ok(result)
    }

    @GetMapping("/balance")
    suspend fun getBalance(): ResponseEntity<KISBalanceResponseDto> {
        val result = tradingService.getBalance()

        return ResponseEntity.ok(result)
    }

    @GetMapping("/psamount")
    suspend fun getPsAmount(
        @ModelAttribute psAmountRequestParameterDto: PsAmountRequestParameterDto
    ): ResponseEntity<KISPsAmountResponseDto> {
        val result = tradingService.getPsAmount(psAmountRequestParameterDto)

        return ResponseEntity.ok(result)
    }

    @GetMapping("/present-balance")
    suspend fun getPresentBalance(
        @ModelAttribute presentBalanceRequestParameterDto: PresentBalanceRequestParameterDto
    ): ResponseEntity<KISPresentBalanceResponseDto> {
        val result = tradingService.getPresentBalance(presentBalanceRequestParameterDto)

        return ResponseEntity.ok(result)
    }
}