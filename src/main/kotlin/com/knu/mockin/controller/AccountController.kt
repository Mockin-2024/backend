package com.knu.mockin.controller

import com.knu.mockin.model.dto.response.AccessTokenAPIResponseDto
import com.knu.mockin.model.dto.response.ApprovalKeyResponseDto
import com.knu.mockin.service.AccountService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/account")
class AccountController(
    private val accountService: AccountService
) {

    private val log = LoggerFactory.getLogger(AccountController::class.java)
    @PostMapping("")
    fun getApprovalKey(): Mono<ResponseEntity<ApprovalKeyResponseDto>> {
        return accountService.getApprovalKey()
    }

    @PostMapping("/token")
    fun getAccessToken(): Mono<ResponseEntity<AccessTokenAPIResponseDto>> {
        return accountService.getAccessToken()
    }
}