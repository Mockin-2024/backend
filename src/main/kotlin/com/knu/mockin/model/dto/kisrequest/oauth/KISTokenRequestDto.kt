package com.knu.mockin.model.dto.kisrequest.oauth

import com.fasterxml.jackson.annotation.JsonProperty

data class KISTokenRequestDto(
    @JsonProperty("grant_type") val grantType: String,
    @JsonProperty("appkey") val appKey: String,
    @JsonProperty("appsecret") val appSecret: String
)
