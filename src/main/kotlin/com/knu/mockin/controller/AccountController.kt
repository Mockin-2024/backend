package com.knu.mockin.controller


import com.knu.mockin.model.dto.request.account.KeyPairRequestDto
import com.knu.mockin.model.dto.request.account.UserAccountNumberRequestDto
import com.knu.mockin.model.dto.response.AccessTokenAPIResponseDto
import com.knu.mockin.model.dto.response.ApprovalKeyResponseDto
import com.knu.mockin.model.dto.response.SimpleMessageResponseDto
import com.knu.mockin.service.AccountService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/account")
class AccountController(
    private val accountService: AccountService,
) {

    @PatchMapping("/user")
    suspend fun patchUser(
        @RequestBody userAccountNumberRequestDto: UserAccountNumberRequestDto,
        authentication: Authentication
    ): ResponseEntity<SimpleMessageResponseDto> {
        val result = accountService.patchUser(userAccountNumberRequestDto, authentication.name)
        return ResponseEntity.ok(result)
    }

    @PostMapping("/mock-key")
    suspend fun postMockKeyPair(
        @RequestBody keyPairRequestDto: KeyPairRequestDto,
        authentication: Authentication
    ): ResponseEntity<SimpleMessageResponseDto> {
        val result = accountService.postMockKeyPair(keyPairRequestDto, authentication.name)
        return ResponseEntity.ok(result)
    }

    @PostMapping("/real-key")
    suspend fun postRealKeyPair(
        @RequestBody keyPairRequestDto: KeyPairRequestDto,
        authentication: Authentication
    ): ResponseEntity<SimpleMessageResponseDto> {
        val result = accountService.postRealKeyPair(keyPairRequestDto, authentication.name)
        return ResponseEntity.ok(result)
    }

    @PostMapping("/mock-approval-key")
    suspend fun getMockApprovalKey(
        authentication: Authentication
    ): ResponseEntity<ApprovalKeyResponseDto> {
        val result = accountService.getMockApprovalKey(authentication.name)

        return ResponseEntity.ok(result)
    }

    @PostMapping("/real-approval-key")
    suspend fun getRealApprovalKey(
        authentication: Authentication
    ): ResponseEntity<ApprovalKeyResponseDto> {
        val result = accountService.getRealApprovalKey(authentication.name)

        return ResponseEntity.ok(result)
    }

    @PostMapping("/mock-token")
    suspend fun getMockAccessToken(
        authentication: Authentication
    ): ResponseEntity<AccessTokenAPIResponseDto> {
        val result = accountService.getMockAccessToken(authentication.name)

        return ResponseEntity.ok(result)
    }

    @PostMapping("/real-token")
    suspend fun getRealAccessToken(
        authentication: Authentication
    ): ResponseEntity<AccessTokenAPIResponseDto> {
        val result = accountService.getRealAccessToken(authentication.name)

        return ResponseEntity.ok(result)
    }
}