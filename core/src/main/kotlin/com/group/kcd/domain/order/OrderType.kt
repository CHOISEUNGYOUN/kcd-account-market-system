package com.group.kcd.domain.order

enum class OrderType {

  MANUAL, // 사용자가 직접 장바구니에 담은 주문
  AUTO_FROM_NET_INCOME, // 순수익에 의해 자동으로 생성된 주문

}