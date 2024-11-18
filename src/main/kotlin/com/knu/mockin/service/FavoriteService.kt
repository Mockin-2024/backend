package com.knu.mockin.service

import com.knu.mockin.model.dto.request.favorite.FavoriteDto
import com.knu.mockin.model.dto.response.FavoriteListDto
import com.knu.mockin.model.dto.response.SimpleMessageResponseDto
import com.knu.mockin.model.entity.asDomain
import com.knu.mockin.repository.FavoriteRepository
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service


@Service
class FavoriteService (
    private val favoriteRepository: FavoriteRepository,
) {

    suspend fun selectFavorite(
        favoriteDto: FavoriteDto,
        email: String
    ): SimpleMessageResponseDto {

        val favorite = favoriteDto.asDomain(email)
        val result = favoriteRepository.readByEmailAndSymb(favorite).awaitSingleOrNull()
        if (result == null) {
            favoriteRepository.save(favorite).awaitSingleOrNull()

            return SimpleMessageResponseDto("Add Complete")
        } else {
            favoriteRepository.deleteByEmailAndExcdAndSymb(favorite).awaitSingleOrNull()

            return SimpleMessageResponseDto("Delete Complete")
        }
    }

    suspend fun readAllFavorite(
        email: String
    ): FavoriteListDto {
        val resultList = favoriteRepository.readByEmail(email).collectList().awaitSingle()

        if (resultList.isEmpty()) {
            return FavoriteListDto(emptyList())
        } else {
            val result = resultList.map {
                favorite ->
                FavoriteDto(
                    excd = favorite.excd,
                    symb = favorite.symb,
                )
            }
            return FavoriteListDto(result)
        }
    }

}