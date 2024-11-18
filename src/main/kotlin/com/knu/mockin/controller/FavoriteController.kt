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

    @PostMapping("select")
    suspend fun selectFavorite(
        @RequestBody favoriteDto: FavoriteDto,
        authentication: Authentication
    ): ResponseEntity<SimpleMessageResponseDto> {
        val result = favoriteService.selectFavorite(favoriteDto, authentication.name)

        return ResponseEntity.ok(result)
    }

    @GetMapping("read")
    suspend fun readAllFavorite(
        authentication: Authentication
    ): ResponseEntity<FavoriteListDto> {
        val result = favoriteService.readAllFavorite(authentication.name)

        return ResponseEntity.ok(result)
    }


}