package com.knu.mockin.model.dto.kisresponse.basicprice.searchinfo

import com.fasterxml.jackson.annotation.JsonProperty

data class KISSearchInfoResponseOutput1Dto (
        @JsonProperty("std_pdno") val stdPdno: String,                        // 표준상품번호
        @JsonProperty("prdt_eng_name") val prdtEngName: String,                // 상품영문명
        @JsonProperty("natn_cd") val natnCd: String,                          // 국가코드
        @JsonProperty("natn_name") val natnName: String,                      // 국가명
        @JsonProperty("tr_mket_cd") val trMketCd: String,                     // 거래시장코드
        @JsonProperty("tr_mket_name") val trMketName: String,                 // 거래시장명
        @JsonProperty("ovrs_excg_cd") val ovrsExcgCd: String,                 // 해외거래소코드
        @JsonProperty("ovrs_excg_name") val ovrsExcgName: String,             // 해외거래소명
        @JsonProperty("tr_crcy_cd") val trCrcyCd: String,                     // 거래통화코드
        @JsonProperty("ovrs_papr") val ovrsPapr: String,                     // 해외액면가
        @JsonProperty("crcy_name") val crcyName: String,                       // 통화명
        @JsonProperty("ovrs_stck_dvsn_cd") val ovrsStckDvsnCd: String,       // 해외주식구분코드
        @JsonProperty("prdt_clsf_cd") val prdtClsfCd: String,                 // 상품분류코드
        @JsonProperty("prdt_clsf_name") val prdtClsfName: String,             // 상품분류명
        @JsonProperty("sll_unit_qty") val sllUnitQty: String,                 // 매도단위수량
        @JsonProperty("buy_unit_qty") val buyUnitQty: String,                 // 매수단위수량
        @JsonProperty("tr_unit_amt") val trUnitAmt: String,                   // 거래단위금액
        @JsonProperty("lstg_stck_num") val lstgStckNum: String,               // 상장주식수
        @JsonProperty("lstg_dt") val lstgDt: String,                           // 상장일자
        @JsonProperty("ovrs_stck_tr_stop_dvsn_cd") val ovrsStckTrStopDvsnCd: String, // 해외주식거래정지구분코드
        @JsonProperty("lstg_abol_item_yn") val lstgAbolItemYn: String,       // 상장폐지종목여부
        @JsonProperty("ovrs_stck_prdt_grp_no") val ovrsStckPrdtGrpNo: String, // 해외주식상품그룹번호
        @JsonProperty("lstg_yn") val lstgYn: String,                           // 상장여부
        @JsonProperty("tax_levy_yn") val taxLevyYn: String,                   // 세금징수여부
        @JsonProperty("ovrs_stck_erlm_rosn_cd") val ovrsStckErlmRosnCd: String, // 해외주식등록사유코드
        @JsonProperty("ovrs_stck_hist_rght_dvsn_cd") val ovrsStckHistRghtDvsnCd: String, // 해외주식이력권리구분코드
        @JsonProperty("chng_bf_pdno") val chngBfPdno: String,                  // 변경전상품번호
        @JsonProperty("prdt_type_cd_2") val prdtTypeCd2: String,              // 상품유형코드2
        @JsonProperty("ovrs_item_name") val ovrsItemName: String,              // 해외종목명
        @JsonProperty("sedol_no") val sedolNo: String,                        // SEDOL번호
        @JsonProperty("blbg_tckr_text") val blbgTckrText: String,             // 블름버그티커내용
        @JsonProperty("ovrs_stck_etf_risk_drtp_cd") val ovrsStckEtfRiskDrtpCd: String, // 해외주식ETF위험지표코드
        @JsonProperty("etp_chas_erng_rt_dbnb") val etpChasErngRtDbnb: String, // ETP추적수익율배수
        @JsonProperty("istt_usge_isin_cd") val isttUsgeIsinCd: String,        // 기관용도ISIN코드
        @JsonProperty("mint_svc_yn") val mintSvcYn: String,                   // MINT서비스여부
        @JsonProperty("mint_svc_yn_chng_dt") val mintSvcYnChngDt: String,    // MINT서비스여부변경일자
        @JsonProperty("prdt_name") val prdtName: String,                      // 상품명
        @JsonProperty("lei_cd") val leiCd: String,                            // LEI코드
        @JsonProperty("ovrs_stck_stop_rson_cd") val ovrsStckStopRsonCd: String, // 해외주식정지사유코드
        @JsonProperty("lstg_abol_dt") val lstgAbolDt: String,                 // 상장폐지일자
        @JsonProperty("mini_stk_tr_stat_dvsn_cd") val miniStkTrStatDvsnCd: String, // 미니스탁거래상태구분코드
        @JsonProperty("mint_frst_svc_erlm_dt") val mintFrstSvcErlmDt: String, // MINT최초서비스등록일자
        @JsonProperty("mint_dcpt_trad_psbl_yn") val mintDcptTradPsblYn: String, // MINT소수점매매가능여부
        @JsonProperty("mint_fnum_trad_psbl_yn") val mintFnumTradPsblYn: String, // MINT정수매매가능여부
        @JsonProperty("mint_cblc_cvsn_ipsb_yn") val mintCblcCvsnIpsbYn: String, // MINT잔고전환불가여부
        @JsonProperty("ptp_item_yn") val ptpItemYn: String,                   // PTP종목여부
        @JsonProperty("ptp_item_trfx_exmt_yn") val ptpItemTrfxExmtYn: String, // PTP종목양도세면제여부
        @JsonProperty("ptp_item_trfx_exmt_strt_dt") val ptpItemTrfxExmtStrtDt: String, // PTP종목양도세면제시작일자
        @JsonProperty("ptp_item_trfx_exmt_end_dt") val ptpItemTrfxExmtEndDt: String, // PTP종목양도세면제종료일자
        @JsonProperty("dtm_tr_psbl_yn") val dtmTrPsblYn: String,               // 주간거래가능여부
        @JsonProperty("sdrf_stop_ecls_yn") val sdrfStopEclsYn: String,         // 급등락정지제외여부
        @JsonProperty("sdrf_stop_ecls_erlm_dt") val sdrfStopEclsErlmDt: String // 급등락정지제외등록일자
)
