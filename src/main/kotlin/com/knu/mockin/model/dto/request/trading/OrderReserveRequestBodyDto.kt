package com.knu.mockin.model.dto.request.trading

import com.fasterxml.jackson.annotation.JsonProperty
import com.knu.mockin.model.dto.kisrequest.trading.KISOrderReserveRequestBodyDto
import com.knu.mockin.model.dto.kisrequest.trading.KISOrderReverseRequestBodyDto

data class OrderReserveRequestBodyDto(
    val transactionId: String = "",                 // 요청 종류
    val sellBuyDivisionCode: String = "",           // 매도매수구분코드
    val reviseCancelDivisionCode: String = "",      // 정정취소구분코드
    val productNumber: String = "",                 // 상품번호
    val productTypeCode: String = "",               // 상품유형코드
    val overseasExchangeCode: String = "",          // 해외거래소코드
    val ftOrderQuantity: String = "",               // FT주문수량
    val ftOrderUnitPrice3: String = "",             // FT주문단가3
    val reservationOrderReceiptDate: String = "",   // 예약주문접수일자
    val orderDivision: String = "",                 // 주문구분
    val overseasReservationOrderNumber: String = "" // 해외예약주문번호
)

fun OrderReserveRequestBodyDto.asDomain(accountNumber: String): KISOrderReserveRequestBodyDto{
    return KISOrderReserveRequestBodyDto(
        accountNumber = accountNumber,
        sellBuyDivisionCode = this.sellBuyDivisionCode,
        reviseCancelDivisionCode = this.reviseCancelDivisionCode,
        productNumber = this.productNumber,
        productTypeCode = this.productTypeCode,
        overseasExchangeCode = this.overseasExchangeCode,
        ftOrderQuantity = this.ftOrderQuantity,
        ftOrderUnitPrice3 = this.ftOrderUnitPrice3,
        reservationOrderReceiptDate = this.reservationOrderReceiptDate,
        orderDivision = this.orderDivision,
        overseasReservationOrderNumber = this.overseasReservationOrderNumber
    )
}