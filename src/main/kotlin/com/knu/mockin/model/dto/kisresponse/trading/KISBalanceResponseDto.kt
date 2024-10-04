package com.knu.mockin.model.dto.kisresponse.trading

import com.fasterxml.jackson.annotation.JsonProperty

data class KISBalanceResponseDto (
    @JsonProperty("rt_cd") val successFailure: String,  // 성공 실패 여부 (1자리)
    @JsonProperty("msg_cd") val responseCode: String,   // 응답코드 (8자리)
    @JsonProperty("msg1") val responseMessage: String,  // 응답메세지 (80자리)
    @JsonProperty("ctx_area_fk200") val continuousSearchCondition200: String, // 연속조회검색조건200 (200자리)
    @JsonProperty("ctx_area_nk200") val continuousSearchKey200: String, // 연속조회키200 (200자리)
    @JsonProperty("output1") val output1: List<Output1>,                // 응답상세1
    @JsonProperty("output2") val output2: Output2                       // 응답상세2
)

data class Output1(
    @JsonProperty("cano") val accountNumber: String,                    // 종합계좌번호 (8자리)
    @JsonProperty("acnt_prdt_cd") val accountProductCode: String,       // 계좌상품코드 (2자리)
    @JsonProperty("prdt_type_cd") val productTypeCode: String,          // 상품유형코드 (3자리)
    @JsonProperty("ovrs_pdno") val overseasProductNumber: String,       // 해외상품번호 (12자리)
    @JsonProperty("ovrs_item_name") val overseasItemName: String,       // 해외종목명 (60자리)
    @JsonProperty("frcr_evlu_pfls_amt") val foreignExchangeEvaluationProfitLossAmount: String, // 외화평가손익금액 (30자리)
    @JsonProperty("evlu_pfls_rt") val evaluationProfitLossRate: String, // 평가손익율 (10자리)
    @JsonProperty("pchs_avg_pric") val purchaseAveragePrice: String,    // 매입평균가격 (23자리)
    @JsonProperty("ovrs_cblc_qty") val overseasBalanceQuantity: String, // 해외잔고수량 (19자리)
    @JsonProperty("ord_psbl_qty") val orderPossibleQuantity: String,    // 주문가능수량 (10자리)
    @JsonProperty("frcr_pchs_amt1") val foreignPurchaseAmount1: String, // 외화매입금액1 (23자리)
    @JsonProperty("ovrs_stck_evlu_amt") val overseasStockEvaluationAmount: String, // 해외주식평가금액 (32자리)
    @JsonProperty("now_pric2") val currentPrice2: String,               // 현재가격2 (25자리)
    @JsonProperty("tr_crcy_cd") val transactionCurrencyCode: String,    // 거래통화코드 (3자리)
    @JsonProperty("ovrs_excg_cd") val overseasExchangeCode: String,     // 해외거래소코드 (4자리)
    @JsonProperty("loan_type_cd") val loanTypeCode: String,             // 대출유형코드 (2자리)
    @JsonProperty("loan_dt") val loanDate: String,                      // 대출일자 (8자리)
    @JsonProperty("expd_dt") val expiryDate: String                     // 만기일자 (8자리)
)

data class Output2(
    @JsonProperty("frcr_pchs_amt1") val foreignPurchaseAmount1: String,                 // 외화매입금액1 (24자리)
    @JsonProperty("ovrs_rlzt_pfls_amt") val overseasRealizedProfitLossAmount: String,   // 해외실현손익금액 (20자리)
    @JsonProperty("ovrs_tot_pfls") val overseasTotalProfitLoss: String,                 // 해외총손익 (24자리)
    @JsonProperty("rlzt_erng_rt") val realizedEarningsRate: String,                     // 실현수익율 (32자리)
    @JsonProperty("tot_evlu_pfls_amt") val totalEvaluationProfitLossAmount: String,     // 총평가손익금액 (32자리)
    @JsonProperty("tot_pftrt") val totalProfitRate: String,                             // 총수익률 (32자리)
    @JsonProperty("frcr_buy_amt_smtl1") val foreignPurchaseAmountSum1: String,          // 외화매수금액합계1 (25자리)
    @JsonProperty("ovrs_rlzt_pfls_amt2") val overseasRealizedProfitLossAmount2: String, // 해외실현손익금액2 (24자리)
    @JsonProperty("frcr_buy_amt_smtl2") val foreignPurchaseAmountSum2: String           // 외화매수금액합계2 (25자리)
)