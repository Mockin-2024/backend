package com.knu.mockin.controller

import com.knu.mockin.model.dto.request.favorite.FavoriteRequestDto
import com.knu.mockin.model.dto.response.FavoriteListDto
import com.knu.mockin.model.dto.response.SimpleMessageResponseDto
import com.knu.mockin.service.FavoriteService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("favorite")
class FavoriteController(
    private val favoriteService: FavoriteService,
) {

    @PostMapping("add")
    suspend fun addFavorite(
        @RequestBody favoriteRequestDto: FavoriteRequestDto,
        authentication: Authentication
    ): ResponseEntity<SimpleMessageResponseDto> {
        val result = favoriteService.addFavorite(favoriteRequestDto, authentication.name)

        return ResponseEntity.ok(result)
    }

    @GetMapping("read")
    suspend fun readAllFavorite(
        authentication: Authentication
    ): ResponseEntity<FavoriteListDto> {
        val result = favoriteService.readAllFavorite(authentication.name)

        return ResponseEntity.ok(result)
    }

    @DeleteMapping("delete")
    suspend fun deleteFavorite(
        @RequestBody favoriteRequestDto: FavoriteRequestDto,
        authentication: Authentication
    ): ResponseEntity<SimpleMessageResponseDto> {
        val result = favoriteService.deleteFavorite(favoriteRequestDto, authentication.name)

        return ResponseEntity.ok(result)
    }


}