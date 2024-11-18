package com.knu.mockin.controller

import com.knu.mockin.model.dto.request.favorite.FavoriteDto
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
        @RequestBody favoriteDto: FavoriteDto,
        authentication: Authentication
    ): ResponseEntity<SimpleMessageResponseDto> {
        val result = favoriteService.addFavorite(favoriteDto, authentication.name)

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
        @RequestBody favoriteDto: FavoriteDto,
        authentication: Authentication
    ): ResponseEntity<SimpleMessageResponseDto> {
        val result = favoriteService.deleteFavorite(favoriteDto, authentication.name)

        return ResponseEntity.ok(result)
    }


}