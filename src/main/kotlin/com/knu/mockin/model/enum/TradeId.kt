package com.knu.mockin.model.enum

enum class TradeId(val tradeId:String) {
    USA_BUY("VTTT1002U"),                 // 미국 매수 주문
    USA_SELL("VTTT1001U"),                // 미국 매도 주문
    USA_CANCEL("VTTT1004U"),              // 미국 정정 취소 주문
    JAPAN_BUY("VTTS0308U"),               // 일본 매수 주문
    JAPAN_SELL("VTTS0307U"),              // 일본 매도 주문
    JAPAN_CANCEL("VTTS0309U"),            // 일본 정정 취소 주문
    SHANGHAI_BUY("VTTS0202U"),            // 상해 매수 주문
    SHANGHAI_SELL("VTTS1005U"),           // 상해 매도 주문
    SHANGHAI_CANCEL("VTTS0302U"),         // 상해 정정 취소 주문
    HONG_KONG_BUY("VTTS1002U"),           // 홍콩 매수 주문
    HONG_KONG_SELL("VTTS1001U"),          // 홍콩 매도 주문
    HONG_KONG_CANCEL("VTTS1003U"),        // 홍콩 정정 취소 주문
    SHENZHEN_BUY("VTTS0305U"),            // 심천 매수 주문
    SHENZHEN_SELL("VTTS0304U"),           // 심천 매도 주문
    SHENZHEN_CANCEL("VTTS0306U "),        // 심천 정정 취소 주문
    VIETNAM_BUY("VTTS0311U"),             // 베트남 매수 주문
    VIETNAM_SELL("VTTS0310U"),            // 베트남 매도 주문
    VIETNAM_CANCEL("VTTS0312U"),          // 베트남 정정 취소 주문
    INQUIRE_BALANCE("VTTS3012R"),         // 잔고 조회
    INQUIRE_NCCS("VTTS3018R"),            // 미체결내역 조회
    INQUIRE_PSAMOUNT("VTTS3007R"),        // 매수가능금액 조회
    INQUIRE_PRESENT_BALANCE("VTRP6504R"), // 체결기준현재잔고 조회
    INQUIRE_CCNL("VTTS3035R"),            // 주문체결내역 조회
    PRICE("HHDFS00000300"),       // 해외주식 현재체결가
    DAILY_PRICE("HHDFS76240000"),          // 해외주식 기간별시세
    INQUIRE_DAILY_CHART_PRICE("FHKST03030100"),   // 해외주식 종목/지수/환율 기간별 시세
    INQUIRE_SEARCH("HHDFS76410000"),              // 해외주식 조건검색
    COUNTRIES_HOLIDAY("CTOS5011R"),       // 해외결제일자조회
    PRICE_DETAIL("HHDFS76200200"),        // 해외주식 현재가상세
    INQUIRE_TIME_ITEM_CHART_PRICE("HHDFS76950200"),     // 해외주식 분봉조회
    INQUIRE_TIME_INDEX_CHART_PRICE("FHKST03030200"),     // 해외지수분봉조회
    SEARCH_INFO("CTPF1702R"),              // 해외주식 상품기본정보
    INQUIRE_ASKING_PRICE("HHDFS76200100"),  // 해외주식 현재가 10호가
    NEWS_TITLE("HHPSTH60100C1");          // 해외뉴스종합

  
  
    companion object {
        fun getTradeIdByEnum(enum: TradeId): String {
            return enum.tradeId
        }
    }

}