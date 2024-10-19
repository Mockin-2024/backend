package com.knu.mockin.controller


import com.knu.mockin.model.dto.request.account.*
import com.knu.mockin.model.dto.response.AccessTokenAPIResponseDto
import com.knu.mockin.model.dto.response.ApprovalKeyResponseDto
import com.knu.mockin.model.dto.response.SimpleMessageResponseDto
import com.knu.mockin.security.JwtSupport
import com.knu.mockin.service.AccountService
import com.knu.mockin.service.EmailService
import com.knu.mockin.service.UserService
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.security.Principal

@RestController
@RequestMapping("/account")
class AccountController(
    private val accountService: AccountService,
) {

    @GetMapping("test")
    suspend fun test(): String {
        return "성공"
    }

    @PatchMapping("/user")
    suspend fun patchUser(
        @RequestBody userAccountNumberRequestDto: UserAccountNumberRequestDto
    ): ResponseEntity<SimpleMessageResponseDto> {
        val result = accountService.patchUser(userAccountNumberRequestDto)
        return ResponseEntity.ok(result)
    }

    @PostMapping("/mock-key")
    suspend fun postMockKeyPair(
        @RequestBody keyPairRequestDto: KeyPairRequestDto
    ): ResponseEntity<SimpleMessageResponseDto> {
        val result = accountService.postMockKeyPair(keyPairRequestDto)
        return ResponseEntity.ok(result)
    }

    @PostMapping("/real-key")
    suspend fun postRealKeyPair(
        @RequestBody keyPairRequestDto: KeyPairRequestDto
    ): ResponseEntity<SimpleMessageResponseDto> {
        val result = accountService.postRealKeyPair(keyPairRequestDto)
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