package com.knu.mockin.service

import com.knu.mockin.dsl.RestDocsUtils
import com.knu.mockin.model.dto.response.SimpleMessageResponseDto
import com.knu.mockin.util.ExtensionUtil.toDto
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class HealthCheckTest: BehaviorSpec({
    val healthCheckService = HealthCheckService()

    Context("healthCheck 함수의 경우"){
        val uri = "/health"

        Given("요청 dto에 상관없이"){
            val expectedDto = RestDocsUtils.readJsonFile(uri, "responseDto.json") toDto SimpleMessageResponseDto::class.java

            When("요청이 들어오면"){

                Then("응답 DTO를 정상적으로 받아야 한다."){
                    val result = healthCheckService.healthCheck()
                    result shouldBe expectedDto
                }
            }
        }
    }
})