package com.knu.mockin.controller

import com.knu.mockin.model.dto.request.login.EmailCheckRequestDto
import com.knu.mockin.model.dto.request.login.Jwt
import com.knu.mockin.model.dto.request.login.LoginRequestDto
import com.knu.mockin.model.dto.request.login.SignupRequestDto
import com.knu.mockin.model.dto.response.SimpleMessageResponseDto
import com.knu.mockin.service.EmailService
import com.knu.mockin.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class LoginController (
    private val emailService: EmailService,
    private val userService: UserService,
) {

    @PostMapping("/signup")
    suspend fun signup(
        @RequestBody signupRequestDto: SignupRequestDto
    ): ResponseEntity<SimpleMessageResponseDto> {
        val result = userService.createUser(signupRequestDto)

        return ResponseEntity.ok(result)
    }

    @PostMapping("/login")
    suspend fun login(
        @RequestBody loginRequestDto: LoginRequestDto
    ): ResponseEntity<Jwt> {
        val result = userService.loginUser(loginRequestDto)

        return ResponseEntity.ok(result)
    }

    @PostMapping("/check")
    suspend fun checkUser(
        @RequestBody emailCheckRequestDto: EmailCheckRequestDto
    ): ResponseEntity<SimpleMessageResponseDto> {
        val result = emailService.checkAuthNum(emailCheckRequestDto)

        return ResponseEntity.ok(result)
    }


}