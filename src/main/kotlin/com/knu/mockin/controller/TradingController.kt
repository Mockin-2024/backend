package com.knu.mockin.controller

import com.knu.mockin.model.dto.kisresponse.trading.*
import com.knu.mockin.model.dto.request.trading.*
import com.knu.mockin.service.TradingService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
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
        @RequestBody orderRequestBodyDto: OrderRequestBodyDto,
        authentication: Authentication
    ): ResponseEntity<KISOrderResponseDto>{
        val result = tradingService.postOrder(orderRequestBodyDto, authentication.name)

        return ResponseEntity.ok(result)
    }

    @PostMapping("/order-reverse")
    suspend fun postOrderReverse(
        @RequestBody orderReverseRequestBodyDto: OrderReverseRequestBodyDto,
        authentication: Authentication
    ): ResponseEntity<KISOrderReverseResponseDto> {
        val result = tradingService.postOrderReverse(orderReverseRequestBodyDto, authentication.name)

        return ResponseEntity.ok(result)
    }

    @PostMapping("/order-reserve")
    suspend fun postOrderReserve(
        @RequestBody orderReserveRequestBodyDto: OrderReserveRequestBodyDto,
        authentication: Authentication
    ): ResponseEntity<KISOrderReserveResponseDto> {
        val result = tradingService.postOrderReserve(orderReserveRequestBodyDto, authentication.name)

        return ResponseEntity.ok(result)
    }

    @GetMapping("/nccs")
    suspend fun getNCCS(
        @ModelAttribute nccsRequestParameterDto: NCCSRequestParameterDto,
        authentication: Authentication
    ): ResponseEntity<KISNCCSResponseDto>{
        val result = tradingService.getNCCS(nccsRequestParameterDto, authentication.name)

        return ResponseEntity.ok(result)
    }

    @GetMapping("/balance")
    suspend fun getBalance(
        @ModelAttribute balanceRequestParameterDto: BalanceRequestParameterDto,
        authentication: Authentication
    ): ResponseEntity<KISBalanceResponseDto> {
        val result = tradingService.getBalance(balanceRequestParameterDto, authentication.name)

        return ResponseEntity.ok(result)
    }

    @GetMapping("/psamount")
    suspend fun getPsAmount(
        @ModelAttribute psAmountRequestParameterDto: PsAmountRequestParameterDto,
        authentication: Authentication
    ): ResponseEntity<KISPsAmountResponseDto> {
        val result = tradingService.getPsAmount(psAmountRequestParameterDto, authentication.name)

        return ResponseEntity.ok(result)
    }

    @GetMapping("/present-balance")
    suspend fun getPresentBalance(
        @ModelAttribute presentBalanceRequestParameterDto: PresentBalanceRequestParameterDto,
        authentication: Authentication
    ): ResponseEntity<KISPresentBalanceResponseDto> {
        val result = tradingService.getPresentBalance(presentBalanceRequestParameterDto, authentication.name)

        return ResponseEntity.ok(result)
    }

    @GetMapping("/ccnl")
    suspend fun getCCNL(
        @ModelAttribute ccnlRequestParameterDto: CCNLRequestParameterDto,
        authentication: Authentication
    ): ResponseEntity<KISCCNLResponseDto> {
        val result = tradingService.getCCNL(ccnlRequestParameterDto, authentication.name)

        return ResponseEntity.ok(result)
    }
}