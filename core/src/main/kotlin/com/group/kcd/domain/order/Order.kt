package com.group.kcd.domain.order

import com.group.kcd.domain.BaseEntity
import com.group.kcd.util.kstNowDate
import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
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

  val txDate: LocalDate = kstNowDate(),

  @Enumerated(EnumType.STRING)
  val type: OrderType = OrderType.MANUAL,
) : BaseEntity() {
  companion object {
    const val AUTO_ORDER_THRESHOLD_AMOUNT = 500_000L
    fun fixture(
      userId: Long = 0L,
      productId: Long = 0L,
      count: Int = 1,
      totalPrice: Long = 1000L,
      txDate: LocalDate = kstNowDate(),
      type: OrderType = OrderType.MANUAL
    ): Order {
      return Order(
        userId = userId,
        productId = productId,
        count = count,
        totalPrice = totalPrice,
        txDate = txDate,
        type = type,
      )
    }
  }
}