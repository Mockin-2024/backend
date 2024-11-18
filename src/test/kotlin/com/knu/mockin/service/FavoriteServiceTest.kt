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

    Context("select 함수의 경우") {
        val uri = "$baseUri/select"

        Given("적절한 dto가 주어질 때") {
            val bodyDto = readJsonFile(uri, "requestDto.json") toDto FavoriteDto::class.java

            When("즐겨찾기가 존재하지 않아 등록할 경우") {
                every { favoriteRepository.readByEmailAndSymb(any()) } returns Mono.empty()
                every { favoriteRepository.save(any()) } returns Mono.just(favorite)

                Then("Add Complete 메시지를 반환해야 한다.") {
                    val result = favoriteService.selectFavorite(bodyDto, user.email)
                    result shouldBe SimpleMessageResponseDto("Add Complete")
                }
            }

            When("즐겨찾기가 이미 존재하여 삭제할 경우") {
                every { favoriteRepository.readByEmailAndSymb(any()) } returns Mono.just(favorite)
                every { favoriteRepository.deleteByEmailAndExcdAndSymb(any()) } returns Mono.empty()

                Then("Delete Complete 메시지를 반환해야 한다.") {
                    val result = favoriteService.selectFavorite(bodyDto, user.email)
                    result shouldBe SimpleMessageResponseDto("Delete Complete")
                }
            }
        }
    }

    Context("read 함수의 경우") {
        val uri = "$baseUri/read"

        Given("적절한 dto가 주어질 때") {
            val bodyDto = readJsonFile(uri, "requestDto.json") toDto EmailRequestDto::class.java
            val expectedDto = readJsonFile(uri, "responseDto.json") toDto FavoriteListDto::class.java

            When("즐겨찾기가 있을 경우") {
                every { favoriteRepository.readByEmail(user.email) } returns Flux.just(favorite)

                Then("즐겨찾기가 담긴 리스트를 반환해야 한다.") {
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

})