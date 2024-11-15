package com.knu.mockin.model.dto.response

import com.knu.mockin.model.dto.request.favorite.FavoriteRequestDto

data class FavoriteListDto (
    val output1: List<FavoriteRequestDto>
)