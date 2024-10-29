package com.knu.mockin.exception

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.handleCoroutineException
import org.springframework.http.ResponseEntity

class GlobalExceptionHandlerTest:FunSpec({
    val handler = GlobalExceptionHandler()

    test("GlobalExceptionHandler 테스트"){
        val exception = CustomException(ErrorCode.INTERNAL_SERVER_ERROR)
        val response: ResponseEntity<*> = handler.handleCustomException(exception)

        val body = response.body as ExceptionDto
        body.message shouldBe exception.message
        body.errorCode shouldBe exception.errorCode
        body.status shouldBe exception.result
    }

})