package com.knu.mockin.model.dto.kisresponse.trading

import com.fasterxml.jackson.annotation.JsonProperty

data class KISCCNLResponseDto(
    @JsonProperty("rt_cd") val rtCd: String,                    // 성공 실패 여부
    @JsonProperty("msg_cd") val msgCd: String,                  // 응답코드
    @JsonProperty("msg1") val msg1: String,                     // 응답메세지
    @JsonProperty("ctx_area_fk200") val ctxAreaFk200: String,   // 연속조회검색조건200
    @JsonProperty("ctx_area_nk200") val ctxAreaNk200: String,   // 연속조회키200
    @JsonProperty("output") val output: List<OrderDetail>       // 응답상세
)

data class OrderDetail(
    @JsonProperty("ord_dt") val ordDt: String,                          // 주문접수 일자 (현지시각 기준)
    @JsonProperty("ord_gno_brno") val ordGnoBrno: String,               // 계좌 개설 시 관리점으로 선택한 영업점의 고유번호
    @JsonProperty("odno") val odno: String,                             // 접수한 주문의 일련번호
    @JsonProperty("orgn_odno") val orgnOdno: String,                    // 정정 또는 취소 대상 주문의 일련번호
    @JsonProperty("sll_buy_dvsn_cd") val sllBuyDvsnCd: String,          // 매도매수구분코드
    @JsonProperty("sll_buy_dvsn_cd_name") val sllBuyDvsnCdName: String, // 매도매수구분코드명
    @JsonProperty("rvse_cncl_dvsn") val rvseCnclDvsn: String,           // 정정취소구분
    @JsonProperty("rvse_cncl_dvsn_name") val rvseCnclDvsnName: String,  // 정정취소구분명
    @JsonProperty("pdno") val pdno: String,                             // 상품번호
    @JsonProperty("prdt_name") val prdtName: String,                    // 상품명
    @JsonProperty("ft_ord_qty") val ftOrdQty: String,                   // 주문수량
    @JsonProperty("ft_ord_unpr3") val ftOrdUnpr3: String,               // 주문단가3
    @JsonProperty("ft_ccld_qty") val ftCcldQty: String,                 // 체결된 수량
    @JsonProperty("ft_ccld_unpr3") val ftCcldUnpr3: String,             // 체결된 가격
    @JsonProperty("ft_ccld_amt3") val ftCcldAmt3: String,               // 체결된 금액
    @JsonProperty("nccs_qty") val nccsQty: String,                      // 미체결수량
    @JsonProperty("prcs_stat_name") val prcsStatName: String,           // 처리상태명
    @JsonProperty("rjct_rson") val rjctRson: String,                    // 거부사유
    @JsonProperty("ord_tmd") val ordTmd: String,                        // 주문 접수 시간
    @JsonProperty("tr_mket_name") val trMketName: String,               // 거래시장명
    @JsonProperty("tr_natn") val trNatn: String,                        // 거래국가
    @JsonProperty("tr_natn_name") val trNatnName: String,               // 거래국가명
    @JsonProperty("ovrs_excg_cd") val ovrsExcgCd: String,               // 해외거래소코드
    @JsonProperty("tr_crcy_cd") val trCrcyCd: String,                   // 거래통화코드
    @JsonProperty("dmst_ord_dt") val dmstOrdDt: String,                 // 국내주문일자
    @JsonProperty("thco_ord_tmd") val thcoOrdTmd: String,               // 당사주문시각
    @JsonProperty("loan_type_cd") val loanTypeCd: String,               // 대출유형코드
    @JsonProperty("mdia_dvsn_name") val mdiaDvsnName: String,           // 매체구분명
    @JsonProperty("loan_dt") val loanDt: String,                        // 대출일자
    @JsonProperty("rjct_rson_name") val rjctRsonName: String,           // 거부사유명
    @JsonProperty("usa_amk_exts_rqst_yn") val usaAmkExtsRqstYn: String  // 미국애프터마켓연장신청여부
)