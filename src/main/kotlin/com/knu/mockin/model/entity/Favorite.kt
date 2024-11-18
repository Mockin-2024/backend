package com.knu.mockin.model.entity

import com.knu.mockin.model.dto.request.favorite.FavoriteDto
import org.springframework.data.relational.core.mapping.Table

@Table("favorite")
data class Favorite (
    val email: String,
    val excd: String,
    val symb: String
)

fun FavoriteDto.asDomain(email: String): Favorite {
    return Favorite(
        email = email,
        excd = this.excd,
        symb = this.symb
    )
}