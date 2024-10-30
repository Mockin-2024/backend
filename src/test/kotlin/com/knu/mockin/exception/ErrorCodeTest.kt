package com.knu.mockin.exception

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.springframework.http.HttpStatus

class ErrorCodeTest:FunSpec({

    test("ErrorCode 테스트"){
        val errorCode = ErrorCode.INTERNAL_SERVER_ERROR

        errorCode.httpStatus shouldBe HttpStatus.INTERNAL_SERVER_ERROR
    }
})