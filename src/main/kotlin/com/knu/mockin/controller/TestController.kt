package com.knu.mockin.controller

import com.knu.mockin.service.TokenService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/token")
class TestController(
        private val tokenService: TokenService
) {

    @PostMapping("/save")
    fun saveToken(@RequestParam email: String, @RequestParam token: String) {
        tokenService.saveToken(email, token)
    }

    @GetMapping("/get")
    fun getToken(@RequestParam email: String): ResponseEntity<String> {
        val token = tokenService.getToken(email)
        return if (token != null) {
            ResponseEntity.ok(token)
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Token not found")
        }
    }

    @DeleteMapping("/remove")
    fun removeToken(@RequestParam email: String) {
        tokenService.removeToken(email)
    }
}