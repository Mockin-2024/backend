package com.knu.mockin.model.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

data class ApprovalKeyResponseDto(
    @JsonProperty("approval_key")val approvalKey:String
)
