package com.knu.mockin.repository

import com.knu.mockin.model.entity.Favorite
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface FavoriteRepository : ReactiveCrudRepository<Favorite, String> {

    @Query("insert into favorite (email, excd, symb) " +
            "values (:#{#favorite.email}, :#{#favorite.excd}, :#{#favorite.symb})")
    fun save(favorite: Favorite): Mono<Favorite>


    @Query("DELETE FROM favorite WHERE email = :#{#favorite.email} AND excd = :#{#favorite.excd} AND symb = :#{#favorite.symb}")
    fun deleteByEmailAndExcdAndSymb(favorite: Favorite): Mono<Void>


    fun readByEmail(email: String): Flux<Favorite>


}