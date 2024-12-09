package com.knu.mockin.model.dto.response

import com.knu.mockin.model.entity.UserInfo

data class LoginResponseDto (
    val token: String = "",
    val accountNumber:String = "",
    val isMockRegistered: Boolean,
    val isRealRegistered: Boolean
)

fun buildLoginResponseDto(token: String, userInfo:UserInfo): LoginResponseDto {
    return LoginResponseDto(
        token = token,
        accountNumber = userInfo.accountNumber,
        isMockRegistered = userInfo.appKey != "",
        isRealRegistered = userInfo.appSecret != ""
    )
}