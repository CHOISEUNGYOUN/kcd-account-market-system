package com.group.kcd.domain.order

import com.group.kcd.domain.BaseEntity
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "orders")
class Order(
  val userId: Long,
  val productId: Long,
  val count: Int, // 주문 수량

  /**
   * Product를 주문한 이후, Product의 가격이 바뀔 수 있으므로 합산 금액은 역정규화 해주었다.
   */
  val totalPrice: Long,
) : BaseEntity()