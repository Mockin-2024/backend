package com.knu.mockin.model.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

data class AccessTokenAPIResponseDto (
    @JsonProperty("access_token") val accessToken:String,
    @JsonProperty("token_type") val tokenType: String,
    @JsonProperty("expire_in") val expiresIn: Long,
    @JsonProperty("access_token_token_expired") val accessTokenTokenExpired: String
)
