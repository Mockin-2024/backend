package com.knu.mockin.controller


import com.knu.mockin.model.dto.request.account.AccountRequestDto
import com.knu.mockin.model.dto.request.account.KeyPairRequestDto
import com.knu.mockin.model.dto.request.account.UserAccountNumberRequestDto
import com.knu.mockin.model.dto.request.account.UserRequestDto
import com.knu.mockin.model.dto.response.AccessTokenAPIResponseDto
import com.knu.mockin.model.dto.response.ApprovalKeyResponseDto
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
    ): ResponseEntity<String> {
        accountService.postUser(userRequestDto)
        return ResponseEntity.ok("등록 완료")
    }

    @PatchMapping("/user")
    suspend fun patchUser(
        @RequestBody userAccountNumberRequestDto: UserAccountNumberRequestDto
    ): ResponseEntity<String> {
        accountService.patchUser(userAccountNumberRequestDto)
        return ResponseEntity.ok("등록 완료")
    }

    @PostMapping("/mock-key")
    suspend fun postMockKey(
        @RequestBody keyPairRequestDto: KeyPairRequestDto
    ): ResponseEntity<String> {
        accountService.postMockKey(keyPairRequestDto)
        return ResponseEntity.ok("등록 완료")
    }

    @PostMapping("/real-key")
    suspend fun postRealKey(
        @RequestBody keyPairRequestDto: KeyPairRequestDto
    ): ResponseEntity<String> {
        accountService.postRealKey(keyPairRequestDto)
        return ResponseEntity.ok("등록 완료")
    }

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