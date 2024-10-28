package com.knu.mockin.model.dto.kisresponse.quotations.basic.real

import com.fasterxml.jackson.annotation.JsonProperty

class KISInquireAskingPriceResponseDto (
    @JsonProperty("rt_cd") val successFailureStatus: String,    // 성공 실패 여부
    @JsonProperty("msg_cd") val responseCode: String,           // 응답코드
    @JsonProperty("msg1") val responseMessage: String,          // 응답메세지
    @JsonProperty("output1") val output1: InquireAskingPriceOutput1,        // 응답상세
    @JsonProperty("output2") val output2: InquireAskingPriceOutput2?,
    @JsonProperty("output3") val output3: InquireAskingPriceOutput3?
)

data class InquireAskingPriceOutput1 (
    @JsonProperty("rsym") val rsym: String,              // 실시간조회종목코드
    @JsonProperty("zdiv") val zdiv: String,              // 소수점자리수
    @JsonProperty("curr") val curr: String,              // 통화
    @JsonProperty("base") val base: String,              // 전일종가
    @JsonProperty("open") val open: String,              // 시가
    @JsonProperty("high") val high: String,              // 고가
    @JsonProperty("low") val low: String,                // 저가
    @JsonProperty("last") val last: String,              // 현재가
    @JsonProperty("dymd") val dymd: String,              // 호가일자
    @JsonProperty("dhms") val dhms: String,              // 호가시간
    @JsonProperty("bvol") val bvol: String,              // 매수호가총잔량
    @JsonProperty("avol") val avol: String,              // 매도호가총잔량
    @JsonProperty("bdvl") val bdvl: String,              // 매수호가총잔량대비
    @JsonProperty("advl") val advl: String,              // 매도호가총잔량대비
    @JsonProperty("code") val code: String,              // 종목코드
    @JsonProperty("ropen") val ropen: String,            // 시가율
    @JsonProperty("rhigh") val rhigh: String,            // 고가율
    @JsonProperty("rlow") val rlow: String,              // 저가율
    @JsonProperty("rclose") val rclose: String           // 현재가율
)

data class InquireAskingPriceOutput2 (
    @JsonProperty("pbid1") val pbid1: String,         // 매수호가가격1
    @JsonProperty("pask1") val pask1: String,         // 매도호가가격1
    @JsonProperty("vbid1") val vbid1: String,         // 매수호가잔량1
    @JsonProperty("vask1") val vask1: String,         // 매도호가잔량1
    @JsonProperty("dbid1") val dbid1: String,         // 매수호가대비1
    @JsonProperty("dask1") val dask1: String,         // 매도호가대비1

    @JsonProperty("pbid2") val pbid2: String,         // 매수호가가격2
    @JsonProperty("pask2") val pask2: String,         // 매도호가가격2
    @JsonProperty("vbid2") val vbid2: String,         // 매수호가잔량2
    @JsonProperty("vask2") val vask2: String,         // 매도호가잔량2
    @JsonProperty("dbid2") val dbid2: String,         // 매수호가대비2
    @JsonProperty("dask2") val dask2: String,         // 매도호가대비2

    @JsonProperty("pbid3") val pbid3: String,         // 매수호가가격3
    @JsonProperty("pask3") val pask3: String,         // 매도호가가격3
    @JsonProperty("vbid3") val vbid3: String,         // 매수호가잔량3
    @JsonProperty("vask3") val vask3: String,         // 매도호가잔량3
    @JsonProperty("dbid3") val dbid3: String,         // 매수호가대비3
    @JsonProperty("dask3") val dask3: String,         // 매도호가대비3

    @JsonProperty("pbid4") val pbid4: String,         // 매수호가가격4
    @JsonProperty("pask4") val pask4: String,         // 매도호가가격4
    @JsonProperty("vbid4") val vbid4: String,         // 매수호가잔량4
    @JsonProperty("vask4") val vask4: String,         // 매도호가잔량4
    @JsonProperty("dbid4") val dbid4: String,         // 매수호가대비4
    @JsonProperty("dask4") val dask4: String,         // 매도호가대비4

    @JsonProperty("pbid5") val pbid5: String,         // 매수호가가격5
    @JsonProperty("pask5") val pask5: String,         // 매도호가가격5
    @JsonProperty("vbid5") val vbid5: String,         // 매수호가잔량5
    @JsonProperty("vask5") val vask5: String,         // 매도호가잔량5
    @JsonProperty("dbid5") val dbid5: String,         // 매수호가대비5
    @JsonProperty("dask5") val dask5: String,         // 매도호가대비5

    @JsonProperty("pbid6") val pbid6: String,         // 매수호가가격6
    @JsonProperty("pask6") val pask6: String,         // 매도호가가격6
    @JsonProperty("vbid6") val vbid6: String,         // 매수호가잔량6
    @JsonProperty("vask6") val vask6: String,         // 매도호가잔량6
    @JsonProperty("dbid6") val dbid6: String,         // 매수호가대비6
    @JsonProperty("dask6") val dask6: String,         // 매도호가대비6

    @JsonProperty("pbid7") val pbid7: String,         // 매수호가가격7
    @JsonProperty("pask7") val pask7: String,         // 매도호가가격7
    @JsonProperty("vbid7") val vbid7: String,         // 매수호가잔량7
    @JsonProperty("vask7") val vask7: String,         // 매도호가잔량7
    @JsonProperty("dbid7") val dbid7: String,         // 매수호가대비7
    @JsonProperty("dask7") val dask7: String,         // 매도호가대비7

    @JsonProperty("pbid8") val pbid8: String,         // 매수호가가격8
    @JsonProperty("pask8") val pask8: String,         // 매도호가가격8
    @JsonProperty("vbid8") val vbid8: String,         // 매수호가잔량8
    @JsonProperty("vask8") val vask8: String,         // 매도호가잔량8
    @JsonProperty("dbid8") val dbid8: String,         // 매수호가대비8
    @JsonProperty("dask8") val dask8: String,         // 매도호가대비8

    @JsonProperty("pbid9") val pbid9: String,         // 매수호가가격9
    @JsonProperty("pask9") val pask9: String,         // 매도호가가격9
    @JsonProperty("vbid9") val vbid9: String,         // 매수호가잔량9
    @JsonProperty("vask9") val vask9: String,         // 매도호가잔량9
    @JsonProperty("dbid9") val dbid9: String,         // 매수호가대비9
    @JsonProperty("dask9") val dask9: String,         // 매도호가대비9

    @JsonProperty("pbid10") val pbid10: String,       // 매수호가가격10
    @JsonProperty("pask10") val pask10: String,       // 매도호가가격10
    @JsonProperty("vbid10") val vbid10: String,       // 매수호가잔량10
    @JsonProperty("vask10") val vask10: String,       // 매도호가잔량10
    @JsonProperty("dbid10") val dbid10: String,       // 매수호가대비10
    @JsonProperty("dask10") val dask10: String        // 매도호가대비10
)

data class InquireAskingPriceOutput3 (
    @JsonProperty("vstm") val vstm: String,         // VCMStart시간
    @JsonProperty("vetm") val vetm: String,         // VCMEnd시간
    @JsonProperty("csbp") val csbp: String,         // CAS/VCM기준가
    @JsonProperty("cshi") val cshi: String,         // CAS/VCMHighprice
    @JsonProperty("cslo") val cslo: String,         // CAS/VCMLowprice
    @JsonProperty("iep") val iep: String,           // IEP
    @JsonProperty("iev") val iev: String            // IEV
)