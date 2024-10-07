package com.knu.mockin.model.dto.kisresponse.basicprice.askingprice

import com.fasterxml.jackson.annotation.JsonProperty

data class KISAskingPriceResponseOutput3Dto (
        @JsonProperty("vstm") val vstm: String,         // VCMStart시간
        @JsonProperty("vetm") val vetm: String,         // VCMEnd시간
        @JsonProperty("csbp") val csbp: String,         // CAS/VCM기준가
        @JsonProperty("cshi") val cshi: String,         // CAS/VCMHighprice
        @JsonProperty("cslo") val cslo: String,         // CAS/VCMLowprice
        @JsonProperty("iep") val iep: String,           // IEP
        @JsonProperty("iev") val iev: String            // IEV
)