package com.knu.mockin.model.dto.kisresponse.trading

import com.fasterxml.jackson.annotation.JsonProperty

data class KISPresentBalanceResponseDto(
    @JsonProperty("rt_cd") val successFailure: String,      // 성공 실패 여부
    @JsonProperty("msg_cd") val responseCode: String,       // 응답코드
    @JsonProperty("msg1") val responseMessage: String,      // 응답메세지
    @JsonProperty("output1") val output1: List<Output1Dto>, // 응답상세1 (체결기준 잔고)
    @JsonProperty("output2") val output2: List<Output2Dto>, // 응답상세2
    @JsonProperty("output3") val output3: Output3Dto        // 응답상세3
)

data class Output1Dto(
    @JsonProperty("prdt_name") val productName: String,                                 // 상품명
    @JsonProperty("cblc_qty13") val balanceQuantity13: String,                          // 잔고수량13
    @JsonProperty("thdt_buy_ccld_qty1") val todayBuyExecutedQuantity1: String,          // 당일매수체결수량1
    @JsonProperty("thdt_sll_ccld_qty1") val todaySellExecutedQuantity1: String,         // 당일매도체결수량1
    @JsonProperty("ccld_qty_smtl1") val executedQuantityTotal1: String,                 // 체결수량합계1
    @JsonProperty("ord_psbl_qty1") val orderPossibleQuantity1: String,                  // 주문가능수량1
    @JsonProperty("frcr_pchs_amt") val foreignPurchaseAmount: String,                   // 외화매입금액
    @JsonProperty("frcr_evlu_amt2") val foreignEvaluationAmount2: String,               // 외화평가금액2
    @JsonProperty("evlu_pfls_amt2") val evaluationProfitLossAmount2: String,            // 평가손익금액2
    @JsonProperty("evlu_pfls_rt1") val evaluationProfitLossRate1: String,               // 평가손익율1
    @JsonProperty("pdno") val productNumber: String,                                    // 상품번호
    @JsonProperty("bass_exrt") val baseExchangeRate: String,                            // 기준환율
    @JsonProperty("buy_crcy_cd") val purchaseCurrencyCode: String,                      // 매수통화코드
    @JsonProperty("ovrs_now_pric1") val overseasCurrentPrice1: String,                  // 해외현재가격1
    @JsonProperty("avg_unpr3") val averagePrice3: String,                               // 평균단가3
    @JsonProperty("tr_mket_name") val marketName: String,                               // 거래시장명
    @JsonProperty("natn_kor_name") val countryNameInKorean: String,                     // 국가한글명
    @JsonProperty("pchs_rmnd_wcrc_amt") val purchaseRemainingWonAmount: String,         // 매입잔액원화금액
    @JsonProperty("thdt_buy_ccld_frcr_amt") val todayBuyExecutedForeignAmount: String,  // 당일매수체결외화금액
    @JsonProperty("thdt_sll_ccld_frcr_amt") val todaySellExecutedForeignAmount: String, // 당일매도체결외화금액
    @JsonProperty("unit_amt") val unitAmount: String,                                   // 단위금액
    @JsonProperty("std_pdno") val standardProductNumber: String,                        // 표준상품번호
    @JsonProperty("prdt_type_cd") val productTypeCode: String,                          // 상품유형코드
    @JsonProperty("scts_dvsn_name") val securityDivisionName: String,                   // 유가증권구분명
    @JsonProperty("loan_rmnd") val loanRemaining: String,                               // 대출잔액
    @JsonProperty("loan_dt") val loanDate: String,                                      // 대출일자
    @JsonProperty("loan_expd_dt") val loanExpirationDate: String,                       // 대출만기일자
    @JsonProperty("ovrs_excg_cd") val overseasExchangeCode: String,                     // 해외거래소코드
    @JsonProperty("item_lnkg_excg_cd") val itemLinkingExchangeCode: String              // 종목연동거래소코드
)

