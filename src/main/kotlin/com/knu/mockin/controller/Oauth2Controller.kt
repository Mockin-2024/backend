package com.knu.mockin.controller

import com.knu.mockin.model.dto.response.AccessTokenAPIResponseDto
import com.knu.mockin.model.dto.response.ApprovalKeyResponseDto
import com.knu.mockin.service.Oauth2Service
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/oauth2")
class Oauth2Controller(
    val oauth2Service: Oauth2Service
) {
    @PostMapping("/mock-approval-key")
    suspend fun getMockApprovalKey(
        authentication: Authentication
    ): ResponseEntity<ApprovalKeyResponseDto> {
        val result = oauth2Service.getMockApprovalKey(authentication.name)

        return ResponseEntity.ok(result)
    }

    @PostMapping("/real-approval-key")
    suspend fun getRealApprovalKey(
        authentication: Authentication
    ): ResponseEntity<ApprovalKeyResponseDto> {
        val result = oauth2Service.getRealApprovalKey(authentication.name)

        return ResponseEntity.ok(result)
    }

    @PostMapping("/mock-token")
    suspend fun getMockAccessToken(
        authentication: Authentication
    ): ResponseEntity<AccessTokenAPIResponseDto> {
        val result = oauth2Service.getMockAccessToken(authentication.name)

        return ResponseEntity.ok(result)
    }

    @PostMapping("/real-token")
    suspend fun getRealAccessToken(
        authentication: Authentication
    ): ResponseEntity<AccessTokenAPIResponseDto> {
        val result = oauth2Service.getRealAccessToken(authentication.name)

        return ResponseEntity.ok(result)
    }
}