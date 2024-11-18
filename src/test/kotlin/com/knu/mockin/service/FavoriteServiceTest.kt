package com.knu.mockin.service

import com.knu.mockin.dsl.RestDocsUtils.readJsonFile
import com.knu.mockin.model.dto.request.favorite.FavoriteDto
import com.knu.mockin.model.dto.request.login.EmailRequestDto
import com.knu.mockin.model.dto.response.FavoriteListDto
import com.knu.mockin.model.dto.response.SimpleMessageResponseDto
import com.knu.mockin.model.entity.Favorite
import com.knu.mockin.model.entity.User
import com.knu.mockin.repository.FavoriteRepository
import com.knu.mockin.util.ExtensionUtil.toDto
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class FavoriteServiceTest(
    private val favoriteRepository: FavoriteRepository = mockk<FavoriteRepository>(),
): BehaviorSpec({
    val favoriteService = FavoriteService(
        favoriteRepository = favoriteRepository
    )
    val user = readJsonFile("setting", "user.json") toDto User::class.java
    val favorite = readJsonFile("setting", "favorite.json") toDto Favorite::class.java

    val baseUri = "/favorite"

    Context("add 함수의 경우"){
        val uri = "$baseUri/add"

        Given("적절한 dto가 주어질 때"){
            val bodyDto = readJsonFile(uri, "requestDto.json") toDto FavoriteDto::class.java
            val expectedDto = readJsonFile(uri, "responseDto.json") toDto SimpleMessageResponseDto::class.java

            When("즐겨찾기를 정상적으로 등록한 후"){
                every { favoriteRepository.save(favorite) } returns Mono.just(favorite)

                Then("응답 DTO를 정상적으로 받아야 한다."){
                    val result = favoriteService.addFavorite(bodyDto, user.email)
                    result shouldBe expectedDto
                }
            }
        }
    }

    Context("read 함수의 경우") {
        val uri = "$baseUri/read"

        Given("적절한 dto가 주어질 때") {
            val bodyDto = readJsonFile(uri, "requestDto.json") toDto EmailRequestDto::class.java
            val expectedDto = readJsonFile(uri, "responseDto.json") toDto FavoriteListDto::class.java

            When("즐겨찾기를 정상적으로 읽어온 후") {
                every { favoriteRepository.readByEmail(user.email) } returns Flux.just(favorite)

                Then("응답 DTO를 정상적으로 받아야 한다.") {
                    val result = favoriteService.readAllFavorite(user.email)
                    result shouldBe expectedDto
                }
            }

            When("즐겨찾기가 없을 때") {
                every { favoriteRepository.readByEmail(user.email) } returns Flux.empty()

                Then("빈 리스트를 반환해야 한다.") {
                    val result = favoriteService.readAllFavorite(user.email)
                    result shouldBe FavoriteListDto(emptyList())
                }
            }
        }
    }

    Context("delete 함수의 경우"){
        val uri = "$baseUri/delete"

        Given("적절한 dto가 주어질 때"){
            val bodyDto = readJsonFile(uri, "requestDto.json") toDto FavoriteDto::class.java
            val expectedDto = readJsonFile(uri, "responseDto.json") toDto SimpleMessageResponseDto::class.java

            When("즐겨찾기를 정상적으로 등록한 후"){
                every { favoriteRepository.deleteByEmailAndExcdAndSymb(favorite) } returns Mono.empty()

                Then("응답 DTO를 정상적으로 받아야 한다."){
                    val result = favoriteService.deleteFavorite(bodyDto, user.email)
                    result shouldBe expectedDto
                }
            }
        }
    }

})