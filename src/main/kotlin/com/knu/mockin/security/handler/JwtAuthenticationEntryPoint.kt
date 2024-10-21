package com.knu.mockin.security.handler

import com.knu.mockin.exeption.ErrorCode
import com.knu.mockin.exeption.ExceptionDto
import com.knu.mockin.logging.utils.LogUtil
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.server.ServerAuthenticationEntryPoint
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class JwtAuthenticationEntryPoint: ServerAuthenticationEntryPoint {
    override fun commence(exchange: ServerWebExchange, ex: AuthenticationException): Mono<Void> {
        exchange.response.statusCode = HttpStatus.UNAUTHORIZED
        exchange.response.headers.contentType = MediaType.APPLICATION_JSON

        val responseMessage = ExceptionDto(
            status = ErrorCode.TOKEN_INVALID.status,
            errorCode = ErrorCode.TOKEN_INVALID,
            message = ErrorCode.TOKEN_INVALID.message
        )

        return exchange.response.writeWith(Mono.just(
            exchange.response.bufferFactory().wrap(LogUtil.toJson(responseMessage).toByteArray())
        ))
    }

}