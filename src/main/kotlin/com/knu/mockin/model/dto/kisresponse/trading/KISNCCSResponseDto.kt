package com.knu.mockin.model.dto.kisresponse.trading

import com.fasterxml.jackson.annotation.JsonProperty

data class KISNCCSResponseDto(
    @JsonProperty("rt_cd") val successFailureStatus: String,       // 성공 실패 여부
    @JsonProperty("msg_cd") val responseCode: String,        // 응답코드
    @JsonProperty("msg1") val responseMessage: String,       // 응답메세지
    @JsonProperty("ctx_area_fk200") val continuousSearchCondition200: String, // 연속조회검색조건200
    @JsonProperty("ctx_area_nk200") val continuousSearchKey200: String, // 연속조회키200
    @JsonProperty("output") val output: List<OrderDetailDto> // 응답상세

)

data class OrderDetailDto(
    @JsonProperty("ord_dt") val orderDate: String,               // 주문일자
    @JsonProperty("ord_gno_brno") val orderBranchNumber: String, // 주문채번지점번호
    @JsonProperty("odno") val orderNumber: String,               // 주문번호
    @JsonProperty("orgn_odno") val originalOrderNumber: String,  // 원주문번호
    @JsonProperty("pdno") val productCode: String,               // 상품번호
    @JsonProperty("prdt_name") val productName: String,          // 상품명
    @JsonProperty("sll_buy_dvsn_cd") val sellBuyDivisionCode: String,           // 매도매수구분코드
    @JsonProperty("sll_buy_dvsn_cd_name") val sellBuyDivisionCodeName: String,  // 매도매수구분코드명
    @JsonProperty("rvse_cncl_dvsn_cd") val reviseCancelDivisionCode: String,    // 정정취소구분코드
    @JsonProperty("rvse_cncl_dvsn_cd_name") val reviseCancelDivisionCodeName: String, // 정정취소구분코드명
    @JsonProperty("rjct_rson") val rejectionReason: String,             // 거부사유
    @JsonProperty("rjct_rson_name") val rejectionReasonName: String,    // 거부사유명
    @JsonProperty("ord_tmd") val orderTime: String,                     // 주문시각
    @JsonProperty("tr_mket_name") val tradingMarketName: String,        // 거래시장명
    @JsonProperty("tr_crcy_cd") val tradingCurrencyCode: String,        // 거래통화코드
    @JsonProperty("natn_cd") val countryCode: String,                   // 국가코드
    @JsonProperty("natn_kor_name") val countryNameKor: String,          // 국가한글명
    @JsonProperty("ft_ord_qty") val ftOrderQuantity: String,            // FT주문수량
    @JsonProperty("ft_ccld_qty") val ftExecutedQuantity: String,        // FT체결수량
    @JsonProperty("nccs_qty") val unexecutedQuantity: String,           // 미체결수량
    @JsonProperty("ft_ord_unpr3") val ftOrderPrice3: String,            // FT주문단가3
    @JsonProperty("ft_ccld_unpr3") val ftExecutedPrice3: String,        // FT체결단가3
    @JsonProperty("ft_ccld_amt3") val ftExecutedAmount3: String,        // FT체결금액3
    @JsonProperty("ovrs_excg_cd") val overseasExchangeCode: String,     // 해외거래소코드
    @JsonProperty("prcs_stat_name") val processingStatusName: String,   // 처리상태명
    @JsonProperty("loan_type_cd") val loanTypeCode: String,             // 대출유형코드
    @JsonProperty("loan_dt") val loanDate: String,                      // 대출일자
    @JsonProperty("usa_amk_exts_rqst_yn") val usAfterMarketExtensionRequest: String, // 미국애프터마켓연장신청여부
)
