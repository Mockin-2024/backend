package com.knu.mockin.model.dto.request.favorite

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonProperty

data class FavoriteRequestDto (
    val excd: String = "",
    val symb: String = "",
)