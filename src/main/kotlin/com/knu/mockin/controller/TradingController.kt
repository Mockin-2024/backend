package com.knu.mockin.controller

import com.knu.mockin.model.dto.kisresponse.trading.*
import com.knu.mockin.model.dto.request.trading.*
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

    @PostMapping("/order-reverse")
    suspend fun postOrderReverse(
        @RequestBody orderReverseRequestBodyDto: OrderReverseRequestBodyDto
    ): ResponseEntity<KISOrderReverseResponseDto> {
        val result = tradingService.postOrderReverse(orderReverseRequestBodyDto)

        return ResponseEntity.ok(result)
    }

    @GetMapping("/nccs")
    suspend fun getNCCS(
        @ModelAttribute nccsRequestParameterDto: NCCSRequestParameterDto
    ): ResponseEntity<KISNCCSResponseDto>{
        val result = tradingService.getNCCS(nccsRequestParameterDto)

        return ResponseEntity.ok(result)
    }

    @GetMapping("/balance")
    suspend fun getBalance(
        @ModelAttribute balanceRequestParameterDto: BalanceRequestParameterDto
    ): ResponseEntity<KISBalanceResponseDto> {
        val result = tradingService.getBalance(balanceRequestParameterDto)

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

    @GetMapping("/ccnl")
    suspend fun getCCNL(
        @ModelAttribute ccnlRequestParameterDto: CCNLRequestParameterDto
    ): ResponseEntity<KISCCNLResponseDto> {
        val result = tradingService.getCCNL(ccnlRequestParameterDto)

        return ResponseEntity.ok(result)
    }
}