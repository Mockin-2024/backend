package com.knu.mockin.model.dto.kisrequest

import com.fasterxml.jackson.annotation.JsonProperty

data class TestRequestParameter (
        @JsonProperty("FID_COND_MRKT_DIV_CODE") val fidCondMrktDivCode : String,
        @JsonProperty("FID_INPUT_ISCD") val fidInputIscd : String,
)