data class Output2Dto(
    @JsonProperty("crcy_cd") val currencyCode: String,                                  // 통화코드
    @JsonProperty("crcy_cd_name") val currencyCodeName: String,                         // 통화코드명
    @JsonProperty("frcr_buy_amt_smtl") val foreignBuyAmountTotal: String,               // 외화매수금액합계
    @JsonProperty("frcr_sll_amt_smtl") val foreignSellAmountTotal: String,              // 외화매도금액합계
    @JsonProperty("frcr_dncl_amt_2") val foreignDepositAmount2: String,                 // 외화예수금액2
    @JsonProperty("frst_bltn_exrt") val firstOfficialExchangeRate: String,              // 최초고시환율
    @JsonProperty("frcr_buy_mgn_amt") val foreignBuyMarginAmount: String,               // 외화매수증거금액
    @JsonProperty("frcr_etc_mgna") val foreignOtherMargin: String,                      // 외화기타증거금
    @JsonProperty("frcr_drwg_psbl_amt_1") val foreignWithdrawalPossibleAmount1: String, // 외화출금가능금액1
    @JsonProperty("frcr_evlu_amt2") val withdrawalPossibleWonAmount: String,            // 출금가능원화금액
    @JsonProperty("acpl_cstd_crcy_yn") val localCustodyCurrency: String,                // 현지보관통화여부
    @JsonProperty("nxdy_frcr_drwg_psbl_amt") val nextDayForeignWithdrawalPossibleAmount: String // 익일외화출금가능금액
)

data class Output3Dto(
    @JsonProperty("pchs_amt_smtl") val purchaseAmountTotal: String,                     // 매입금액합계
    @JsonProperty("evlu_amt_smtl") val evaluationAmountTotal: String,                   // 평가금액합계
    @JsonProperty("evlu_pfls_amt_smtl") val evaluationProfitLossAmountTotal: String,    // 평가손익금액합계
    @JsonProperty("dncl_amt") val depositAmount: String,                                // 예수금액
    @JsonProperty("cma_evlu_amt") val cmaEvaluationAmount: String,                      // CMA평가금액
    @JsonProperty("tot_dncl_amt") val totalDepositAmount: String,                       // 총예수금액
    @JsonProperty("etc_mgna") val otherMargin: String,                                  // 기타증거금
    @JsonProperty("wdrw_psbl_tot_amt") val withdrawableTotalAmount: String,             // 인출가능총금액
    @JsonProperty("frcr_evlu_tota") val foreignEvaluationTotal: String,                 // 외화평가총액
    @JsonProperty("evlu_erng_rt1") val evaluationEarningsRate1: String,                 // 평가수익율1
    @JsonProperty("pchs_amt_smtl_amt") val purchaseAmountTotalAmount: String,           // 매입금액합계금액
    @JsonProperty("evlu_amt_smtl_amt") val evaluationAmountTotalAmount: String,         // 평가금액합계금액
    @JsonProperty("tot_evlu_pfls_amt") val totalEvaluationProfitLossAmount: String,     // 총평가손익금액
    @JsonProperty("tot_asst_amt") val totalAssetAmount: String,                         // 총자산금액
    @JsonProperty("buy_mgn_amt") val buyMarginAmount: String,                           // 매수증거금액
    @JsonProperty("mgna_tota") val totalMargin: String,                                 // 증거금총액
    @JsonProperty("frcr_use_psbl_amt") val foreignUsableAmount: String,                 // 외화사용가능금액
    @JsonProperty("ustl_sll_amt_smtl") val unsettledSellAmountTotal: String,            // 미결제매도금액합계
    @JsonProperty("ustl_buy_amt_smtl") val unsettledBuyAmountTotal: String,             // 미결제매수금액합계
    @JsonProperty("tot_frcr_cblc_smtl") val totalForeignBalanceTotal: String,           // 총외화잔고합계
    @JsonProperty("tot_loan_amt") val totalLoanAmount: String                           // 총대출금액
)
