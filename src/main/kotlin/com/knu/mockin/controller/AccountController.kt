package com.knu.mockin.controller


import com.knu.mockin.model.dto.response.AccessTokenAPIResponseDto
import com.knu.mockin.model.dto.response.ApprovalKeyResponseDto
import com.knu.mockin.service.AccountService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/account")
class AccountController(
    private val accountService: AccountService
) {
    @PostMapping("/approval-key")
    suspend fun getApprovalKey(): ResponseEntity<ApprovalKeyResponseDto> {
        val result = accountService.getApprovalKey()

        return ResponseEntity.ok(result)
    }

    @PostMapping("/token")
    suspend fun getAccessToken(): ResponseEntity<AccessTokenAPIResponseDto> {
        val result = accountService.getAccessToken()

        return ResponseEntity.ok(result)
    }
}