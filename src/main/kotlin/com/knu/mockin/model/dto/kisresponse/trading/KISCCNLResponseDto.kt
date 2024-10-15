package com.knu.mockin.model.dto.kisresponse.trading

import com.fasterxml.jackson.annotation.JsonProperty

data class KISCCNLResponseDto(
    @JsonProperty("rt_cd") val successFailureStatus: String,           // 성공 실패 여부
    @JsonProperty("msg_cd") val messageCode: String, // 응답코드
    @JsonProperty("msg1") val responseMessage: String, // 응답메세지
    @JsonProperty("ctx_area_fk200") val continuousQuerySearchCondition200: String, // 연속조회검색조건200
    @JsonProperty("ctx_area_nk200") val continuousQueryKey200: String, // 연속조회키200
    @JsonProperty("output") val output: List<OrderDetail> // 응답상세
)

data class OrderDetail(
    @JsonProperty("ord_dt") val orderDate: String, // 주문접수 일자
    @JsonProperty("ord_gno_brno") val orderBranchNumber: String, // 주문채번지점번호
    @JsonProperty("odno") val orderNumber: String, // 주문번호
    @JsonProperty("orgn_odno") val originalOrderNumber: String, // 원주문번호
    @JsonProperty("sll_buy_dvsn_cd") val sellBuyDivisionCode: String, // 매도매수구분코드
    @JsonProperty("sll_buy_dvsn_cd_name") val sellBuyDivisionCodeName: String, // 매도매수구분코드명
    @JsonProperty("rvse_cncl_dvsn") val revisionCancellationDivision: String, // 정정취소구분
    @JsonProperty("rvse_cncl_dvsn_name") val revisionCancellationDivisionName: String, // 정정취소구분명
    @JsonProperty("pdno") val productNumber: String, // 상품번호
    @JsonProperty("prdt_name") val productName: String, // 상품명
    @JsonProperty("ft_ord_qty") val ftOrderQuantity: String, // FT주문수량
    @JsonProperty("ft_ord_unpr3") val ftOrderPrice3: String, // FT주문단가3
    @JsonProperty("ft_ccld_qty") val ftExecutedQuantity: String, // FT체결수량
    @JsonProperty("ft_ccld_unpr3") val ftExecutedPrice3: String, // FT체결단가3
    @JsonProperty("ft_ccld_amt3") val ftExecutedAmount3: String, // FT체결금액3
    @JsonProperty("nccs_qty") val unexecutedQuantity: String, // 미체결수량
    @JsonProperty("prcs_stat_name") val processingStatusName: String, // 처리상태명
    @JsonProperty("rjct_rson") val rejectionReason: String, // 거부사유
    @JsonProperty("ord_tmd") val orderTime: String, // 주문시각
    @JsonProperty("tr_mket_name") val tradingMarketName: String, // 거래시장명
    @JsonProperty("tr_natn") val tradingCountry: String, // 거래국가
    @JsonProperty("tr_natn_name") val tradingCountryName: String, // 거래국가명
    @JsonProperty("ovrs_excg_cd") val overseasExchangeCode: String, // 해외거래소코드
    @JsonProperty("tr_crcy_cd") val tradingCurrencyCode: String, // 거래통화코드
    @JsonProperty("dmst_ord_dt") val domesticOrderDate: String, // 국내주문일자
    @JsonProperty("thco_ord_tmd") val companyOrderTime: String, // 당사주문시각
    @JsonProperty("loan_type_cd") val loanTypeCode: String, // 대출유형코드
    @JsonProperty("mdia_dvsn_name") val mediaDivisionName: String, // 매체구분명
    @JsonProperty("loan_dt") val loanDate: String, // 대출일자
    @JsonProperty("rjct_rson_name") val rejectionReasonName: String, // 거부사유명
    @JsonProperty("usa_amk_exts_rqst_yn") val usAfterMarketExtensionRequest: String // 미국애프터마켓연장신청여부
    )