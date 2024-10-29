package com.knu.mockin.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(val status: Int, val httpStatus: HttpStatus, val message: String) {
    USER_NOT_FOUND(404,HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다"),
    INVALID_PERMISSION(401,HttpStatus.UNAUTHORIZED, "권한이 없습니다"),
    INVALID_INPUT(400, HttpStatus.BAD_REQUEST,"잘못된 요청입니다."),
    INVALID_LOGIN(400, HttpStatus.BAD_REQUEST, "아이디가 존재하지 않거나, 비밀번호가 틀렸습니다."),
    ALREADY_REGISTERED(409, HttpStatus.CONFLICT,"이미 가입된 회원입니다."),
    TOKEN_INVALID(401, HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰: 로그인이 필요합니다."),
    TOKEN_EXPIRED(401, HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),
    KIS_API_FAILED(502, HttpStatus.BAD_GATEWAY, "KIS API 서버로부터 적절한 응답을 받아오지 못했습니다."),
    INTERNAL_SERVER_ERROR(500, HttpStatus.INTERNAL_SERVER_ERROR, "서버에 알 수 없는 문제가 생겼습니다. 개발자에게 문의하세요"),
    FORBIDDEN(403, HttpStatus.FORBIDDEN, "요청이 KIS 서버에 도달했으나, 잘못된 요청이 담겨 거부당했습니다."),
    UNAUTHORIZED(401, HttpStatus.UNAUTHORIZED, "적절한 토큰이 없어 접근이 거부당했습니다.")
}
