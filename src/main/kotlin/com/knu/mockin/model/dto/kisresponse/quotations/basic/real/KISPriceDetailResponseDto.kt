package com.knu.mockin.model.dto.kisresponse.quotations.basic.real

import com.fasterxml.jackson.annotation.JsonProperty

data class KISPriceDetailResponseDto (
        @JsonProperty("rt_cd") val successFailureStatus: String,    // 성공 실패 여부
        @JsonProperty("msg_cd") val responseCode: String,           // 응답코드
        @JsonProperty("msg1") val responseMessage: String,          // 응답메세지
        @JsonProperty("output") val output: PriceDetailOutput?,        // 응답상세
)

data class PriceDetailOutput (
        @JsonProperty("rsym") val rsym: String,                 // 실시간조회종목코드
        @JsonProperty("pvol") val pvol: String,                 // 전일거래량
        @JsonProperty("open") val open: String,                 // 시가
        @JsonProperty("high") val high: String,                 // 고가
        @JsonProperty("low") val low: String,                   // 저가
        @JsonProperty("last") val last: String,                 // 현재가
        @JsonProperty("base") val base: String,                 // 전일종가
        @JsonProperty("tomv") val tomv: String,                 // 시가총액
        @JsonProperty("pamt") val pamt: String,                 // 전일거래대금
        @JsonProperty("uplp") val uplp: String,                 // 상한가
        @JsonProperty("dnlp") val dnlp: String,                 // 하한가
        @JsonProperty("h52p") val h52p: String,                 // 52주최고가
        @JsonProperty("h52d") val h52d: String,                 // 52주최고일자
        @JsonProperty("l52p") val l52p: String,                 // 52주최저가
        @JsonProperty("l52d") val l52d: String,                 // 52주최저일자
        @JsonProperty("perx") val perx: String,                 // PER
        @JsonProperty("pbrx") val pbrx: String,                 // PBR
        @JsonProperty("epsx") val epsx: String,                 // EPS
        @JsonProperty("bpsx") val bpsx: String,                 // BPS
        @JsonProperty("shar") val shar: String,                 // 상장주수
        @JsonProperty("mcap") val mcap: String,                 // 자본금
        @JsonProperty("curr") val curr: String,                 // 통화
        @JsonProperty("zdiv") val zdiv: String,                 // 소수점자리수
        @JsonProperty("vnit") val vnit: String,                 // 매매단위
        @JsonProperty("t_xprc") val t_xprc: String,             // 원환산당일가격
        @JsonProperty("t_xdif") val t_xdif: String,             // 원환산당일대비
        @JsonProperty("t_xrat") val t_xrat: String,             // 원환산당일등락
        @JsonProperty("p_xprc") val p_xprc: String,             // 원환산전일가격
        @JsonProperty("p_xdif") val p_xdif: String,             // 원환산전일대비
        @JsonProperty("p_xrat") val p_xrat: String,             // 원환산전일등락
        @JsonProperty("t_rate") val t_rate: String,             // 당일환율
        @JsonProperty("p_rate") val p_rate: String,             // 전일환율
        @JsonProperty("t_xsgn") val t_xsgn: String,             // 원환산당일기호
        @JsonProperty("p_xsng") val p_xsng: String,             // 원환산전일기호
        @JsonProperty("e_ordyn") val e_ordyn: String,           // 거래가능여부
        @JsonProperty("e_hogau") val e_hogau: String,           // 호가단위
        @JsonProperty("e_icod") val e_icod: String,             // 업종(섹터)
        @JsonProperty("e_parp") val e_parp: String,             // 액면가
        @JsonProperty("tvol") val tvol: String,                 // 거래량
        @JsonProperty("tamt") val tamt: String,                 // 거래대금
        @JsonProperty("etyp_nm") val etyp_nm: String            // ETP 분류명
)