package com.knu.mockin.controller


import com.knu.mockin.model.dto.request.account.AccountRequestDto
import com.knu.mockin.model.dto.request.account.KeyPairRequestDto
import com.knu.mockin.model.dto.request.account.UserAccountNumberRequestDto
import com.knu.mockin.model.dto.request.account.UserRequestDto
import com.knu.mockin.model.dto.response.AccessTokenAPIResponseDto
import com.knu.mockin.model.dto.response.ApprovalKeyResponseDto
import com.knu.mockin.model.dto.response.SimpleMessageResponseDto
import com.knu.mockin.service.AccountService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/account")
class AccountController(
    private val accountService: AccountService
) {
    @PostMapping("/user")
    suspend fun postUser(
        @RequestBody userRequestDto: UserRequestDto
    ): ResponseEntity<SimpleMessageResponseDto> {
        val result = accountService.postUser(userRequestDto)
        return ResponseEntity.ok(result)
    }

    @PatchMapping("/user")
    suspend fun patchUser(
        @RequestBody userAccountNumberRequestDto: UserAccountNumberRequestDto
    ): ResponseEntity<SimpleMessageResponseDto> {
        val result = accountService.patchUser(userAccountNumberRequestDto)
        return ResponseEntity.ok(result)
    }

    @PostMapping("/mock-key")
    suspend fun postMockKey(
        @RequestBody keyPairRequestDto: KeyPairRequestDto
    ): ResponseEntity<SimpleMessageResponseDto> {
        val result = accountService.postMockKey(keyPairRequestDto)
        return ResponseEntity.ok(result)
    }

    @PostMapping("/real-key")
    suspend fun postRealKey(
        @RequestBody keyPairRequestDto: KeyPairRequestDto
    ): ResponseEntity<SimpleMessageResponseDto> {
        val result = accountService.postRealKey(keyPairRequestDto)
        return ResponseEntity.ok(result)
    }

    @PostMapping("/mock-approval-key")
    suspend fun getMockApprovalKey(
        @RequestBody accountRequestDto: AccountRequestDto
    ): ResponseEntity<ApprovalKeyResponseDto> {
        val result = accountService.getMockApprovalKey(accountRequestDto)

        return ResponseEntity.ok(result)
    }

    @PostMapping("/real-approval-key")
    suspend fun getRealApprovalKey(
            @RequestBody accountRequestDto: AccountRequestDto
    ): ResponseEntity<ApprovalKeyResponseDto> {
        val result = accountService.getRealApprovalKey(accountRequestDto)

        return ResponseEntity.ok(result)
    }

    @PostMapping("/mock-token")
    suspend fun getMockAccessToken(
        @RequestBody accountRequestDto: AccountRequestDto
    ): ResponseEntity<AccessTokenAPIResponseDto> {
        val result = accountService.getMockAccessToken(accountRequestDto)

        return ResponseEntity.ok(result)
    }

    @PostMapping("/real-token")
    suspend fun getRealMockAccessToken(
            @RequestBody accountRequestDto: AccountRequestDto
    ): ResponseEntity<AccessTokenAPIResponseDto> {
        val result = accountService.getRealAccessToken(accountRequestDto)

        return ResponseEntity.ok(result)
    }
}