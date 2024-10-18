package com.knu.mockin.model.dto.kisrequest.trading

import com.fasterxml.jackson.annotation.JsonProperty

data class KISOrderReverseRequestBodyDto(
    @JsonProperty("CANO") var accountNumber: String = "" ,                              // 종합계좌번호
    @JsonProperty("ACNT_PRDT_CD") var accountProductCode: String = "01" ,               // 계좌상품코드
    @JsonProperty("OVRS_EXCG_CD") var overseasExchangeCode: String = "" ,               // 해외거래소코드
    @JsonProperty("PDNO") var productNumber: String = "" ,                              // 상품번호
    @JsonProperty("ORGN_ODNO") var originalOrderNumber: String = "" ,                   // 원주문번호
    @JsonProperty("RVSE_CNCL_DVSN_CD") var cancelOrReviseCode: String = "" ,            // 정정취소구분코드
    @JsonProperty("ORD_QTY") var orderQuantity: String = "" ,                           // 주문수량
    @JsonProperty("OVRS_ORD_UNPR") var overseasOrderPrice: String = "" ,                // 해외주문단가
    @JsonProperty("MGCO_APTM_ODNO") var managementDesignatedOrderNumber: String = "" ,  // 운용사지정주문번호
    @JsonProperty("ORD_SVR_DVSN_CD") var orderServerDivisionCode: String = "0"           // 주문서버구분코드
)
