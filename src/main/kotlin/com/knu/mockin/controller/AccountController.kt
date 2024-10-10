package com.knu.mockin.controller


import com.knu.mockin.model.dto.request.account.AccountRequestDto
import com.knu.mockin.model.dto.response.AccessTokenAPIResponseDto
import com.knu.mockin.model.dto.response.ApprovalKeyResponseDto
import com.knu.mockin.service.AccountService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/account")
class AccountController(
    private val accountService: AccountService
) {
    @PostMapping("/approval-key")
    suspend fun getApprovalKey(
        @RequestBody accountRequestDto: AccountRequestDto
    ): ResponseEntity<ApprovalKeyResponseDto> {
        val result = accountService.getApprovalKey(accountRequestDto)

        return ResponseEntity.ok(result)
    }

    @PostMapping("/token")
    suspend fun getAccessToken(
        @RequestBody accountRequestDto: AccountRequestDto
    ): ResponseEntity<AccessTokenAPIResponseDto> {
        val result = accountService.getAccessToken(accountRequestDto)

        return ResponseEntity.ok(result)
    }
}