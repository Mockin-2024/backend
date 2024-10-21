package com.knu.mockin.security.handler

import com.knu.mockin.exeption.CustomException
import com.knu.mockin.exeption.ErrorCode
import com.knu.mockin.exeption.ExceptionDto
import com.knu.mockin.logging.utils.LogUtil.toJson
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class JwtAccessDeniedHandler: ServerAccessDeniedHandler {
    override fun handle(exchange: ServerWebExchange, denied: AccessDeniedException): Mono<Void> {

        exchange.response.statusCode = HttpStatus.UNAUTHORIZED
        exchange.response.headers.contentType = APPLICATION_JSON

        val responseMessage = ExceptionDto(
            status = ErrorCode.UNAUTHORIZED.status,
            errorCode = ErrorCode.UNAUTHORIZED,
            message = ErrorCode.UNAUTHORIZED.message
        )

        return exchange.response.writeWith(Mono.just(
            exchange.response.bufferFactory().wrap(toJson(responseMessage).toByteArray())
        ))
    }
}