package com.knu.mockin.model.dto.request

import com.fasterxml.jackson.annotation.JsonProperty

data class KISApprovalRequestDto(
    @JsonProperty("grant_type") val grantType: String,
    @JsonProperty("appkey") val appKey: String,
    @JsonProperty("secretkey") val secretKey: String
)
