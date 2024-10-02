package com.knu.mockin.controller

import com.knu.mockin.model.dto.kisheader.request.KISOverSeaRequestHeaderDto
import com.knu.mockin.model.dto.kisrequest.oauth.KISTokenRequestDto
import com.knu.mockin.model.dto.kisrequest.order.KISOrderRequestDto
import com.knu.mockin.model.dto.kisresponse.order.KISOverSeaResponseDto
import com.knu.mockin.model.enum.ExchangeCode
import com.knu.mockin.model.enum.TradeId
import com.knu.mockin.service.AccountService
import com.knu.mockin.service.OrderService
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/trading")
class OrderController(
    private val orderService: OrderService,
) {
    @PostMapping("/order")
    fun order(): Mono<ResponseEntity<KISOverSeaResponseDto>>{
        return orderService.order().map {
            dto -> ResponseEntity.ok(dto)
        }
    }
}