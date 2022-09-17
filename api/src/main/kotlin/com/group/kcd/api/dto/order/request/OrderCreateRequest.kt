package com.group.kcd.api.dto.order.request

/**
 * 다음과 같은 경우는 고려하지 않는다.
 * - 유저가 화면에 노출된 제품 정보를 보고 주문한다. 이때 가격은 1000원이었다.
 * - 주문을 하려던 찰나, 동시에 제품 가격이 변경된다. 이때 2000원이 되었다.
 * 현재 로직상은 2000원으로 간주되어 저장된다.
 */
data class OrderCreateRequest(
  val productId: Long,
  val count: Int,
)
