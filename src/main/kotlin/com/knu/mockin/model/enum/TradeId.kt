package com.knu.mockin.model.enum

enum class TradeId(val tradeId:String) {
    USA_BUY("VTTT1002U"),         // 미국 매수 주문
    USA_SELL("VTTT1001U"),        // 미국 매도 주문
    USA_CANCEL("VTTT1004U"),      // 미국 정정 취소 주문
    JAPAN_BUY("VTTS0308U"),       // 일본 매수 주문
    JAPAN_SELL("VTTS0307U"),      // 일본 매도 주문
    JAPAN_CANCEL("VTTS0309U"),    // 일본 정정 취소 주문
    SHANGHAI_BUY("VTTS0202U"),    // 상해 매수 주문
    SHANGHAI_SELL("VTTS1005U"),   // 상해 매도 주문
    SHANGHAI_CANCEL("VTTS0302U"), // 상해 정정 취소 주문
    HONG_KONG_BUY("VTTS1002U"),   // 홍콩 매수 주문
    HONG_KONG_SELL("VTTS1001U"),  // 홍콩 매도 주문
    HONG_KONG_CANCEL("VTTS1003U"),// 홍콩 정정 취소 주문
    SHENZHEN_BUY("VTTS0305U"),    // 심천 매수 주문
    SHENZHEN_SELL("VTTS0304U"),   // 심천 매도 주문
    SHENZHEN_CANCEL("VTTS0306U "),// 심천 정정 취소 주문
    VIETNAM_BUY("VTTS0311U"),     // 베트남 매수 주문
    VIETNAM_SELL("VTTS0310U"),    // 베트남 매도 주문
    VIETNAM_CANCEL("VTTS0312U"),  // 베트남 정정 취소 주문
    CURRENT_PRICE("HHDFS00000300");

    companion object {
        fun getTradeIdByEnum(enum: TradeId): String {
            return enum.tradeId
        }
    }

}